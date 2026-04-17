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
    gpgga = GPGGASentence.parseGPGGA(sentence);
  }

  /**
   * Paint the graphics.
   * 
   * @param g
   *          graphics
   */
  public void paint(Graphics g)
  {
    // 1
    // double currLat = gpgga.getLatitude();
    // double currLng = gpgga.getLongitude();
    //
    //
    // AffineTransform at = displayTransform.getTransform(new Rectangle2D.Double(currLat,currLng,
    // 2000, 2000), getBounds());
    //
    // Rectangle2D.Double rect = new Rectangle2D.Double();
    // zoomStack.get(0).add(rect);
    //
    // // 2
    // super.paint(g);

    // 3

    // 4

    // The paint() method must:
    // 1. Add a Rectangle2D.Double object to the first element of the zoomStack that is
    // centered on the current location/position and is 2km wide and 2km high.
    // 2. Call the parent paint() method to invoke the street network.
    // 3. Project and transform the current position/location.
    // 4. Render a filled circle (in red) that is centered on the current location and is 8 pixels
    // wide and 8
    // pixels high.

    double currLat = gpgga.getLatitude();
    double currLng = gpgga.getLongitude();

    // Step 1 — project current position and 2km offsets into map space (km)
    double[] projectedPos = proj.forward(new double[] {currLng, currLat});

    // 1km offset in each direction to make a 2km x 2km box
    double kmOffset = 1.0;

    Rectangle2D.Double mapBounds = new Rectangle2D.Double(
        projectedPos[0] - kmOffset, // west edge
        projectedPos[1] - kmOffset, // south edge
        2 * kmOffset, // 2km wide
        2 * kmOffset // 2km tall
    );

    zoomStack.set(0, mapBounds);

    // Step 2 — call parent to render street network
    super.paint(g);

    // Step 3 — recompute the same transform the parent used
    Graphics2D g2 = (Graphics2D) g;
    Rectangle screenBounds = g2.getClipBounds();
    AffineTransform at = displayTransform.getTransform(screenBounds, mapBounds);

    // Transform projected position (already in km) to pixel coordinates
    Point2D projectedPoint = new Point2D.Double(projectedPos[0], projectedPos[1]);
    Point2D pixel = at.transform(projectedPoint, null);

    // Step 4 — draw red dot centered on pixel location
    g2.setColor(Color.RED);
    g2.fill(new Ellipse2D.Double(pixel.getX() - 4, pixel.getY() - 4, 8, 8));
  }

}
