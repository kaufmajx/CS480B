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

public class DigitizerPanel extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static final int ADD = 0;
	public static final int DELETE = 1;

	private BufferedImage ortho;
	private DigitizerDocument model;
	private int mode;
	
	private Point newLineStart;
	private Point newLineEnd;
	private ArrayList<double[]> newLinePoints;
	private List<Line2D> currDrawingLines;
	private boolean drawing;

	public DigitizerPanel(BufferedImage ortho) {
		this.ortho = ortho;
		this.model = new DisplayDigitizerDocument(this);
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (mode == ADD) {
					newLineStart = e.getPoint();
				} else if (mode == DELETE) {

				}

				model.addLine(new double[] { 3, 3 }, new double[] { 8, 5 });
				System.out.print("test");
				paint(getGraphics());
				
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				if (mode == ADD) {
					newLineStart = e.getPoint();
					drawing = true;
					currDrawingLines = new ArrayList<>();
				} else if (mode == DELETE) {

				}
			}

			public void mouseReleased(MouseEvent e) {
				if (mode == ADD) {
					drawing = false;
					newLineEnd = e.getPoint();
					calcNewLinePoints();
					
					
					model.addLine(newLinePoints.get(0), newLinePoints.get(1));

					paint(getGraphics());
				} else if (mode == DELETE) {

				}
				
			}

		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (mode == ADD) {
					newLineEnd = e.getPoint();
					calcNewLinePoints();
					
					Line2D.Double newLine = new Line2D.Double(newLinePoints.get(0)[0], newLinePoints.get(0)[1], newLinePoints.get(1)[0], newLinePoints.get(1)[1]);
					currDrawingLines.add(newLine);
					paint(getGraphics());
				} else if (mode == DELETE) {

				}
			}
		});
	}
	
	private void calcNewLinePoints() {
		newLinePoints = new ArrayList<>();
		if (newLineStart != null && newLineEnd != null) 
		{
			newLinePoints.add(new double[] {newLineStart.getX(), newLineStart.getY()});
			newLinePoints.add(new double[] {newLineEnd.getX(), newLineEnd.getY()});
		}
	}
	
//	private void render

	public DigitizerDocument createDefaultMode() {
		return model;
	}

	public List<Line2D> getLines() {
		return this.model.getLines();
	}

	public void paint(Graphics g) {
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
			for (Line2D line : currDrawingLines) {
				g2.setPaint(Color.YELLOW);
				g2.draw(line);
			}
		} else
		{
			for (Line2D line : model.getLines()) {
				g2.setPaint(Color.GREEN);
				g2.draw(line);
			}
		}

	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
//		model.addLine(new double[] { 3, 3 }, new double[] { 8, 5 });
//		System.out.print("test");
//		paint(getGraphics());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
