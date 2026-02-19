package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * DigitizerPanel.
 *
 * @author Tenley Kennett & Jelal Kaufman
 * @version 2/19
 */
public class DigitizerPanel extends JPanel implements MouseMotionListener, MouseListener
{

  public static final int ADD = 0;
  public static final int DELETE = 1;
  private static final long serialVersionUID = 1L;

  private BufferedImage ortho;
  private DigitizerDocument model;
  private int mode;

  private Point newLineStart;
  private Point newLineEnd;
  private ArrayList<double[]> currLine;

  private boolean drawing;

  /**
   * Constructs a {@code DigitizerPanel} with the given orthophoto as the background image.
   * Initializes the document model and registers mouse listeners.
   *
   * @param ortho
   *          the {@link BufferedImage} to display as the background; must not be {@code null}
   */
  public DigitizerPanel(final BufferedImage ortho)
  {
    this.ortho = ortho;
    this.model = createDefaultModel();
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  private void calcNewLinePoints(final Point p1, final Point p2)
  {
    currLine = new ArrayList<>();
    if (p1 != null && p2 != null)
    {
      double[] d1 = new double[] {p1.getX(), p1.getY()};
      double[] d2 = new double[] {p2.getX(), p2.getY()};
      currLine.add(d1);
      currLine.add(d2);
    }
  }

  /**
   * Returns the underlying {@link DigitizerDocument} model used by this panel.
   *
   * @return the default {@link DigitizerDocument} instance
   */
  public DigitizerDocument createDefaultModel()
  {
    return new DisplayDigitizerDocument(this);
  }

  /**
   * Returns all line segments currently stored in the document model.
   *
   * @return a {@link List} of {@link Line2D.Double} objects
   */
  public List<Line2D.Double> getLines()
  {
    return this.model.getLines();
  }

  /**
   * Paints the panel by drawing the background orthophoto, any completed line segments (in green),
   * and the line currently being drawn if a drag is in progress (in yellow).
   *
   * @param g
   *          the {@link Graphics} context to paint on
   */
  @Override
  public void paint(final Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;

    // clears view
    super.paint(g2);

    // draw photo
    g2.drawImage(ortho, null, 0, 0);

    // add lines
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // add line currently being drawn
    if (drawing)
    {

      g2.setPaint(Color.YELLOW);
      g2.draw(new Line2D.Double(currLine.get(0)[0], currLine.get(0)[1], currLine.get(1)[0],
          currLine.get(1)[1]));

    }
    for (Line2D line : model.getLines())
    {
      g2.setPaint(Color.GREEN);
      g2.draw(line);
    }

  }

  /**
   * Sets the interaction mode of this panel.
   *
   * @param mode
   *          either {@link #ADD} or {@link #DELETE}
   */
  public void setMode(final int mode)
  {
    this.mode = mode;
  }

  @Override
  public void mouseClicked(final MouseEvent e)
  {
    if (mode == ADD)
    {
      newLineStart = e.getPoint();
    }
    else if (mode == DELETE)
    {
      model.removeLine(model.getClosest(new double[] {e.getPoint().getX(), e.getPoint().getY()}));
    }
    repaint();
  }

  @Override
  public void mousePressed(final MouseEvent e)
  {
    if (mode == ADD)
    {
      newLineStart = e.getPoint();
      drawing = true;
      calcNewLinePoints(newLineStart, newLineStart);
    }
  }

  @Override
  public void mouseReleased(final MouseEvent e)
  {
    if (mode == ADD)
    {
      drawing = false;
      newLineEnd = e.getPoint();
      calcNewLinePoints(newLineStart, newLineEnd);

      model.addLine(currLine.get(0), currLine.get(1));

      paint(getGraphics());
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(final MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseDragged(final MouseEvent e)
  {
    if (mode == ADD)
    {
      newLineEnd = e.getPoint();
      calcNewLinePoints(newLineStart, newLineEnd);

      paint(getGraphics());
    }

  }

  @Override
  public void mouseMoved(final MouseEvent e)
  {

  }

}
