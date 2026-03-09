package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import geography.AbstractMapProjection;
import geography.ConicalEqualAreaProjection;
import geography.GeographicShape;
import geography.GeographicShapesReader;
import geography.SinusoidalProjection;
import gui.CartographyDocument;
import gui.CartographyPanel;
import gui.GeographicShapeCartographer;

public class PA3App implements ActionListener, Runnable
{
  private JFileChooser fileChooser;
  private JFrame f;
  private CartographyPanel panel;

  public void actionPerformed(ActionEvent evt)
  {
    String ac = evt.getActionCommand();

    if (ac.equals("Open"))
    {
      int retval = fileChooser.showOpenDialog(f);
      if (retval == JFileChooser.APPROVE_OPTION)
      {
        String name = fileChooser.getSelectedFile().getName();
        System.out.println("Selected file: " + name);
        AbstractMapProjection proj;
        if (name.indexOf("world") >= 0)
          proj = new SinusoidalProjection();
        else
        {
          proj = new ConicalEqualAreaProjection(-96.0, 37.5, 29.5, 45.5);
          System.out.println("Doing ConicalEqualAreaProjection");
        }
        // proj = new SinusoidalProjection();
        try
        {
          FileInputStream in = new FileInputStream(name);
          GeographicShapesReader reader = new GeographicShapesReader(in, proj);
          CartographyDocument<GeographicShape> model = reader.read();
          panel.setModel(model);
        }
        catch (FileNotFoundException fnfe)
        {
          JOptionPane.showMessageDialog(f, "Unable to open file!", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    }

    if (ac.equals("Exit"))
    {
      f.dispose();
      System.exit(0);
    }
  }

  public void run()
  {
    f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(600, 600);

    fileChooser = new JFileChooser();

    try
    {

      InputStream in = new FileInputStream(new File("world-countries.geo"));
      AbstractMapProjection proj = new SinusoidalProjection();
      GeographicShapesReader reader = new GeographicShapesReader(in, proj);
      CartographyDocument<GeographicShape> model = reader.read();

      panel = new CartographyPanel<GeographicShape>(model,
          new GeographicShapeCartographer(Color.BLACK));

      JPanel cp = (JPanel) f.getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(panel, BorderLayout.CENTER);

      JMenuBar menuBar = new JMenuBar();
      JMenuItem item;
      JMenu menu;

      menu = new JMenu("File");
      menuBar.add(menu);
      item = new JMenuItem("Open");
      item.addActionListener(this);
      menu.add(item);

      item = new JMenuItem("Exit");
      item.addActionListener(this);
      menu.add(item);

      f.setJMenuBar(menuBar);
      f.setVisible(true);
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(f, ioe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}
