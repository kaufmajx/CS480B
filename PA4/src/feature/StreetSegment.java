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
    this.length = length;
    this.head = head;
    this.tail = tail;
    this.highAddress = highAddress;
    this.lowAddress = lowAddress;
    this.geographicShape = shape;
    this.code = code;
  }

  public double getLength()
  {
    return length;
  }

  public int getHead()
  {
    return head;
  }

  public int getTail()
  {
    return tail;
  }

  public int getHighAddress()
  {
    return highAddress;
  }

  public double getLowAddress()
  {
    return lowAddress;
  }

  public String getCode()
  {
    return code;
  }

  @Override
  public String getID()
  {
    return super.getID();
  }

  @Override
  public GeographicShape getGeographicShape()
  {
    return geographicShape;
  }

}
