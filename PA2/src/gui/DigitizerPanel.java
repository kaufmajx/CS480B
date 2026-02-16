package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class DigitizerPanel extends JPanel implements MouseWheelListener, MouseListener {

	private static final long serialVersionUID = 1L;
	public static final int ADD = 0;
	public static final int DELETE = 1;

	private BufferedImage ortho;
	private DigitizerDocument model;
	private int mode;

	public DigitizerPanel(BufferedImage ortho) {
		this.ortho = ortho;
	}

	public DigitizerDocument createDefaultMode() {
		return model;
	}

	public List<Line2D> getLines() {
		return this.model.getLines();
	}

	public void paint(Graphics g) {

	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

}
