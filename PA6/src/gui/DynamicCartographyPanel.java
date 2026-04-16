package gui;

import java.awt.Graphics;

import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;

public class DynamicCartographyPanel<T> extends CartographyPanel<T> implements GPSObserver
{

  public CartographyDocument<T> model;
  public Cartographer<T> cartographer;
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
    this.model = model;
    this.cartographer = cartographer;
    this.proj = proj;
  }

  @Override
  public void handleGPSData(String sentence)
  {
    // parse the NMEA sentence it is passed and store it as an attribute
//    this.gpgga = ???;
  }

  /**
   * Paint the graphics.
   * 
   * @param g
   *          graphics
   */
  public void paint(Graphics g)
  {
    
    //The paint() method must:
//    1. Add a Rectangle2D.Double object to the first element of the zoomStack that is
//    centered on the current location/position and is 2km wide and 2km high.
//    2. Call the parent paint() method to invoke the street network.
//    3. Project and transform the current position/location.
//    4. Render a filled circle (in red) that is centered on the current location and is 8 pixels wide and 8
//    pixels high.

  }

}
