package gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import feature.StreetSegment;
import feature.StreetThemeLibrary;
import geography.Theme;
import geography.ThemeLibrary;

public class StreetSegmentCartographer<T> implements Cartographer<StreetSegment>
{
  private ThemeLibrary themes;

  public StreetSegmentCartographer()
  {
    themes = new StreetThemeLibrary();
  }

  @Override
  public void paintHighlights(final CartographyDocument<StreetSegment> model, final Graphics2D g2,
      final AffineTransform af)
  {
    Theme currTheme = themes.getHighlightTheme();
    g2.setColor(currTheme.getColor());
    g2.setStroke(currTheme.getStroke());

    Iterator<StreetSegment> it = model.highlighted();
    while (it.hasNext())
    {
      Shape transformed = af.createTransformedShape(it.next().getGeographicShape().getShape());
      g2.fill(transformed);
      g2.draw(transformed);
    }

  }

  @Override
  public void paintShapes(final CartographyDocument<StreetSegment> model, final Graphics2D g2,
      final AffineTransform af)
  {
    for (StreetSegment ss : model)
    {
      Shape transformed = af.createTransformedShape(ss.getGeographicShape().getShape());
      Theme currTheme = themes.getTheme(ss.getCode());
      g2.setColor(currTheme.getColor());
      g2.setStroke(currTheme.getStroke());
      g2.draw(transformed);
    }
  }

}
