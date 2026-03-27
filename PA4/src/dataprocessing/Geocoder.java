package dataprocessing;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.Street;
import feature.StreetSegment;
import geography.GeographicShape;
import gui.CartographyDocument;

/**
 * Translates a street address (name + number) into geographic coordinates.
 * 
 * Given a canonical street name and a house number, Geocoder searches the known street segments for
 * a matching address range and interpolates the corresponding point along the segment's geometry.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class Geocoder
{
  private CartographyDocument<GeographicShape> shapes;
  private CartographyDocument<StreetSegment> segments;
  private Map<String, Street> streets;

  /**
   * Creates a Geocoder with the given geographic shapes, street segments, and streets.
   *
   * @param shapes
   *          the geographic shapes document
   * @param segments
   *          the street segments document
   * @param streets
   *          a map of canonical street names to Street objects
   */
  public Geocoder(final CartographyDocument<GeographicShape> shapes,
      final CartographyDocument<StreetSegment> segments, final Map<String, Street> streets)
  {
    this.shapes = shapes;
    this.segments = segments;
    this.streets = streets;
  }

  /**
   * Finds the geographic coordinates of a street address.
   * 
   * All segment IDs belonging to the street are added to segmentIDs for highlighting. The returned
   * list contains one coordinate pair for each segment whose address range contains the given
   * street number.
   *
   * @param canonicalName
   *          the street name in canonical form (e.g. "N Paul St")
   * @param streetNumber
   *          the house number to locate (e.g. 410)
   * @param segmentIDs
   *          an empty list that will be populated with all segment IDs on the street, used for
   *          highlighting
   * @return a list of {x, y} coordinate pairs for the interpolated address location, or an empty
   *         list if the street or address was not found
   */
  public List<double[]> geocode(final String canonicalName, final int streetNumber,
      final List<String> segmentIDs)
  {
    // System.out.println("\nwe r geocoding()");
    List<double[]> results = new ArrayList<double[]>();

    Street street = streets.get(canonicalName);
    if (street == null)
    {
      // System.out.println("No " + street + " Street :(");
      return results;
    }

    // Collect ALL segment IDs for this street (for highlighting)
    Iterator<StreetSegment> allSegs = street.getSegments();
    while (allSegs.hasNext())
    {
      segmentIDs.add(allSegs.next().getID());
    }

    // Now find the matched segment(s) to return interpolated coordinates

    Iterator<StreetSegment> it = street.getSegments();

    while (it.hasNext())
    {
      StreetSegment seg = it.next();

      int low = seg.getLowAddress();
      int high = seg.getHighAddress();
      // If the given number is out of range
      if (streetNumber < low || streetNumber > high)
      {
        continue;
      }
      double t = (high == low) ? 0.5 : (double) (streetNumber - low) / (high - low);

      GeographicShape geoShape = seg.getGeographicShape();
      if (geoShape == null)
      {
        continue;
      }

      List<double[]> points = new ArrayList<>();

      double[] coords = new double[6];
      PathIterator pi = geoShape.getShape().getPathIterator(null);

      // Iterate along the path and accumulate all the points in 'points'
      while (!pi.isDone())
      {
        int type = pi.currentSegment(coords);

        if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO)
        {
          points.add(new double[] {coords[0], coords[1]});
          // System.out.println("coords: " + coords[0] + ", " + coords[1]);
        }
        pi.next();
      }

      if (points.isEmpty())
      {
        continue;
      }

      // if there is only 1 point, the path has no length. end here
      if (points.size() == 1)
      {
        results.add(points.get(0));
        continue;
      }

      // Add up the total length of the curve
      // example: A(0,0) -> B(3,0) -> C(3,4)
      double totalLength = 0.0;
      for (int i = 1; i < points.size(); i++)
      {
        double dx = points.get(i)[0] - points.get(i - 1)[0];
        double dy = points.get(i)[1] - points.get(i - 1)[1];
        totalLength += Math.sqrt(dx * dx + dy * dy); // example: 7
      }

      // How far along the curve is in actual distance
      double target = t * totalLength;

      // go thru the sub-segments until we find where target falls
      double accumulated = 0.0;

      double[] point = points.get(points.size() - 1);

      for (int i = 1; i < points.size(); i++)
      {
        double[] a = points.get(i - 1);
        double[] b = points.get(i);
        double dx = b[0] - a[0];
        double dy = b[1] - a[1];
        double segLen = Math.sqrt(dx * dx + dy * dy);

        if (accumulated + segLen >= target)
        {
          double u = (segLen == 0) ? 0 : (target - accumulated) / segLen;
          point = new double[] {a[0] + u * dx, a[1] + u * dy};
          break;
        }

        accumulated += segLen;
      }

      results.add(point);

    }

    return results;
  }
}
