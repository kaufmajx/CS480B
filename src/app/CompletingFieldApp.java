package app;

import gui.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * An app that can be used to test the CompletingField and CompletingDocument
 * classes.
 *
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class CompletingFieldApp implements Runnable {
	private String fn;

	/**
	 * Explicit Value Constructor.
	 * 
	 * @param fn The name of the file to use
	 */
	public CompletingFieldApp(final String fn) {
		this.fn = fn;
	}

	/**
	 * The code to execute in the event dispatch thread.
	 */
	@Override
	public void run() {
		JFrame frame = new JFrame("CompletingFieldApp");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel contentPane = (JPanel) frame.getContentPane();

		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.WHITE);

		try {
			BufferedImage image = ImageIO.read(new File("logoWay.gif"));
			JLabel logo = new JLabel(new ImageIcon(image));
			contentPane.add(logo, BorderLayout.CENTER);
		} catch (IOException ioe) {
			// Ignore
		}

		CompletingField field = new CompletingField();

		field.setWordList(fn);
		field.setBorder(BorderFactory.createTitledBorder("Street Name"));
		contentPane.add(field, BorderLayout.SOUTH);

		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
