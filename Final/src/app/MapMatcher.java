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

public class MapMatcher
{

  // how far a segment can be in degrees before it's ignored
  private static final double MAX_CORRECTION_DIST = 0.035; // in km

  private final List<StreetSegment> segments;
  private final AbstractMapProjection proj; // ADD

  // previous fix (for bearing)
  private static final int BEARING_HISTORY = 5;
  private final List<double[]> recentFixes = new ArrayList<>();

  private double prevLat = Double.NaN;
  private double prevLon = Double.NaN;

  // results of last match() call
  public StreetSegment matchedSegment;
  public double matchedLon;
  public double matchedLat;
  public double matchedDist;

  public MapMatcher(final Map<String, Street> streets, AbstractMapProjection proj)
  {
    this.proj = proj; // ADD

    // populate all street segments
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
   * Match a GPS point to the nearest street arc.
   * 
   * @param lon
   * @param lat
   * @return
   */
  public boolean match(double lon, double lat)
  {
    // Project GPS fix from degrees into km (same as street vertices)
    double[] km = proj.forward(new double[] {lon, lat});
    double kmLon = km[0];
    double kmLat = km[1];

    // TEMP: print the projected GPS point so we can compare to vertices
    System.out.printf("GPS in km: %.4f, %.4f%n", kmLon, kmLat);

    Double bearing = travelBearing(kmLon, kmLat); // handles its own history
    // prevLon = lon;
    // prevLat = lat;

    // reset results
    matchedSegment = null;
    matchedLon = lon; // keep these in degrees — panel expects degrees
    matchedLat = lat;
    matchedDist = Double.MAX_VALUE;

    for (StreetSegment seg : segments)
    {
      List<double[]> pts = vertices(seg); // already in km
      if (pts.size() < 2)
      {
        continue;
      }

      double[] closest = closestPoint(kmLon, kmLat, pts); // compare km to km

      if (closest[2] > MAX_CORRECTION_DIST)
        continue;

      if (bearing != null) // only heading-filter if not already very close
      {
        double arcBearing = bearing(pts, (int) closest[3]); // closest[3] = distance from GPS to F
        double diff = Math.min(angularDiff(bearing, arcBearing),
            angularDiff(bearing, (arcBearing + 180) % 360));

//        // # TEMP
//        System.out.printf("  seg %s  travelBearing=%.1f  arcBearing=%.1f  diff=%.1f%n", seg.getID(),
//            bearing, arcBearing, diff);

        if (diff > 45.0)
          continue;
      }

      if (closest[2] < matchedDist)
      {
        matchedSegment = seg;
        matchedDist = closest[2];

        // Convert the matched km point back to degrees for the panel
        double[] deg = proj.inverse(new double[] {closest[0], closest[1]});
        matchedLon = deg[0];
        matchedLat = deg[1];

      }

    }

    return matchedSegment != null;
  }

  /*************/
  /** HELPERS **/
  /*************/

  /**
   * Get the lon/lat vertices of a street segment's Path2D curve.
   * 
   * @param seg
   *          segment to be verteci-ified
   * @return plain list of lon/lat pairs found on the curve
   */
  private List<double[]> vertices(StreetSegment seg)
  {
    List<double[]> pts = new ArrayList<>();
    if (!(seg.getGeographicShape() instanceof PieceWiseLinearCurve))
      return pts;

    PieceWiseLinearCurve piecewise = (PieceWiseLinearCurve) seg.getGeographicShape();
    PathIterator pi = piecewise.getShape().getPathIterator(null);

    // currCord[0] = x, currCord[1] = y. others are obsolete (ex. bezier curve)
    double[] currCord = new double[6];

    while (!pi.isDone())
    {
      // fill currCord with coordinates of this path & return its TYPE
      int type = pi.currentSegment(currCord);

      // SEG_MOVETO (start of the curve, SEG_LINETO (every other point)
      if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO)
      {
        pts.add(new double[] {currCord[0], currCord[1]});
      }

      pi.next();
    }

    return pts;
  }

  /**
   * Find the closest point to a lon/lat from a list of points.
   * 
   * @param lon
   * @param lat
   * @param pts
   * @return closest point (lon, lat, distance, sub-seg index)
   */
  private double[] closestPoint(double lon, double lat, List<double[]> pts)
  {
    double bestDist = Double.MAX_VALUE;
    double[] best = null;

    for (int i = 0; i < pts.size() - 1; i++)
    {
      double ax = pts.get(i)[0], ay = pts.get(i)[1]; // start of segment
      double bx = pts.get(i + 1)[0], by = pts.get(i + 1)[1]; // end of segment

      double dx = bx - ax, dy = by - ay; // vector from A to B (direction of segment)

      // "t is how far along AB you need to walk so that you're standing directly below the GPS
      // point."
      // t tells you where along the segment, F is the actual coordinates of that place.
      double t = ((lon - ax) * dx + (lat - ay) * dy) / (dx * dx + dy * dy);
      t = Math.max(0, Math.min(1, t)); // clamp t so it falls along 0-1

      // Calculate F (actual closest point on segment)
      double fx = ax + t * dx; // F.lon = A.lon + t * (B.lon - A.lon)
      double fy = ay + t * dy; // F.lat = A.lat + t * (B.lat - A.lat)

      // Calculate distance from GPS to F
      // cos-corect lon difference b/c lon degrees get physically shorter when u move away from the
      // equator.
      // double cosLat = Math.cos(Math.toRadians(lat));
      // double dist = Math.sqrt(Math.pow((lon - fx) * cosLat, 2) + Math.pow(lat - fy, 2));
      double dist = Math.sqrt((lon - fx) * (lon - fx) + (lat - fy) * (lat - fy));

      if (dist < bestDist)
      {
        bestDist = dist;
        // store: the foot point, distance, and sub-seg index
        best = new double[] {fx, fy, dist, i, t};
      }
    }
    return best;
  }

  /**
   * Compass bearing (0=North, clockwise) of sub-segment i.
   * 
   * @param pts
   * @param i
   * @return
   */
  private double bearing(List<double[]> pts, int i)
  {
    double dLon = pts.get(i + 1)[0] - pts.get(i)[0]; // remove cos-correction
    double dLat = pts.get(i + 1)[1] - pts.get(i)[1];
    return (Math.toDegrees(Math.atan2(dLon, dLat)) + 360) % 360;
  }

  /**
   * Smallest angle between two compass bearings [0, 180].
   * 
   * @param a
   * @param b
   * @return
   */
  private double angularDiff(double a, double b)
  {
    double d = Math.abs(a - b) % 360;
    return d > 180 ? 360 - d : d;
  }

  /**
   * 
   * @param lon
   * @param lat
   * @return
   */
  private Double travelBearing(double kmLon, double kmLat)
  {
    // if (Double.isNaN(prevLon))
    // return null;
    recentFixes.add(new double[] {kmLon, kmLat});

    if (recentFixes.size() > BEARING_HISTORY)
    {
      recentFixes.remove(0);
    }

    if (recentFixes.size() < 2)
    {
      return null; // need at least 2 fixes to derive a bearing
    }

    double[] oldest = recentFixes.get(0);
    double[] newest = recentFixes.get(recentFixes.size() - 1);

    double dLon = newest[0] - oldest[0];
    double dLat = newest[1] - oldest[1];
    double dist = Math.sqrt(dLon * dLon + dLat * dLat);

    // If we've barely moved, don't trust the bearing at all
    if (dist < 0.005)
      return null; // less than 5 meters in km space

    return (Math.toDegrees(Math.atan2(dLon, dLat)) + 360) % 360;
  }
}
