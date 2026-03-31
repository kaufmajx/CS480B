package graph;

/**
 * Handles permanent labels in the graph.
 */
public interface PermanentLabelManager
{
  /**
   * Gets the smallest label.
   *
   * @return the smallest label
   */
  public Label getSmallerLabel();

  /**
   * Marks a label as permanent.
   *
   * @param intersectionID
   *          the intersection ID
   */
  public void makePermanent(final int intersectionID);
}
