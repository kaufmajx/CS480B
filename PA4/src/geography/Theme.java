package geography;

import java.awt.Color;
import java.awt.Stroke;

/**
 * Represents a visual theme defined by a color and a stroke style.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class Theme
{
  private Color color;
  private Stroke stroke;

  /**
   * Constructs a Theme with the given color and stroke.
   *
   * @param color
   *          the color for this theme
   * @param stroke
   *          the stroke style for this theme
   */
  public Theme(final Color color, final Stroke stroke)
  {
    this.color = color;
    this.stroke = stroke;
  }

  /**
   * Returns the color for this theme.
   *
   * @return the color
   */
  public Color getColor()
  {
    return color;
  }

  /**
   * Returns the stroke style for this theme.
   *
   * @return the stroke
   */
  public Stroke getStroke()
  {
    return stroke;
  }
}
