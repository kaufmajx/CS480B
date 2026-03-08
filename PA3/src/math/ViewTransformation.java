package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Defines the contract for computing view transformations between content and display coordinates.
 */
public interface ViewTransformation
{
  /**
   * Returns the last reflection transform computed by this transformation.
   * 
   * @return the last reflection as an AffineTransform
   */
  public abstract AffineTransform getLastReflection();

  /**
   * Returns the last full transform computed by this transformation.
   * 
   * @return the last transform as an AffineTransform
   */
  public abstract AffineTransform getLastTransform();

  /**
   * Computes and returns the transform that maps content bounds to display bounds.
   * 
   * @param dispalyBounds
   *          the target display rectangle
   * @param contentBounds
   *          the source content rectangle
   * @return the computed AffineTransform
   */
  public abstract AffineTransform getTransform(Rectangle2D displayBounds, Rectangle2D contentBounds);
}
