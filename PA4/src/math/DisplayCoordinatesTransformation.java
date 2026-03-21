package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Computes an affine transformation that maps content coordinates to display coordinates, applying
 * scaling and a Y-axis reflection to convert between coordinate systems.
 */
public class DisplayCoordinatesTransformation implements ViewTransformation
{
  private AffineTransform lastReflection;
  private AffineTransform lastTransform;

  @Override
  public AffineTransform getLastReflection()
  {
    return lastReflection;
  }

  @Override
  public AffineTransform getLastTransform()
  {
    return lastTransform;
  }

  @Override
  public AffineTransform getTransform(final Rectangle2D displayBounds,
      final Rectangle2D contentBounds)
  {
    // Find scale
    double scaleX = displayBounds.getWidth() / contentBounds.getWidth();
    double scaleY = displayBounds.getHeight() / contentBounds.getHeight();
    double scale = Math.min(scaleX, scaleY);

    // move content origin
    AffineTransform translate = new AffineTransform();

    translate.translate(displayBounds.getX() - contentBounds.getMinX() * scale,
        displayBounds.getY() + contentBounds.getMaxY() * scale);

    // Perform scale
    AffineTransform scaleAndReflect = new AffineTransform();
    scaleAndReflect.scale(scale, -scale);

    // Store the reflection (Y-flip only, no scaling) for getLastReflection()
    lastReflection = new AffineTransform();
    lastReflection.scale(1.0, -1.0);

    AffineTransform fullTranslate = new AffineTransform(translate);
    fullTranslate.concatenate(scaleAndReflect);

    lastTransform = fullTranslate;
    return new AffineTransform(fullTranslate);
  }

}
