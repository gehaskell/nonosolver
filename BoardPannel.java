package uk.ac.cam.gh455.projects.nono;

import javax.swing.JPanel;

public class BoardPannel extends JPanel{
	private Boolean[][] mBoard;
	
	@Override
    protected void paintComponent(java.awt.Graphics g) {
        
    	// Paint the background white
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        if (mBoard != null) {

	        int maxHeight = this.getHeight() / mBoard.length;
	        int maxWidth  = this.getWidth() / mBoard[0].length;
	        
	        //choose the limiting dimension
	        int rectSize = maxHeight < maxWidth ? maxHeight : maxWidth;
	        	        
	        for (int y = 0; y < mBoard.length; ++y) {
	        	for (int x = 0; x < mBoard[0].length; ++x) {
	        		int xPos = x*rectSize;
	        		int yPos = y*rectSize;
	        		if (mBoard[y][x] == null) {
	        			g.setColor(java.awt.Color.LIGHT_GRAY);
	        			g.fillRect(xPos,yPos , rectSize, rectSize);
	        		} else if (mBoard[y][x]) {
	        			g.setColor(java.awt.Color.BLACK);
	        			g.fillRect(xPos,yPos , rectSize, rectSize);
        			} else {
        				g.setColor(java.awt.Color.WHITE);
        				g.fillRect(xPos,yPos , rectSize, rectSize);
        			}
	        		
	        		g.setColor(java.awt.Color.GRAY);
	        		g.drawRect(xPos,yPos , rectSize, rectSize);
	        	}
	        }
        }
    }
    public void display(Boolean[][] board) {
    	mBoard = board;
        repaint();
    }
}
