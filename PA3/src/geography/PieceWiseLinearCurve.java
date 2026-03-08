package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class PieceWiseLinearCurve extends AbstractGeographicShape
{
  protected Path2D.Double shape;

  public PieceWiseLinearCurve(String id)
  {
    super(id);
    shape = new Path2D.Double();
  }

  public PieceWiseLinearCurve(String id, Path2D.Double shape)
  {
    super(id);
    this.shape = shape;
  }

  public void add(double[] point)
  {
    if (shape.getCurrentPoint() == null)
      shape.moveTo(point[0], point[1]); // first point — just move there
    else
      shape.lineTo(point[0], point[1]);
  }

  public void append(Shape addition, boolean connect)
  {
    this.shape.append(addition, connect);
  }

  @Override
  public Shape getShape()
  {
    return this.shape;
  }

}
