package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class GeographicShapeCartographer<GeographicShape> implements Cartographer<GeographicShape>
{
  private Color color;

  public GeographicShapeCartographer(Color color)
  {
    this.color = color;
  }

  @Override
  public void paintHighlights(CartographyDocument<GeographicShape> model, Graphics2D g2, AffineTransform af)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void paintShapes(CartographyDocument<GeographicShape> model, Graphics2D g2, AffineTransform af)
  {
    // TODO Auto-generated method stub

  }

}
