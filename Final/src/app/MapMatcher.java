package app;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.Street;
import feature.StreetSegment;
import geography.AbstractMapProjection;
import geography.PieceWiseLinearCurve;

/**
 * Snaps a raw GPS point to the nearest street segment, preferring segments whose direction agrees
 * with the vehicle's travel bearing.
 *
 * @author Jelal Kaufman, Tenley Kennett
 * @version 1.0
 */
public class MapMatcher
{
  // Max snap distance, km
  private static final double MAX_CORRECTION_DIST = 0.035;

  // Max bearing gap before a candidate is treated as a cross-street, degrees
  private static final double MAX_BEARING_DIFF = 45.0;

  // Number of recent fixes kept for the ongoing bearing estimate
  private static final int BEARING_HISTORY = 5;

  // Min displacement before the rolling bearing is trusted, km
  private static final double MIN_BEARING_DIST = 0.005; // 5 meters

  // closestPoint() return-array indices (for reference)
  private static final int FOOT_X = 0;
  private static final int FOOT_Y = 1;
  private static final int FOOT_DIST = 2;
  private static final int SUB_INDEX = 3;

  // All candidate segments, made once at construction
  private final List<StreetSegment> segments;

  // Projection between (lon, lat) degrees and the local km grid
  private final AbstractMapProjection proj;

  // Sliding window of recent projected fixes [kmX, kmY], oldest at index 0
  private final List<double[]> recentFixes = new ArrayList<>();

  // Segment the last fix snapped to, or null
  public StreetSegment matchedSegment;

  // Snapped longitude & latitude, degrees
  public double matchedLon;
  public double matchedLat;

  // Perpendicular distance from raw fix to snap point, km
  public double matchedDist;

  // True if travel direction agrees with matchedSegment's tail -> head
  public boolean matchedDirectionAgrees = true;

  /**
   * Builds a matcher over every segment in the supplied street map.
   *
   * @param streets
   * @param proj
   */
  public MapMatcher(final Map<String, Street> streets, final AbstractMapProjection proj)
  {
    this.proj = proj;

    segments = new ArrayList<>();
    for (Street s : streets.values())
    {
      Iterator<StreetSegment> it = s.getSegments();
      while (it.hasNext())
      {
        segments.add(it.next());
      }
    }
  }

  /**
   * Snaps a raw GPS fix to most plausible segment + updates 'matched' fields. Prefers
   * same-direction candidate, but falls back to the closest reverse-direction candidate when none
   * exists.
   *
   * @param lon
   *          raw GPS longitude, in degrees
   * @param lat
   *          raw GPS latitude, in degrees
   * @return true if a segment within range was found
   */
  public boolean match(final double lon, final double lat)
  {

    // Translate coordinates into km
    double[] km = proj.forward(new double[] {lon, lat});
    double kmLon = km[0];
    double kmLat = km[1];

    Double bearing = travelBearing(kmLon, kmLat);

    matchedSegment = null;
    matchedLon = lon;
    matchedLat = lat;
    matchedDist = Double.MAX_VALUE;
    matchedDirectionAgrees = true;

    for (StreetSegment seg : segments)
    {
      List<double[]> pts = vertices(seg);
      if (pts.size() < 2)
        continue; // only 0-1 point. not enough to be a line

      double[] closest = closestPoint(kmLon, kmLat, pts);
      if (closest[FOOT_DIST] > MAX_CORRECTION_DIST)
        continue; // segment is too far away to be a plausible snap target.

      // Direction status of this candidate
      boolean agrees = true;
      if (bearing != null) // if we haven't established a direction yet, skip
      {
        // find compass bearing of the specific sub-segment so we can compare it to the car's
        // actual direction of travel.
        double arcBearing = bearing(pts, (int) closest[SUB_INDEX]);
        double sameDirDiff = angularDiff(bearing, arcBearing);
        double oppDirDiff = angularDiff(bearing, (arcBearing + 180.0) % 360.0);

        if (Math.min(sameDirDiff, oppDirDiff) > MAX_BEARING_DIFF)
          continue; // neither the arc nor the reverse aligns with travel, so this is a
                    // cross-street, not the road we're on

        // is the car driving WITH the segment's tail->head arrow (true) or AGAINST it (false).
        agrees = sameDirDiff <= oppDirDiff;
      }

      // Closest segment wins outright. Record whichever direction status came with it so
      // FinalApp can pick head vs tail correctly when routing.
      if (closest[FOOT_DIST] < matchedDist)
      {
        double[] deg = proj.inverse(new double[] {closest[FOOT_X], closest[FOOT_Y]});
        matchedSegment = seg;
        matchedDist = closest[FOOT_DIST];
        matchedLon = deg[0];
        matchedLat = deg[1];
        matchedDirectionAgrees = agrees;
      }
    }

    return matchedSegment != null;
  }

