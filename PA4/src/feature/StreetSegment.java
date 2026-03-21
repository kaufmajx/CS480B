package feature;

import geography.GeographicShape;

public class StreetSegment extends AbstractFeature
{
  private double length;
  private int tail;
  private int head;
  private int highAddress;
  private int lowAddress;
  private GeographicShape geographicShape;
  private String code;

  public StreetSegment(String id, String code, GeographicShape shape, int lowAddress,
      int highAddress, int tail, int head, double length)
  {
    super(id);
  }

  @Override
  public String getID()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GeographicShape getGeographicShape()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
