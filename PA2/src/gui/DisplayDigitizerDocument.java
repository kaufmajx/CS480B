package gui;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import math.Vector;

/**
 * DisplayDigitizerDocument.
 *
 * @author Tenley Kennett & Jelal Kaufman
 * @version 2/19
 */
public class DisplayDigitizerDocument implements DigitizerDocument
{

  protected DigitizerPanel panel;
  protected List<Line2D.Double> lines;

  /**
   * Constructs a {@code DisplayDigitizerDocument} associated with the given panel, initializing an
   * empty line list.
   *
   * @param panel
   *          the {@link DigitizerPanel} that owns this document; must not be {@code null}
   */
  public DisplayDigitizerDocument(final DigitizerPanel panel)
  {
    this.panel = panel;
    lines = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Creates a {@link Line2D.Double} from the provided coordinate arrays and appends it to the
   * internal list.
   */
  @Override
  public void addLine(final double[] start, final double[] stop)
  {
    Line2D.Double newLine = new Line2D.Double(start[0], start[1], stop[0], stop[1]);
    lines.add(newLine);
  }

  /**
   * {@inheritDoc}
   *
   * <p>
   * Iterates all stored lines and returns the one with the smallest sum of distances from
   * {@code point} to its two endpoints.
   */
  @Override
  public Line2D.Double getClosest(final double[] point)
  {
    Line2D.Double closestLine = null;
    double closestDistance = Double.POSITIVE_INFINITY;
    for (Line2D.Double line : getLines())
    {
      double[] v = {line.getX1() - point[0], line.getY1() - point[1]};
      double dist = Vector.norm(v);

      if ((dist) < closestDistance)
      {
        closestLine = line;
        closestDistance = dist;
      }
    }
    return closestLine;
  }

  /** {@inheritDoc} */
  @Override
  public List<Line2D.Double> getLines()
  {
    return this.lines;
  }

  /** {@inheritDoc} */
  @Override
  public void removeLine(final Line2D.Double line)
  {
    this.lines.remove(line);
  }
}
