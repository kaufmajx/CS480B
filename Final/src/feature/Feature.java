package feature;

import geography.GeographicShape;

/**
 * Represents a mappable feature with a unique identifier and a geographic shape.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public interface Feature
{
  /**
   * Returns the unique identifier for this feature.
   *
   * @return the id
   */
  public String getID();

  /**
   * Returns the geographic shape associated with this feature.
   *
   * @return the geographic shape
   */
  public GeographicShape getGeographicShape();
}
