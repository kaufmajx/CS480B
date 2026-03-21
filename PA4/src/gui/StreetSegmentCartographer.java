package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import feature.StreetThemeLibrary;
import geography.ThemeLibrary;

public class StreetSegmentCartographer implements Cartographer
{
  private ThemeLibrary themes;

  public StreetSegmentCartographer()
  {
    themes = new StreetThemeLibrary();
  }

  @Override
  public void paintHighlights(CartographyDocument model, Graphics2D g2, AffineTransform af)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void paintShapes(CartographyDocument model, Graphics2D g2, AffineTransform af)
  {
    // TODO Auto-generated method stub

  }

}
