package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import geography.GeographicShape;

public class GeographicShapeCartographer<T> implements Cartographer<GeographicShape>

{
  private Color color;

  public GeographicShapeCartographer(Color color)
  {
    this.color = color;
  }

  @Override
  public void paintHighlights(CartographyDocument<GeographicShape> model, Graphics2D g2,
      AffineTransform af)
  {
    Color highlight = new Color(color.getRed(), color.getBlue(), color.getGreen());
    g2.setColor(highlight);

    Iterator<GeographicShape> it = model.highlighted();
    while (it.hasNext())
    {
      Shape transformed = af.createTransformedShape(it.next().getShape());
      g2.fill(transformed); // fill to make highlights stand out
      g2.draw(transformed);
    }

  }

  @Override
  public void paintShapes(CartographyDocument<GeographicShape> model, Graphics2D g2,
      AffineTransform af)
  {
    g2.setColor(color);
    // AffineTransform old = g2.getTransform();
    // g2.transform(af);

    for (GeographicShape t : model)
    {
      Shape transformed = af.createTransformedShape(t.getShape());
      g2.draw(transformed);
      // System.out.print("Shape drawn: " + t.getID());
    }

    // g2.setTransform(old);
  }

}
