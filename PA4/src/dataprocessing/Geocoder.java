package dataprocessing;

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

  }

  public List<double[]> geocode(String canonicalName, int streetNumber, List<String> segmentIDs)
  {
    return null;
  }
}
