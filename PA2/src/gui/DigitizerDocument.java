package gui;

import java.awt.geom.Line2D;
import java.util.List;

/**
 * DigitizerDocument interface.
 *
 * @author Tenley Kennett & Jelal Kaufman
 * @version 2/19
 */
public interface DigitizerDocument
{

  /**
   * Adds a new line segment defined by two endpoints.
   *
   * @param start
   *          a double array of length 2 representing the start point {x, y}
   * @param stop
   *          a double array of length 2 representing the end point {x, y}
   */
  public void addLine(final double[] start, final double[] stop);

  /**
   * Returns the line segment whose endpoints are closest to the given point, using the sum of
   * Euclidean distances from the point to each endpoint as the proximity metric.
   *
   * @param point
   *          a double array of length 2 representing the query point {x, y}
   * @return the closest {@link Line2D.Double}, or {@code null} if no lines exist
   */
  public Line2D.Double getClosest(final double[] point);

  /**
   * Returns the list of all line segments currently stored in this document.
   *
   * @return a {@link List} of {@link Line2D.Double} objects; never {@code null}
   */
  public List<Line2D.Double> getLines();

  /**
   * Removes the specified line segment from this document.
   *
   * @param line
   *          the {@link Line2D.Double} to remove; if {@code null} or not present, no action is
   *          taken
   */
  public void removeLine(final Line2D.Double line);

}
