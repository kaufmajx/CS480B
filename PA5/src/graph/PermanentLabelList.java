package graph;

import feature.StreetSegment;

/**
 * A list-based implementation of a permanent label manager. It manages labels that have been marked
 * as permanent.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class PermanentLabelList extends AbstractLabelManager implements PermanentLabelManager
{
  /**
   * Creates a permanent label list.
   *
   * @param networkSize
   *          the number of nodes in the network
   */
  public PermanentLabelList(final int networkSize)
  {
    super(networkSize);
  }

  /**
   * Returns the smallest label among non-permanent labels.
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
