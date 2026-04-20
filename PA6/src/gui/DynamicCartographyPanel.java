package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;

public class DynamicCartographyPanel<T> extends CartographyPanel<T> implements GPSObserver
{
  private static final long serialVersionUID = 1L;
  private GPGGASentence gpgga;
  private MapProjection proj;

  /**
   * IDK WHAT TO DO HERE.
   * 
   * @param model
   * @param cartographer
   * @param proj
   */
  public DynamicCartographyPanel(CartographyDocument<T> model, Cartographer<T> cartographer,
      MapProjection proj)
  {
    super(model, cartographer);
    this.proj = proj;
  }

  @Override
  public void handleGPSData(String sentence)
  {
    GPGGASentence parsed = GPGGASentence.parseGPGGA(sentence);

    if (parsed != null)
    {
      this.gpgga = parsed;
      repaint();
    }
  }

  /**
   * Paint the graphics.
   * 
   * @param g
   *          graphics
   */
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    // No data yet
    if (gpgga == null)
    {
      return;
    }

    // Ignore invalid fix
    // if (gpgga.getFixType() == 0)
    // {
    // return;
    // }

    System.out.println("Not Null");

    double currLat = gpgga.getLatitude();
    double currLng = gpgga.getLongitude();
    System.out.println(currLat + ", " + currLng);
    double[] km = proj.forward(new double[] {currLng, currLat});

    Graphics2D g2 = (Graphics2D) g;

    Rectangle screenBounds = g2.getClipBounds();
    AffineTransform at = displayTransform.getTransform(screenBounds, zoomStack.getFirst());

    Point2D transCoords = new Point2D.Double();
    at.transform(new Point2D.Double(km[0], km[1]), transCoords);

    g2.setColor(Color.RED);
    g2.fill(new Ellipse2D.Double((int) transCoords.getX() - 4, (int) transCoords.getY() - 4, 8, 8));
  }

}
