package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class DisplayCoordinatesTransformation implements ViewTransformation
{

  @Override
  public AffineTransform getLastReflection()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AffineTransform getLastTransform()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AffineTransform getTransform(Rectangle2D displayBounds, Rectangle2D contentBounds)
  {
    double scaleX = displayBounds.getWidth() / contentBounds.getWidth();
    double scaleY = displayBounds.getHeight() / contentBounds.getHeight();

    double scale = Math.min(scaleX, scaleY);

    AffineTransform at = new AffineTransform();

    at.translate(displayBounds.getX(), displayBounds.getY());
    at.scale(scale, -scale);
    at.translate(-contentBounds.getMinX(), -contentBounds.getMaxY());

    return at;
  }

}
