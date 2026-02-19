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

public class DigitizerPanel extends JPanel implements MouseMotionListener, MouseListener
{

  private static final long serialVersionUID = 1L;
  public static final int ADD = 0;
  public static final int DELETE = 1;

  private BufferedImage ortho;
  private DigitizerDocument model;
  private int mode;

  private Point newLineStart;
  private Point newLineEnd;
  private ArrayList<double[]> currLine;

  private boolean drawing;

  public DigitizerPanel(BufferedImage ortho)
  {
    this.ortho = ortho;
    this.model = new DisplayDigitizerDocument(this);

    addMouseListener(this);
    addMouseMotionListener(this);
  }

  private void calcNewLinePoints(Point p1, Point p2)
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

  public DigitizerDocument createDefaultMode()
  {
    return model;
  }

  public List<Line2D> getLines()
  {
    return this.model.getLines();
  }

  public void paint(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;

    // clears view
    super.paint(g2);

    // draw photo
    g2.drawImage(ortho, null, ADD, ADD);

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

  public void setMode(int mode)
  {
    this.mode = mode;
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
    if (mode == ADD)
    {
      newLineStart = e.getPoint();
    }
    else if (mode == DELETE)
    {
      model.removeLine(model.getClosest(new double[] {e.getPoint().getX(), e.getPoint().getY()}));
    }
    model.addLine(new double[] {3, 3}, new double[] {8, 5});
    paint(getGraphics());
  }

  @Override
  public void mousePressed(MouseEvent e)
  {
    if (mode == ADD)
    {
      newLineStart = e.getPoint();
      drawing = true;
      calcNewLinePoints(newLineStart, newLineStart);
    }
    // else if (mode == DELETE) {
    // newLineStart = e.getPoint();
    // }
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    if (mode == ADD)
    {
      drawing = false;
      newLineEnd = e.getPoint();
      calcNewLinePoints(newLineStart, newLineEnd);

      model.addLine(currLine.get(0), currLine.get(1));

      paint(getGraphics());
    }
    // else if (mode == DELETE) {
    // newLineEnd = e.getPoint();
    // if ((newLineStart.getX() == newLineEnd.getX()) && (newLineStart.getY() == newLineEnd.getY()))
    // {
    // model.removeLine(model.getClosest(new double[] { newLineEnd.getX(), newLineEnd.getY() }));
    // }
    // }

  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseExited(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseDragged(MouseEvent e)
  {
    if (mode == ADD)
    {
      newLineEnd = e.getPoint();
      calcNewLinePoints(newLineStart, newLineEnd);

      paint(getGraphics());
    }
    else if (mode == DELETE)
    {

    }

  }

  @Override
  public void mouseMoved(MouseEvent e)
  {
    // TODO Auto-generated method stub

  }

}
