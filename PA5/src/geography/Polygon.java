package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * Represents a closed polygon as a geographic shape.
 */
public class Polygon extends PieceWiseLinearCurve
{

  /**
   * Constructs a Polygon with the given id.
   * 
   * @param id
   *          the unique identifier for this polygon
   */
  public Polygon(final String id)
  {
    super(id);
  }

  /**
   * Constructs a Polygon with the given id and shape.
   * 
   * @param id
   *          the unique identifier for this polygon
   * @param shape
   *          the path defining this polygon
   */
  public Polygon(final String id, final Path2D.Double shape)
  {
    super(id, shape);
  }

  /**
   * Returns the closed shape of this polygon.
   * 
   * @return the closed AWT Shape
   */
  public Shape getShape()
  {
    // Must close the polygon if necessary before returning
    Path2D.Double closed = (Path2D.Double) shape.clone();
    closed.closePath();
    return closed;
  }
}
