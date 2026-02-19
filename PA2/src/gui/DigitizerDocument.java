package gui;

import java.awt.geom.Line2D;
import java.util.List;

public interface DigitizerDocument
{

  public void addLine(final double[] start, final double[] stop);

  public Line2D.Double getClosest(final double[] point);

  public List<Line2D.Double> getLines();

  public void removeLine(final Line2D.Double line);

}
