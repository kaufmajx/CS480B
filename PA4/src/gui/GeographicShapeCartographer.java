package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import geography.GeographicShape;

/**
 * A cartographer that paints geographic shapes onto a graphics context.
 * 
 * @param <T>
 *          the type parameter for the cartographer
 */
public class GeographicShapeCartographer<T> implements Cartographer<GeographicShape>

{
  private Color color;

  /**
   * Constructs a GeographicShapeCartographer with the given color.
   * 
   * @param color
   *          the color used to draw shapes
   */
  public GeographicShapeCartographer(final Color color)
  {
    this.color = color;
  }

  @Override
  public void paintHighlights(final CartographyDocument<GeographicShape> model, final Graphics2D g2,
      final AffineTransform af)
  {
    Color highlight = new Color(255, 255, 0, 128);
    g2.setColor(highlight);

    Iterator<GeographicShape> it = model.highlighted();
    while (it.hasNext())
    {
      Shape transformed = af.createTransformedShape(it.next().getShape());
      g2.fill(transformed);
      g2.draw(transformed);
    }

  }

  @Override
  public void paintShapes(final CartographyDocument<GeographicShape> model, final Graphics2D g2,
      final AffineTransform af)
  {
    g2.setColor(color);

    for (GeographicShape t : model)
    {
      Shape transformed = af.createTransformedShape(t.getShape());
      g2.draw(transformed);
      System.out.println("Shape drawn: " + t.getID());
    }
  }

}
