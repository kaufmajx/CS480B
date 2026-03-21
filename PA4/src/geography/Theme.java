package geography;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class Theme
{
  private Color color;
  private Stroke stroke;

  public Theme(Color color, Stroke stroke)
  {
    this.color = color;
    this.stroke = stroke;
  }

  public Color getColor()
  {
    return color;
  }
  
  public Stroke getStroke()
  {
    return stroke;
  }
}
