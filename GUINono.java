package uk.ac.cam.gh455.projects.nono;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

public class GUINono extends JFrame {
	private TextPanel mRowData;
	private TextPanel mColData;
	
	private BoardPannel mBoardPanel;

	private int mWidth;
	private int mHeight;
	
	private Nono mBoard;
	
	public GUINono() {
		while (setDimensions() == false);
	
		mRowData = new TextPanel(mHeight, "col-Input");
		mColData = new TextPanel(mWidth, "row-Input");
		
		mBoard = new Nono(mWidth, mHeight, mRowData.getOutput(), mColData.getOutput());
			
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024,768);	
		
		add(createInputPanel(), BorderLayout.WEST);	
	    add(createBoardPanel(), BorderLayout.CENTER);
	}
	
	private Boolean setDimensions() {
		Boolean state = true;
		
		JTextField[] mDimensions = new JTextField[2];
		
		mDimensions[0] = new JTextField(10);
		mDimensions[1] = new JTextField(10);
		
		
		JPanel base = new JPanel();
        JLabel width = new JLabel("Width:");
        JLabel height = new JLabel("Height:");

        base.add(width);
        base.add(mDimensions[1]);
        base.add(height);
        base.add(mDimensions[0]);

        JOptionPane.showConfirmDialog(null, base, "Puzzel Size:"
        		, JOptionPane.DEFAULT_OPTION);
        
        String x = mDimensions[0].getText();
        String y = mDimensions[1].getText();

        try {
        	mWidth = Integer.parseInt(x);
        	mHeight = Integer.parseInt(y);
        
        } catch (NumberFormatException e) {
        	Dialogue.InputError(this, "Dimensions must be integers.");
			state = false;        	
        }
        
        return state;
	}
	
	private JScrollPane createFieldPanel() {
		JPanel input = new JPanel();

		input.add(mRowData);
		input.add(mColData);
		
		FlowLayout inLayout = new FlowLayout(FlowLayout.CENTER, 5, 5);
		
		inLayout.setAlignOnBaseline(true);
		input.setLayout(inLayout);
		
		JScrollPane scrollInput = new JScrollPane(input);
		scrollInput.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		return scrollInput;
	}

	private JPanel createInputPanel() {
		JButton solve = new JButton("Solve"); 
		JButton reset = new JButton("Reset"); 
		
		solve.addActionListener(e->solve());
		reset.addActionListener(e->reset());
		
		
		JPanel ctrl = new JPanel();

		ctrl.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		
		c.ipadx = 15;
		c.weighty = 1.0;
		c.gridheight = 5;		
		c.gridy = 0;
		
		ctrl.add(createFieldPanel(), c);
		
		c.ipadx = 0;
		c.weighty = 0;
		c.gridheight = 1;
		c.gridy = 6;	
		
		ctrl.add(solve,c);
		c.gridy++;
		ctrl.add(reset,c);
		
		return ctrl;
	}
	
	private JPanel createBoardPanel() {
		mBoardPanel = new BoardPannel();		
		
		mBoardPanel.display(mBoard.getBoard());
		
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				"Output");
		
		JPanel temp = new JPanel();
		temp.setBorder(border);
		temp.setLayout(new BorderLayout(10,10));
		temp.add(mBoardPanel);
		
		return temp;
	}

	public void reset() {
		dispose();
		String[] args = { }; 
		GUINono.main(args);
	}
	
	private void solve() {
		try {
			List<List<Integer>> row = mRowData.updateOut();
			List<List<Integer>> col = mColData.updateOut();

			mBoard = new Nono(mWidth, mHeight, row, col);	
			mBoard.solve();
			
			mBoardPanel.display(mBoard.getBoard());
			
		} catch (NumberFormatException e) {
			Dialogue.InputError(this, "Input should be integers separated by commas.");
		} catch (IllegalArgumentException f) {
			Dialogue.InputError(this, "This pattern cannot be solved.");
		}
	}
	
	public static void main(String args[]) {
    	GUINono gui = new GUINono();
    	gui.setVisible(true);

    }
}