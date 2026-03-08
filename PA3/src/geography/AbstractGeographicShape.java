package geography;

import java.awt.Shape;

/**
 * Abstract base class for geographic shapes with a string identifier.
 */
public abstract class AbstractGeographicShape implements GeographicShape
{

  private String id;

  /**
   * Constructs an AbstractGeographicShape with the given id.
   * 
   * @param id
   *          the unique identifier for this shape
   */
  public AbstractGeographicShape(String id)
  {
    this.id = id;
  }

  @Override
  public String getID()
  {
    return id;
  }

  @Override
  public abstract Shape getShape();

}