  /**
   * Extracts a segment's vertices as a list of [kmX, kmY] vertex pairs in tail->head order.
   *
   * @param seg
   *          segment to flatten
   * @return polyline vertices in projected km; empty if the segment shape is not a polyline
   */
  private List<double[]> vertices(final StreetSegment seg)
  {
    List<double[]> pts = new ArrayList<>();
    if (!(seg.getGeographicShape() instanceof PieceWiseLinearCurve))
      return pts;

    PieceWiseLinearCurve piecewise = (PieceWiseLinearCurve) seg.getGeographicShape();
    PathIterator pi = piecewise.getShape().getPathIterator(null);
    double[] currCord = new double[6];

    while (!pi.isDone())
    {
      int type = pi.currentSegment(currCord);
      if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO)
        pts.add(new double[] {currCord[0], currCord[1]});
      pi.next();
    }

    return pts;
  }

  /**
   * Returns the foot of the perpendicular from GPS to the closest sub-segment of a polyline, the
   * distance, sub-segment index, and parametric position.
   *
   * @param x
   *          query x in projected km
   * @param y
   *          query y in projected km
   * @param pts
   *          polyline vertices in km, tail->head order
   * @return [footX, footY, dist, subIndex, t] in km / km / km / int / [0,1]; null if pts < 2
   */
  private double[] closestPoint(final double x, final double y, final List<double[]> pts)
  {
    double bestDist = Double.MAX_VALUE;
    double[] best = null;

    for (int i = 0; i < pts.size() - 1; i++)
    {
      double ax = pts.get(i)[0], ay = pts.get(i)[1];
      double bx = pts.get(i + 1)[0], by = pts.get(i + 1)[1];

      double dx = bx - ax, dy = by - ay;

      double t = ((x - ax) * dx + (y - ay) * dy) / (dx * dx + dy * dy);
      t = Math.max(0.0, Math.min(1.0, t));

      double fx = ax + t * dx;
      double fy = ay + t * dy;
      double dist = Math.sqrt((x - fx) * (x - fx) + (y - fy) * (y - fy));

      if (dist < bestDist)
      {
        bestDist = dist;
        best = new double[] {fx, fy, dist, i, t};
      }
    }
    return best;
  }

  /**
   * Compass bearing (0=N, clockwise) of sub-segment {code i} of a polyline in the km grid.
   *
   * @param pts
   *          polyline vertices in projected km
   * @param i
   *          index of the sub-segment whose bearing is wanted
   * @return bearing in degrees in [0, 360)
   */
  private double bearing(final List<double[]> pts, final int i)
  {
    double dx = pts.get(i + 1)[0] - pts.get(i)[0]; // how far east does this segment go?
    double dy = pts.get(i + 1)[1] - pts.get(i)[1]; // how far north does this segment go?
    return (Math.toDegrees(Math.atan2(dx, dy)) + 360.0) % 360.0;
  }

  /**
   * Smallest absolute angular distance between two compass bearings.
   *
   * @param a
   *          first bearing, degrees
   * @param b
   *          second bearing, degrees
   * @return angular difference in [0, 180]
   */
  private double angularDiff(final double a, final double b)
  {
    double d = Math.abs(a - b) % 360.0;
    return d > 180.0 ? 360.0 - d : d;
  }

  /**
   * Updates the rolling fix window and returns the current travel bearing, or null when the
   * displacement is too small to be trusted.
   *
   * @param kmX
   *          newest fix x in projected km
   * @param kmY
   *          newest fix y in projected km
   * @return bearing in degrees [0, 360), or null when not yet reliable
   */
  private Double travelBearing(final double kmX, final double kmY)
  {
    recentFixes.add(new double[] {kmX, kmY}); // push newest
    if (recentFixes.size() > BEARING_HISTORY)
      recentFixes.remove(0); // drop oldest

    if (recentFixes.size() < 2)
      return null;

    double[] oldest = recentFixes.get(0);
    double[] newest = recentFixes.get(recentFixes.size() - 1);
    double dx = newest[0] - oldest[0]; // find change in E-W displacement
    double dy = newest[1] - oldest[1]; // find change in N-S displacement

    if (Math.sqrt(dx * dx + dy * dy) < MIN_BEARING_DIST)
      return null; // if Euclidean distance traveled is negligible, ignore

    return (Math.toDegrees(Math.atan2(dx, dy)) + 360.0) % 360.0;
  }
}
