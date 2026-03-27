package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import dataprocessing.Geocoder;
import feature.*;
import geography.*;
import gui.*;

/**
 * The application for PA4.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class PA4App implements ActionListener, Runnable, StreetSegmentObserver
{
  private static final String EXIT = "Exit";
  private static final String SHOW = "Show";
  
  private CartographyPanel<StreetSegment> panel;
  private CartographyDocument<StreetSegment> segments;
  private GeocodeDialog dialog;
  private JFrame frame;

  /**
   * Handle actionPerformed() messages.
   * 
   * @param evt The event that generated the message
   */
  public void actionPerformed(final ActionEvent evt)
  {
    String ac = evt.getActionCommand();
    
    if (ac.equals(SHOW))
    {
      if (!dialog.isVisible())
      {
        dialog.setLocation((int)frame.getBounds().getMaxX(), (int)frame.getBounds().getY());
        dialog.setVisible(true);
      }
    }
    
    if (ac.equals(EXIT))
    {
      dialog.dispose();
      frame.dispose();
      System.exit(0);
    }
  }
  
  /**
   * The code to be executed in the event dispatch thread.
   */
  @Override
  public void run()
  {
    try
    {
      InputStream isgeo = new FileInputStream(new File("rockingham-streets-2024.geo"));
      //InputStream isgeo = new FileInputStream(new File("va-streets-2024.geo"));
      AbstractMapProjection proj = new ConicalEqualAreaProjection(-96.0, 37.5, 29.5, 45.5);
      GeographicShapesReader gsReader = new GeographicShapesReader(isgeo, proj);
      CartographyDocument<GeographicShape> geographicShapes = gsReader.read();
      System.out.println("Read the .geo file");
      
      InputStream iss = new FileInputStream(new File("rockingham-streets-2024.str"));
//      InputStream iss = new FileInputStream(new File("va-streets-2024.str"));
      StreetsReader sReader = new StreetsReader(iss, geographicShapes);
      Map<String, Street> streets = new HashMap<String, Street>();
      segments = sReader.read(streets);
      System.out.println("Read the .str file");

      panel = new CartographyPanel<StreetSegment>(segments, new StreetSegmentCartographer());
      frame = new JFrame("Map");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 600);

      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);
      JMenuItem item;
      JMenu menu;
      
      menu = new JMenu("File");
      menuBar.add(menu);
      
      item = new JMenuItem(EXIT);
      item.addActionListener(this);
      menu.add(item);

      menu = new JMenu("Geocoder");
      menuBar.add(menu);
      
      item = new JMenuItem(SHOW);
      item.addActionListener(this);
      menu.add(item);

      frame.setContentPane(panel);
      frame.setVisible(true);
      
      Geocoder geocoder = new Geocoder(geographicShapes, segments, streets);
      dialog = new GeocodeDialog(frame, geocoder);
      System.out.println("geocoder made");
      dialog.addStreetSegmentObserver(this);
      dialog.setLocation((int)frame.getBounds().getMaxX(), (int)frame.getBounds().getY());
      dialog.setVisible(true);
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(frame, 
          ioe.toString(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  /**
   * Handle a collection of StreetSegment objects.
   * 
   * @param segmentIDs The IDs of the StreetSegment objects
   */
  @Override
  public void handleStreetSegments(final List<String> segmentIDs)
  {
    HashMap<String, StreetSegment> highlighted = new HashMap<String, StreetSegment>();
    for (String id: segmentIDs) highlighted.put(id, segments.getElement(id));
    segments.setHighlighted(highlighted);
    panel.repaint();
  }

}
