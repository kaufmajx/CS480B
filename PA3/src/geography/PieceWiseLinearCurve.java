package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class PieceWiseLinearCurve extends AbstractGeographicShape
{
  protected Path2D.Double shape;

  public PieceWiseLinearCurve(String id)
  {
    super(id);

  }

  public PieceWiseLinearCurve(String id, Path2D.Double shape)
  {
    super(id);
    this.shape = shape;
  }

  public void add(double[] point)
  {

  }

  public void append(Shape addition, boolean connect)
  {

  }

  @Override
  public Shape getShape()
  {
    return this.shape;
  }

}
