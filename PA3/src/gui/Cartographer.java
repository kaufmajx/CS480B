package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Defines the contract for painting cartographic elements onto a graphics context.
 * 
 * @param <T>
 *          the type of geographic elements to be painted
 */
public interface Cartographer<T>
{
  /**
   * Paints the highlighted elements of the given document onto the graphics context.
   * 
   * @param model
   *          the cartography document containing elements to highlight
   * @param g2
   *          the graphics context to paint onto
   * @param af
   *          the affine transform to apply to shapes before painting
   */
  public abstract void paintHighlights(final CartographyDocument<T> model, final Graphics2D g2,
      AffineTransform af);

  /**
   * Paints all shapes in the given document onto the graphics context.
   * 
   * @param model
   *          the cartography document containing elements to paint
   * @param g2
   *          the graphics context to paint onto
   * @param af
   *          the affine transform to apply to shapes before painting
   */
  public abstract void paintShapes(final CartographyDocument<T> model, final Graphics2D g2, final AffineTransform af);
}
