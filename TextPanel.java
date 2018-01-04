package uk.ac.cam.gh455.projects.nono;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class TextPanel extends JPanel {
	private String mName;
	private int mLength;
	private List<JTextField> mFields;
	private List<List<Integer>> mOutput;
	
	public TextPanel(int length, String name) {
		mName = name;
		mLength = length;		
		
		mOutput = new ArrayList<>();
		mFields = new ArrayList<JTextField>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		resetOutput();
		addPanels();
	}
		
		
	public void addPanels() {	
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border grayBorder = BorderFactory.createLineBorder(Color.GRAY);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(blackBorder, mName);

		setBorder(titledBorder);
			
		// adds JTextFields and keeps reference list, 
		// added in groups of 5
			
		JPanel group = new JPanel();	
		setupPanel(group,grayBorder);
		
		int size = 0;		
		for (int i = 0; i < mLength; i++) {
			JTextField temp = new JTextField(10);
			
			mFields.add(temp);			
			group.add(temp);
			
			size++;
			if (size % 5 == 0 || size == mLength) {
				add(group);
				group = new JPanel();
				setupPanel(group,grayBorder);
			}			
		}
		
		
	}
	
	private void setupPanel(JPanel panel, Border border) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(border);
	}

	private void resetOutput() {
		// Defaults mOutput to zeros,
		ArrayList<Integer> listDefault = new ArrayList<>();
		listDefault.add(0);	
		
		mOutput = new ArrayList<>();
		for (int i = 0; i < mLength; i++) {
			mOutput.add(listDefault);
		}
	}
	
	private List<Integer> parseInput(String input) throws NumberFormatException {
		ArrayList<Integer> output = new ArrayList<>();
		String[] numbers = input.split(",");
		for (String x: numbers) {
			if (x != null)
				output.add(Integer.parseInt(x));
		}
		return output;
	}
	
   
	public List<List<Integer>> updateOut() throws NumberFormatException {
		resetOutput();
		
		int x = 0;
		for (JTextField field: mFields) {
			mOutput.set(x, parseInput(field.getText()));
			x++;
		}
		return mOutput;
	}
	
	public List<List<Integer>> getOutput() {
		return mOutput;
	}
	
	public static void main(String args[]) {
    	GUINono gui = new GUINono();
    	gui.setVisible(true);

    }
}