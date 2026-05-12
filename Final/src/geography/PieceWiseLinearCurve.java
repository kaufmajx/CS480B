package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * Represents a piecewise linear curve as a geographic shape.
 *
 * @author Tenley K & Jelal K
 * @version 1.0
 */
public class PieceWiseLinearCurve extends AbstractGeographicShape
{
  protected Path2D.Double shape;

  /**
   * Constructs a PieceWiseLinearCurve with the given id.
   * 
   * @param id
   *          the unique identifier for this shape
   */
  public PieceWiseLinearCurve(final String id)
  {
    super(id);
    shape = new Path2D.Double();
  }

  /**
   * Constructs a PieceWiseLinearCurve with the given id and shape.
   * 
   * @param id
   *          the unique identifier for this shape
   * @param shape
   *          the path defining this curve
   */
  public PieceWiseLinearCurve(final String id, final Path2D.Double shape)
  {
    super(id);
    this.shape = shape;
  }

  /**
   * Adds a point to the curve.
   * 
   * @param point
   *          the coordinate pair to add
   */
  public void add(final double[] point)
  {
    if (shape.getCurrentPoint() == null)
      shape.moveTo(point[0], point[1]); // first point — just move there
    else
      shape.lineTo(point[0], point[1]);
  }

  /**
   * Appends another shape to this curve.
   * 
   * @param addition
   *          the shape to append
   * @param connect
   *          whether to connect the shapes
   */
  public void append(final Shape addition, final boolean connect)
  {
    this.shape.append(addition, connect);
  }

  @Override
  public Shape getShape()
  {
    return this.shape;
  }

}
