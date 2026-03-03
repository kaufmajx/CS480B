package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import geography.*;

public interface Cartographer<T>
{
  public abstract void paintHighlights(CartographyDocument<T> model, Graphics2D g2,
      AffineTransform af);

  public abstract void paintShapes(CartographyDocument<T> model, Graphics2D g2,
      AffineTransform af);
}
