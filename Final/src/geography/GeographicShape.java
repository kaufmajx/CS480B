package geography;

import java.awt.Shape;

/**
 * Represents a geographic shape with an identifier.
 */
public interface GeographicShape
{

  /**
   * Returns the unique identifier for this shape.
   * 
   * @return the shape ID
   */
  public String getID();

  /**
   * Returns the AWT Shape for this geographic shape.
   * 
   * @return the shape
   */
  public Shape getShape();

}
