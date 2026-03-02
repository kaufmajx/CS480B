package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class Polygon extends PieceWiseLinearCurve
{

  public Polygon(String id)
  {
    super(id);
  }

  public Polygon(String id, Path2D.Double shape)
  {
    super(id, shape);
  }

  public Shape getShape()
  {
    return super.getShape();
  }
}
