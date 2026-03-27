package feature;

/**
 * A base implementation of the Feature interface that stores a unique identifier.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public abstract class AbstractFeature implements Feature
{
  private String id;

  /**
   * Constructs an AbstractFeature with the given unique identifier.
   *
   * @param id
   *          the unique identifier for this feature
   */
  public AbstractFeature(final String id)
  {
    this.id = id;
  }

  /**
   * Returns the unique identifier for this feature.
   *
   * @return the id
   */
  public String getID()
  {
    return id;
  }
}
