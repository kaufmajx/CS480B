package gui;

import java.awt.geom.Line2D;
import java.util.List;

public interface DigitizerDocument {

	public void addLine(double[] start, double[] stop);

	public Line2D getClosest(double[] point);

	public List<Line2D> getLines();

	public void removeLine(Line2D line);

}
