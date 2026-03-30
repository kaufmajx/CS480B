package feature;

import geography.GeographicShape;

/**
 * Represents a single segment of a street with address range, geometry, and connectivity
 * information.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class StreetSegment extends AbstractFeature
{
  private double length;
  private int tail;
  private int head;
  private int highAddress;
  private int lowAddress;
  private GeographicShape geographicShape;
  private String code;

  /**
   * Constructs a StreetSegment with the given attributes.
   *
   * @param id
   *          the unique arc ID for this segment
   * @param code
   *          the TIGER road type code
   * @param shape
   *          the geographic shape of this segment
   * @param lowAddress
   *          the lower bound of the address range
   * @param highAddress
   *          the upper bound of the address range
   * @param tail
   *          the tail node ID
   * @param head
   *          the head node ID
   * @param length
   *          the length of this segment in kilometers
   */
  public StreetSegment(final String id, final String code, final GeographicShape shape,
      final int lowAddress, final int highAddress, final int tail, final int head,
      final double length)
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

  /**
   * Returns the length of this segment in kilometers.
   *
   * @return the length
   */
  public double getLength()
  {
    return length;
  }

  /**
   * Returns the head node ID of this segment.
   *
   * @return the head node ID
   */

  public int getHead()
  {
    return head;
  }

  /**
   * Returns the tail node ID of this segment.
   *
   * @return the tail node ID
   */
  public int getTail()
  {
    return tail;
  }

  /**
   * Returns the upper bound of the address range for this segment.
   *
   * @return the high address
   */
  public int getHighAddress()
  {
    return highAddress;
  }

  /**
   * Returns the lower bound of the address range for this segment.
   *
   * @return the low address
   */
  public int getLowAddress()
  {
    return lowAddress;
  }

  /**
   * Returns the TIGER road type code for this segment.
   *
   * @return the code
   */
  public String getCode()
  {
    return code;
  }

  /**
   * Returns the unique identifier for this segment.
   *
   * @return the arc ID
   */
  @Override
  public String getID()
  {
    return super.getID();
  }

  /**
   * Returns the geographic shape of this segment.
   *
   * @return the geographic shape
   */
  @Override
  public GeographicShape getGeographicShape()
  {
    return geographicShape;
  }

}
