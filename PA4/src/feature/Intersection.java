package feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a road intersection, tracking which street segments flow into and out of it.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class Intersection
{
  private List<StreetSegment> inbound;
  private List<StreetSegment> outbound;

  /**
   * Constructs an Intersection with empty inbound and outbound segment lists.
   */
  public Intersection()
  {
    inbound = new ArrayList<StreetSegment>();
    outbound = new ArrayList<StreetSegment>();
  }

  /**
   * Adds a street segment that leads into this intersection.
   *
   * @param segment
   *          the inbound street segment
   */
  public void addInbound(final StreetSegment segment)
  {
    inbound.add(segment);
  }

  /**
   * Adds a street segment that leads out of this intersection.
   *
   * @param segment
   *          the outbound street segment
   */
  public void addOutbound(final StreetSegment segment)
  {
    outbound.add(segment);
  }

  /**
   * Returns the list of street segments that lead into this intersection.
   *
   * @return the inbound street segments
   */
  public List<StreetSegment> getInbound()
  {
    return inbound;
  }

  /**
   * Returns the list of street segments that lead out of this intersection.
   *
   * @return the outbound street segments
   */
  public List<StreetSegment> getOutbound()
  {
    return outbound;
  }
}
