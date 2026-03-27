package dataprocessing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.*;
import geography.*;
import gui.*;

public class Geocoder
{
  private CartographyDocument<GeographicShape> shapes;
  private CartographyDocument<StreetSegment> segments;
  private Map<String, Street> streets;

  public Geocoder(CartographyDocument<GeographicShape> shapes,
      CartographyDocument<StreetSegment> segments, Map<String, Street> streets)
  {
    this.shapes = shapes;
    this.segments = segments;
    this.streets = streets;
  }

  public List<double[]> geocode(String canonicalName, int streetNumber, List<String> segmentIDs)
  {
    System.out.println("\nwe r geocoding()");
    List<double[]> results = new ArrayList<double[]>();

    Street street = streets.get(canonicalName);
    if (street == null)
    {
      System.out.println("No " + street + " Street :(");
      return results;
    }

    Iterator<StreetSegment> debug = street.getSegments();
    while (debug.hasNext())
    {
      StreetSegment seg = debug.next();
      System.out.println(
          "  seg " + seg.getID() + " low adress=" + seg.getLowAddress() + " high address=" + seg.getHighAddress());
    }

    // Collect ALL segment IDs for this street (for highlighting)
    Iterator<StreetSegment> allSegs = street.getSegments();
    while (allSegs.hasNext())
      segmentIDs.add(allSegs.next().getID());

    // Iterator<StreetSegment> it = street.getSegments();
    // while (it.hasNext())
    // {
    // StreetSegment seg = it.next();
    // int low = seg.getLowAddress();
    // int high = seg.getHighAddress();
    // if (streetNumber < low || streetNumber > high)
    // {
    // System.out.println("No " + streetNumber + " on " + street);
    // continue;
    // }
    //
    // double t = (high==low) ? 0.5 : (double) (streetNumber - low) / (high - low);;
    // GeographicShape geoShape = seg.getGeographicShape();
    // if (geoShape == null)
    // continue;
    // }
    //
    // return results;

    // Now find the matched segment(s) to return interpolated coordinates

    Iterator<StreetSegment> it = street.getSegments();

    while (it.hasNext())

    {

      StreetSegment seg = it.next();

      int low = (int) seg.getLowAddress();
      int high = seg.getHighAddress();
      if (streetNumber < low || streetNumber > high) // If the given number is out of range
        continue;

      double t = (high == low) ? 0.5 : (double) (streetNumber - low) / (high - low);

      GeographicShape geoShape = seg.getGeographicShape();

      if (geoShape == null)

        continue;

//      List<double[]> points = new ArrayList<>();
//
//      double[] coords = new double[6];
//
//      java.awt.geom.PathIterator pi = geoShape.getShape().getPathIterator(null);
//
//      while (!pi.isDone())
//
//      {
//
//        int type = pi.currentSegment(coords);
//
//        if (type == java.awt.geom.PathIterator.SEG_MOVETO
//
//            || type == java.awt.geom.PathIterator.SEG_LINETO)
//
//          points.add(new double[] {coords[0], coords[1]});
//
//        pi.next();
//
//      }
//
//      if (points.isEmpty())
//
//        continue;
//
//      if (points.size() == 1)
//
//      {
//
//        results.add(points.get(0));
//
//        continue;
//
//      }
//
//      double totalLength = 0.0;
//
//      for (int i = 1; i < points.size(); i++)
//
//      {
//
//        double dx = points.get(i)[0] - points.get(i - 1)[0];
//
//        double dy = points.get(i)[1] - points.get(i - 1)[1];
//
//        totalLength += Math.sqrt(dx * dx + dy * dy);
//
//      }
//
//      double target = t * totalLength;
//
//      double accumulated = 0.0;
//
//      double[] point = points.get(points.size() - 1);
//
//      for (int i = 1; i < points.size(); i++)
//
//      {
//
//        double[] a = points.get(i - 1);
//
//        double[] b = points.get(i);
//
//        double dx = b[0] - a[0];
//
//        double dy = b[1] - a[1];
//
//        double segLen = Math.sqrt(dx * dx + dy * dy);
//
//        if (accumulated + segLen >= target)
//
//        {
//
//          double u = (segLen == 0) ? 0 : (target - accumulated) / segLen;
//
//          point = new double[] {a[0] + u * dx, a[1] + u * dy};
//
//          break;
//
//        }
//
//        accumulated += segLen;
//
//      }
//
//      results.add(point);
//
    }

    return results;

  }
}
