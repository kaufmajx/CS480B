package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.datatransfer.StringSelection;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import feature.StreetSegment;
import feature.StreetThemeLibrary;
import geography.GeographicShape;
import geography.Theme;
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
    Theme currTheme = themes.getHighlightTheme();
    g2.setColor(currTheme.getColor());
    g2.setStroke(currTheme.getStroke());
    
    Color highlight = new Color(255, 255, 0, 128); // semi-transparent yellow
    g2.setColor(highlight);

    Iterator<GeographicShape> it = model.highlighted();
    while (it.hasNext())
    {
      Shape transformed = af.createTransformedShape(it.next().getShape());
//      g2.fill(transformed); // fill to make highlights stand out
      g2.draw(transformed);
    }

  }

  @Override
  public void paintShapes(CartographyDocument model, Graphics2D g2, AffineTransform af)
  {
    Iterator<StreetSegment> it = model.iterator();
    while (it.hasNext())
    {
      Shape transformed = af.createTransformedShape(it.next().getGeographicShape().getShape());
//      g2.fill(transformed); // fill to make highlights stand out
      g2.draw(transformed);
    }

  }

}
