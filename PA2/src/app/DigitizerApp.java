package app;

import gui.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * An application that can be used to digitize something 
 * (in display coordinates).
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class DigitizerApp implements ActionListener, Runnable
{
  private DigitizerPanel dp;
  private JRadioButton addButton, deleteButton;
  
  /**
   * The code to run in the event dispatch thread.
   */
  public void run()
  {
    // Setup the JFrame
    JFrame frame = new JFrame("Way - Digitizer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 700);
    JPanel contentPane = (JPanel)frame.getContentPane();
    contentPane.setLayout(new BorderLayout());
    
    // Setup the ortho photo
    BufferedImage ortho;
    try
    {
      ortho = ImageIO.read(new File("orthophoto.png"));
    }
    catch (IOException io)
    {
      ortho = null;
    }
    
    // Setup the DigitizerPanel
    dp = new DigitizerPanel(ortho);
    contentPane.add(dp, BorderLayout.CENTER);
    
    // Setup the opearing mode panel
    JPanel south = new JPanel();
    south.setLayout(new FlowLayout(FlowLayout.LEFT));
    south.setBorder(BorderFactory.createTitledBorder("Operating Mode"));
    addButton = new JRadioButton("Add Line", true);
    addButton.addActionListener(this);
    south.add(addButton);
    deleteButton = new JRadioButton("Delete Line", false);
    deleteButton.addActionListener(this);
    south.add(deleteButton);
    ButtonGroup modeGroup = new ButtonGroup();
    modeGroup.add(addButton);
    modeGroup.add(deleteButton);
    contentPane.add(south, BorderLayout.SOUTH);
    
    frame.setVisible(true);
  }
  
  /**
   * Handle actionPerformed messages. Specifically, change the
   * operating mode of the DigitizerPanel.
   * 
   * @param evt The event that generated the message
   */
  public void actionPerformed(final ActionEvent evt)
  {
    if (addButton.isSelected()) dp.setMode(DigitizerPanel.ADD);
    else dp.setMode(DigitizerPanel.DELETE);
  }

}
