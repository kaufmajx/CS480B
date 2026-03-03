package gui;

import java.awt.Color;
import java.awt.Graphics2D;
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
    // TODO Auto-generated method stub

  }

  @Override
  public void paintShapes(CartographyDocument<GeographicShape> model, Graphics2D g2,
      AffineTransform af)
  {
    AffineTransform old = g2.getTransform();
    g2.transform(af);
    g2.setColor(color);

    Iterator<GeographicShape> shapes = model.iterator();
    while (shapes.hasNext())
    {
      g2.draw(shapes.next().getShape());
    }

    g2.setTransform(old);
  }

}
