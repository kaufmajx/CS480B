package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import geography.*;

public interface Cartographer<GeographicShape>
{
  public abstract void paintHighlights(CartographyDocument<GeographicShape> model, Graphics2D g2,
      AffineTransform af);

  public abstract void paintShapes(CartographyDocument<GeographicShape> model, Graphics2D g2,
      AffineTransform af);
}
