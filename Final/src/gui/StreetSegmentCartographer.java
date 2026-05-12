package gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import feature.StreetSegment;
import feature.StreetThemeLibrary;
import geography.Theme;
import geography.ThemeLibrary;

/**
 * Renders StreetSegment objects onto a Graphics2D context using themes from a StreetThemeLibrary.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 * @param <T>
 *          unused type parameter, present for compatibility with Cartographer
 */
public class StreetSegmentCartographer<T> implements Cartographer<StreetSegment>
{
  private ThemeLibrary themes;

  /**
   * Constructs a StreetSegmentCartographer with a default StreetThemeLibrary.
   */
  public StreetSegmentCartographer()
  {
    themes = new StreetThemeLibrary();
  }

  /**
   * Paints the highlighted street segments using the highlight theme.
   *
   * @param model
   *          the document containing the highlighted segments to draw
   * @param g2
   *          the graphics context to draw on
   * @param af
   *          the transform to convert from geographic to screen coordinates
   */
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

  /**
   * Paints all street segments in the document using their corresponding themes.
   *
   * @param model
   *          the document containing all segments to draw
   * @param g2
   *          the graphics context to draw on
   * @param af
   *          the transform to convert from geographic to screen coordinates
   */
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
