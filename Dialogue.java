package uk.ac.cam.gh455.projects.nono;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dialogue extends JFrame {
	
	static void InputError(JFrame frame, String text) {
		JOptionPane.showMessageDialog(frame, text, "Input Error", JOptionPane.ERROR_MESSAGE);
	}
}
