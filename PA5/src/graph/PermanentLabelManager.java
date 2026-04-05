package graph;

/**
 * Handles permanent labels in the graph.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public interface PermanentLabelManager
{
  /**
   * Gets the smallest label.
   *
   * @return the smallest label
   */
  public Label getSmallestLabel();

  /**
   * Marks a label as permanent.
   *
   * @param intersectionID
   *          the intersection ID
   */
  public void makePermanent(final int intersectionID);
}
