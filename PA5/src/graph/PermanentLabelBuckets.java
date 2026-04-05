package graph;

import feature.StreetSegment;

/**
 * A heap-based implementation of a permanent label bucket.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class PermanentLabelBuckets extends AbstractLabelManager implements PermanentLabelManager
{
  private int d;

  /**
   * Creates a permanent label heap.
   *
   * @param d
   *          the number of children per node in the heap
   * @param networkSize
   *          the number of nodes in the network
   */
  public PermanentLabelBuckets(final int networkSize)
  {
    super(networkSize);
    // TODO Auto-generated constructor stub
  }

  /**
   * Returns the smallest label from the heap.
   *
   * @return the smallest label
   */
  @Override
  public Label getSmallestLabel()
  {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Marks the label at the given intersection as permanent.
   *
   * @param intersectionID
   *          the intersection ID
   */
  @Override
  public void makePermanent(final int intersectionID)
  {
    // TODO Auto-generated method stub
  }

  /**
   * Updates labels using the given street segment.
   *
   * @param segment
   *          the street segment to process
   */
  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    // TODO Auto-generated method stub
  }
}
