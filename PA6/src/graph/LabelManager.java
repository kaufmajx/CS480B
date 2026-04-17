package graph;

import feature.StreetSegment;

/**
 * A manager for Label objects.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public interface LabelManager
{
  /**
   * Adjust the head segment.
   * 
   * @param segment
   *          the current segment to change
   */
  public void adjustHeadValue(final StreetSegment segment);

  /**
   * Getter for intersection by ID.
   * 
   * @param intersectionID
   *          the interection to return
   * @return return the label at that ID
   */
  public Label getLabel(final int intersectionID);
}
