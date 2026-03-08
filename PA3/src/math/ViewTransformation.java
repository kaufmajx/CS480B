package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public interface ViewTransformation
{
  public abstract AffineTransform getLastReflection();

  public abstract AffineTransform getLastTransform();

  public abstract AffineTransform getTransform(Rectangle2D displyBounds, Rectangle2D contentBounds);
}
