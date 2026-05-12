package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.fazecast.jSerialComm.SerialPort;

import feature.Street;
import feature.StreetSegment;
import feature.StreetsReader;
import geography.AbstractMapProjection;
import geography.ConicalEqualAreaProjection;
import geography.GeographicShape;
import geography.GeographicShapesReader;
import gps.GPGGASentence;
import gps.GPSObserver;
import gps.GPSReaderTask;
import gps.GPSSimulator;
import gui.CartographyDocument;
import gui.DynamicCartographyPanel;
import gui.StreetSegmentCartographer;

/**
 * The application for PA6.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class PA6App implements Runnable, GPSObserver
{
  private DynamicCartographyPanel<StreetSegment> panel;
  private CartographyDocument<StreetSegment> document;
  private JFrame frame;
  private MapMatcher matcher;
  private Map<String, Street> streets;

  /**
   * The code to be executed in the event dispatch thread.
   */
  @Override
  public void run()
  {
    try
    {
      InputStream isgeo = new FileInputStream(new File("rockingham-streets.geo"));
      AbstractMapProjection proj = new ConicalEqualAreaProjection(-96.0, 37.5, 29.5, 45.5);
      GeographicShapesReader gsReader = new GeographicShapesReader(isgeo, proj);
      CartographyDocument<GeographicShape> geographicShapes = gsReader.read();
      System.out.println("Read the .geo file");

      InputStream iss = new FileInputStream(new File("rockingham-streets.str"));
      StreetsReader sReader = new StreetsReader(iss, geographicShapes);

      streets = new HashMap<String, Street>();
      
   // TEMP: find where the GPS area projects to, and where the streets actually are
      double[] gpsKm = proj.forward(new double[]{-78.868, 38.442});
      System.out.printf("GPS area in km:       %.4f, %.4f%n", gpsKm[0], gpsKm[1]);
      
      
      document = sReader.read(streets);
      matcher = new MapMatcher(streets, proj);
      System.out.println("Read the .str file");

      panel = new DynamicCartographyPanel<StreetSegment>(document, new StreetSegmentCartographer(),
          proj);
      frame = new JFrame("Map");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 600);
      frame.setContentPane(panel);

      // Find the right serial port
      SerialPort[] ports = SerialPort.getCommPorts();
      String gpsPath = null;
      for (SerialPort port : ports)
      {
        String description = port.getPortDescription();
        String path = port.getSystemPortPath();
        if (description.indexOf("GPS") >= 0)
          gpsPath = path;
      }

      // Setup the serial port
      // SerialPort gps = SerialPort.getCommPort(gpsPath);
      // gps.openPort();
      // gps.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
      // InputStream is = gps.getInputStream();

      GPSSimulator gps = new GPSSimulator("rockingham.gps");
      InputStream is = gps.getInputStream();

      // Setup the GPSReaderTask
      // GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
      // gpsReader.addGPSObserver(this);
      // frame.setVisible(true);
      // gpsReader.execute();

      GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
      // gpsReader.addGPSObserver(panel);
      gpsReader.addGPSObserver(this);
      frame.setVisible(true);
      gpsReader.execute();

      frame.setVisible(true);
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(frame, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void handleGPSData(String sentence)
  {
    GPGGASentence gpgga = GPGGASentence.parseGPGGA(sentence);
    if (gpgga == null)
    {
      return;
    }

    double lon = gpgga.getLongitude();
    double lat = gpgga.getLatitude();
    
    if (lon == 0 && lat == 0)
    {
      return; // nothing happening yet
    }
    
    System.out.println("Lat " + lat);
    System.out.println("Lon " + lon);

    if (matcher.match(lon, lat))
    {
      String snapped = GPGGASentence.buildGPGGA(matcher.matchedLat, matcher.matchedLon);
      panel.handleGPSData(snapped);
      System.out.println("MatchedLon " + matcher.matchedLon);
      System.out.println("MatchedLat " + matcher.matchedLat);
//      System.out.printf("Matched arc %s  residual=%.1f m%n", matcher.matchedSegment.getID(),
//          matcher.matchedDist * 111000);
    }
    else
    {
      panel.handleGPSData(sentence);
      System.out.println("No match — using raw GPS");
    }

  }

}
