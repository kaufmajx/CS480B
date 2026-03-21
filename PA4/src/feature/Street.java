package feature;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import geography.GeographicShape;
import geography.PieceWiseLinearCurve;

public class Street
{
  private PieceWiseLinearCurve shape;
  private String category;
  private String code;
  private String name;
  private String prefix;
  private String suffix;
  private List<StreetSegment> segments;

  public Street(String prefix, String name, String category, String suffix, String code)
  {

  }

  public void addSegment(StreetSegment segment)
  {

  }

  public static String createCanonicalName(String prefix, String name, String category, String suffix)
  {
    return null;
  }

  public List<StreetSegment> getSegments(int number)
  {
    return null;
  }

  public Iterator<StreetSegment> getSegments()
  {
    return null;
  }

  public GeographicShape getGeographicShape()
  {
    return null;
  }

  public int getSize()
  {
    return 0;
  }

}
