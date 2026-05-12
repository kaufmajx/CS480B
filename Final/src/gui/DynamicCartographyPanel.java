package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;

/**
 * DynamicCartographyPanel class.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 * 
 * @param <T>
 *          StreetSegmentCartographer
 */
public class DynamicCartographyPanel<T> extends CartographyPanel<T> implements GPSObserver
{
  private static final long serialVersionUID = 1L;
  private GPGGASentence gpgga;
  private MapProjection proj;

  /**
   * Constructor for DynamicCartographyPanel.
   *
   * @param model
   *          the cartography document model containing the map data
   * @param cartographer
   *          the cartographer responsible for rendering map elements
   * @param proj
   *          the map projection used to convert geographic coordinates to screen coordinates
   */
  public DynamicCartographyPanel(final CartographyDocument<T> model,
      final Cartographer<T> cartographer, final MapProjection proj)
  {
    super(model, cartographer);
    this.proj = proj;
  }

  @Override
  public void handleGPSData(final String sentence)
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
  public void paint(final Graphics g)
  {
    // No data yet
    if (gpgga == null)
    {
      super.paint(g);
      return;
    }

    double currLat = gpgga.getLatitude();
    double currLng = gpgga.getLongitude();

    if (currLat == 0.0 || currLng == 0.0)
    {
      super.paint(g);
      return;
    }
    double[] km = proj.forward(new double[] {currLng, currLat});

    Rectangle2D.Double mapBounds = new Rectangle2D.Double(km[0] - 1.0, km[1] - 1.0, 2.0, 2.0);
    zoomStack.set(0, mapBounds);

    super.paint(g);

    Graphics2D g2 = (Graphics2D) g;
    Rectangle screenBounds = g2.getClipBounds();
    AffineTransform at = displayTransform.getTransform(screenBounds, zoomStack.getFirst());

    Point2D transCoords = new Point2D.Double();
    at.transform(new Point2D.Double(km[0], km[1]), transCoords);

    g2.setColor(Color.RED);
    g2.fill(new Ellipse2D.Double(transCoords.getX() - 4, transCoords.getY() - 4, 8, 8));
  }

}
