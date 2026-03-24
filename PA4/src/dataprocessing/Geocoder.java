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
    List<double[]> results = new ArrayList<double[]>();
    
    Street street = streets.get(canonicalName);
    if (street == null) {
      return results;
    }
    
    Iterator<StreetSegment> it = street.getSegments();
    while (it.hasNext()) {
      StreetSegment seg = it.next();
      if (streetNumber < seg.getLowAddress() || streetNumber > seg.getHighAddress())
        continue;
      
      double range = seg.getHighAddress() - seg.getLowAddress();
      double x = (range == 0) ? 0.5 : (streetNumber - seg.getLowAddress() /range); // idk
      
            
    }
    
    return results;
  }
}
