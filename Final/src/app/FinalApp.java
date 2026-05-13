package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import dataprocessing.Geocoder;
import feature.Street;
import feature.StreetSegment;
import feature.StreetSegmentObserver;
import feature.StreetsReader;
import geography.AbstractMapProjection;
import geography.ConicalEqualAreaProjection;
import geography.GeographicShape;
import geography.GeographicShapesReader;
import gps.GPGGASentence;
import gps.GPSObserver;
import gps.GPSReaderTask;
import gps.GPSSimulator;
import graph.LabelSettingAlgorithm;
import graph.PathFindingWorker;
import graph.PermanentLabelHeap;
import graph.ShortestPathAlgorithm;
import graph.StreetNetwork;
import gui.BackgroundTaskDialog;
import gui.CartographyDocument;
import gui.DynamicCartographyPanel;
import gui.GeocodeDialog;
import gui.StreetSegmentCartographer;

/**
 * FinalApp runnable.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public class FinalApp
    implements Runnable, ActionListener, StreetSegmentObserver, PropertyChangeListener, GPSObserver
{
  private static final int SET_DESTINATION = 0;
  private static final int SET_ORIGIN = 1;

  private static final String ORIGIN = "Origin";
  private static final String DESTINATION = "Destination";
  private static final String CALCULATE = "Calculate";
  private static final String GPS_ORIGIN = "Use GPS As Origin";
  private static final String EXIT = "Exit";

  private JFrame frame;
  private DynamicCartographyPanel<StreetSegment> panel;
  private CartographyDocument<StreetSegment> document;
  private GeocodeDialog dialog;

  private StreetNetwork network;
  private ShortestPathAlgorithm alg;
  private PathFindingWorker task;

  private Map<String, StreetSegment> currentRoute = new HashMap<>();
  private StreetSegment routeStartSegment;
  private boolean reroutePending;
  private int offRouteFixes;

  private static final int OFF_ROUTE_FIX_LIMIT = 3;
  private static final int HEAP_D = 4;

  private StreetSegment originSegment;
  private StreetSegment destinationSegment;
  private StreetSegment currentGPSSegment;

  // Travel direction at the moment origin was set; picks head vs tail at calc time.
  private boolean originDirectionAgrees = true;

  private MapMatcher matcher;
  private Map<String, Street> streets;

  private int mode;

  @Override
  public void run()
  {
    try
    {
      /* MAP FILES */

      InputStream isgeo = new FileInputStream("rockingham-streets.geo");

      AbstractMapProjection proj = new ConicalEqualAreaProjection(-96.0, 37.5, 29.5, 45.5);

      GeographicShapesReader gsReader = new GeographicShapesReader(isgeo, proj);

      CartographyDocument<GeographicShape> geographicShapes = gsReader.read();

      InputStream iss = new FileInputStream("rockingham-streets.str");

      StreetsReader sReader = new StreetsReader(iss, geographicShapes);

      streets = new HashMap<String, Street>();

      document = sReader.read(streets);

      matcher = new MapMatcher(streets, proj);

      network = StreetNetwork.createStreetNetwork(streets);

      /* PANEL */

      panel = new DynamicCartographyPanel<StreetSegment>(document, new StreetSegmentCartographer<Object>(), proj);

      frame = new JFrame("Navigation");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(700, 700);
      frame.setContentPane(panel);

      /* MENUS */

      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);

      JMenu menu;
      JMenuItem item;

      menu = new JMenu("File");
      menuBar.add(menu);

      item = new JMenuItem(EXIT);
      item.addActionListener(this);
      menu.add(item);

      menu = new JMenu("Geocode");
      menuBar.add(menu);

      item = new JMenuItem(ORIGIN);
      item.addActionListener(this);
      menu.add(item);

      item = new JMenuItem(DESTINATION);
      item.addActionListener(this);
      menu.add(item);

      menu = new JMenu("Path");
      menuBar.add(menu);

      item = new JMenuItem(CALCULATE);
      item.addActionListener(this);
      menu.add(item);

      menu = new JMenu("GPS");
      menuBar.add(menu);

      item = new JMenuItem(GPS_ORIGIN);
      item.addActionListener(this);
      menu.add(item);

      /* GEOCODER */

      Geocoder geocoder = new Geocoder(geographicShapes, document, streets);

      dialog = new GeocodeDialog(frame, geocoder);
      dialog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      dialog.addStreetSegmentObserver(this);

      /* GPS */
      // Find the right serial port
      // SerialPort[] ports = SerialPort.getCommPorts();
      // String gpsPath = null;
      // for (SerialPort port : ports)
      // {
      // String description = port.getPortDescription();
      // String path = port.getSystemPortPath();
      // if (description.indexOf("GPS") >= 0)
      // gpsPath = path;
      // }
      //
      // // Setup the serial port
      // SerialPort gps = SerialPort.getCommPort(gpsPath);
      // gps.openPort();
      // gps.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
      // InputStream is = gps.getInputStream();
      //
      // // Setup the GPSReaderTask
      // GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
      // gpsReader.addGPSObserver(this);
      // frame.setVisible(true);
      // gpsReader.execute();

       GPSSimulator gps = new GPSSimulator("rockingham.gps");
       InputStream is = gps.getInputStream();
      
       GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");

      gpsReader.addGPSObserver(this);

      frame.setVisible(true);

      gpsReader.execute();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /* GPS */

  @Override
  public void handleGPSData(final String sentence)
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

    if (matcher.match(lon, lat))
    {

      currentGPSSegment = matcher.matchedSegment;
      checkForReroute();
      String snapped = GPGGASentence.buildGPGGA(matcher.matchedLat, matcher.matchedLon);

      panel.handleGPSData(snapped);
    }
    else
    {
      panel.handleGPSData(sentence);
    }

  }

  /**
   * Method to check if user is off route.
   */
  private void checkForReroute()
  {
    // make sure data is good and there are no nulls
    if (destinationSegment == null || currentGPSSegment == null || currentRoute.isEmpty()
        || reroutePending)
    {
      return;
    }

    // check if user is still on the route
    if (currentRoute.containsKey(currentGPSSegment.getID()))
    {
      offRouteFixes = 0;
      return;
    }

    // for each tick spent off route, add a fix
    offRouteFixes++;

    // recalculate route if user is confirmed off the original route
    if (offRouteFixes >= OFF_ROUTE_FIX_LIMIT)
    {
      offRouteFixes = 0;
      // Route from the endpoint we are driving toward, not always head.
      int originNode = matcher.matchedDirectionAgrees ? currentGPSSegment.getHead()
          : currentGPSSegment.getTail();
      startRouteCalculation(originNode, destinationSegment.getHead(), currentGPSSegment, false);
    }
  }

  /* menus */

  @Override
  public void actionPerformed(final ActionEvent evt)
  {
    String ac = evt.getActionCommand();

    if (ac.equals(ORIGIN))
      mode = SET_ORIGIN;
    else if (ac.equals(DESTINATION))
      mode = SET_DESTINATION;

    if (ac.equals(ORIGIN) || ac.equals(DESTINATION))
    {
      dialog.setVisible(true);
    }

    if (ac.equals(GPS_ORIGIN))
    {
      originSegment = currentGPSSegment;

      // grab travel direction so Calculate uses the right endpoint later.
      originDirectionAgrees = matcher.matchedDirectionAgrees;
    }

    if (ac.equals(CALCULATE))
    {
      int originNode = originDirectionAgrees ? originSegment.getHead() : originSegment.getTail();
      startRouteCalculation(originNode, destinationSegment.getHead(), originSegment, true);
    }

    if (ac.equals(EXIT))
    {
      System.exit(0);
    }
  }

  @Override
  public void propertyChange(final PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("state")
        && evt.getNewValue().equals(SwingWorker.StateValue.DONE))
    {
      try
      {
        Map<String, StreetSegment> path = task.get();

        if (path == null)
        {
          currentRoute = new HashMap<>();
        }
        else
        {
          currentRoute = path;
          if (routeStartSegment != null)
            currentRoute.put(routeStartSegment.getID(), routeStartSegment);
        }

        document.setHighlighted(currentRoute);
        panel.repaint();
        reroutePending = false;

      }
      catch (InterruptedException | ExecutionException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Geocoder
   */
  @Override
  public void handleStreetSegments(final List<String> segmentIDs)
  {
    HashMap<String, StreetSegment> highlighted = new HashMap<>();

    for (String id : segmentIDs)
    {
      highlighted.put(id, document.getElement(id));
    }

    document.setHighlighted(highlighted);

    panel.repaint();

    if (segmentIDs.size() > 0)
    {
      if (mode == SET_ORIGIN)
      {
        originSegment = highlighted.get(segmentIDs.get(0));

        // Geocoded origin has no direction; head() is the default
        originDirectionAgrees = true;
      }
      else if (mode == SET_DESTINATION)
      {
        destinationSegment = highlighted.get(segmentIDs.get(0));
      }
    }
  }

  /**
   * Method to start recalculations if user is off route.
   * 
   * @param origin
   *          the starting node
   * @param destination
   *          the ending node
   * @param startSegment
   *          the current streetSegment
   * @param showDialog
   *          a boolean for whether or not to show the calcuation dialog box
   */
  private void startRouteCalculation(final int origin, final int destination,
      final StreetSegment startSegment, final boolean showDialog)
  {
    if (reroutePending)
    {
      return;
    }

    reroutePending = true;
    routeStartSegment = startSegment;

    // startSegment blocks an immediate U-turn back onto the segment we're on.
    alg = new LabelSettingAlgorithm(new PermanentLabelHeap(HEAP_D, network.size()), startSegment);
    task = new PathFindingWorker(alg, origin, destination, network, document, panel);
    task.addPropertyChangeListener(this);

    if (showDialog)
    {
      new BackgroundTaskDialog<Map<String, StreetSegment>, String>(frame, "Calculating...", task)
          .execute();
    }
    else
    {
      task.execute();
    }
  }
}
