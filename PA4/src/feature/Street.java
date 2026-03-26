package feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import geography.GeographicShape;
import geography.PieceWiseLinearCurve;
import geography.Polygon;

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
    this.prefix = prefix;
    this.name = name;
    this.category = category;
    this.suffix = suffix;
    this.code = code;
    
    segments = new ArrayList<StreetSegment>();
    
    shape = new Polygon(code);
  }

  public void addSegment(StreetSegment segment)
  {
    segments.add(segment);
    
//    PieceWiseLinearCurve segmentCurve = segment.getShape();
//    if (segmentCurve != null)
//    {
//        if (shape == null)
//            shape = new PieceWiseLinearCurve(createCanonicalName(prefix, name, category, suffix));
//        shape.add(segmentCurve);
//    }
  }

  // idk what to do with the suffix? ex: would it be "Water Ave E"
  public static String createCanonicalName(String prefix, String name, String category, String suffix)
  {
    // prefix like = E, N, S, W
    // category like = RD, ST, AVE
    // suffix like = E, N, S, W
    return prefix + name + category + suffix;
  }

  public List<StreetSegment> getSegments(int number)
  {
    return null;
  }

  public Iterator<StreetSegment> getSegments()
  {
    return segments.iterator();
  }

  public GeographicShape getGeographicShape()
  {
    return shape;
  }

  public int getSize()
  {
    return segments.size();
  }

}
