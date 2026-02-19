package gui;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DisplayDigitizerDocument implements DigitizerDocument
{

  protected DigitizerPanel panel;
  protected List<Line2D.Double> lines;

  public DisplayDigitizerDocument(final DigitizerPanel panel)
  {
    this.panel = panel;
    lines = new ArrayList<>();
  }

  @Override
  public void addLine(final double[] start, final double[] stop)
  {
    Line2D.Double newLine = new Line2D.Double(start[0], start[1], stop[0], stop[1]);
    lines.add(newLine);
  }

  @Override
  public Line2D.Double getClosest(final double[] point)
  {
    Line2D.Double closestLine = null;
    double closestDistance = Double.POSITIVE_INFINITY;
    for (Line2D.Double line : getLines())
    {

      double dist1 = Math
          .sqrt(Math.pow((point[0] - line.getX1()), 2) + Math.pow((point[1] - line.getY1()), 2));
      double dist2 = Math
          .sqrt(Math.pow((point[0] - line.getX2()), 2) + Math.pow((point[1] - line.getY2()), 2));
      if ((dist1 + dist2) < closestDistance)
      {
        closestLine = line;
        closestDistance = dist1 + dist2;
      }
    }
    return closestLine;
  }

  @Override
  public List<Line2D.Double> getLines()
  {
    return this.lines;
  }

  @Override
  public void removeLine(final Line2D.Double line)
  {
    this.lines.remove(line);
  }
}
