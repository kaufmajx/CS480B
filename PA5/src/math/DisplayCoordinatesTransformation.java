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
    double scaleX = displayBounds.getWidth() / contentBounds.getWidth();
    double scaleY = displayBounds.getHeight() / contentBounds.getHeight();
    double scale = Math.min(scaleX, scaleY);

    AffineTransform at = new AffineTransform();

    // Step 1: move to display origin
    at.translate(displayBounds.getX(), displayBounds.getY());

    // Step 2: move origin to bottom-left of display (because of Y flip)
    at.translate(0, displayBounds.getHeight());

    // Step 3: scale + flip Y
    at.scale(scale, -scale);

    // Step 4: shift content into view
    at.translate(-contentBounds.getMinX(), -contentBounds.getMinY());

    // Store reflection only
    lastReflection = new AffineTransform();
    lastReflection.scale(1.0, -1.0);

    lastTransform = new AffineTransform(at);
    return at;
  }

}
