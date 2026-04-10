package graph;

import feature.StreetSegment;

/**
 * Abstract implementation of LabelManager.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public abstract class AbstractLabelManager implements LabelManager
{

  // Protected field (accessible in subclasses)
  protected Label[] labels;

  /**
   * Constructor for AbstractLabelManager that initializes the labels and fills it.
   * @param networkSize number of intersections
   */
  public AbstractLabelManager(final int networkSize)
  {
    labels = new Label[networkSize];
    for (int i = 0; i < networkSize; i++)
    {
      labels[i] = new Label(i);
    }
  }

  // Abstract method (must be implemented by subclasses)
  @Override
  public abstract void adjustHeadValue(final StreetSegment segment);

  // Concrete method (shared behavior)
  @Override
  public Label getLabel(final int intersectionID)
  {
    return labels[intersectionID];
  }
}
