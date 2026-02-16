package gui;

import java.awt.geom.Line2D;
import java.util.List;

public class DisplayDigitizerDocument implements DigitizerDocument {

	protected DigitizerPanel panel;
	protected List<Line2D> lines;

	public DisplayDigitizerDocument(DigitizerPanel panel) {
		this.panel = panel;
	}

	@Override
	public void addLine(double[] start, double[] stop) {
		// TODO Auto-generated method stub

	}

	@Override
	public Line2D getClosest(double[] point) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Line2D> getLines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLine(Line2D line) {
		// TODO Auto-generated method stub

	}
}
