package uk.ac.cam.gh455.projects.nono;

import java.util.List;

public class Nono {
	private Boolean[][] mBoard;
	private int mHeight;
	private int mWidth;
	
	private List<List<Integer>> mRowInput;
	private List<List<Integer>> mColInput;
	
	private Line[] mCol;
	private Line[] mRow;
	
	public Nono(int width, int height, List<List<Integer>> rowInput, List<List<Integer>> colInput) {
		
		mRowInput = rowInput;
		mColInput = colInput;
		
		mHeight = height;
		mWidth = width;
		mBoard = new Boolean[mHeight][mWidth];
		
		mCol = new Line[mWidth];
		mRow = new Line[mHeight];
	
		for (int x = 0; x < mWidth; x++)
			mCol[x] = new Line(mHeight, mColInput.get(x));
		
		for (int y = 0; y < mHeight; y++) 
			mRow[y] = new Line(mWidth, mRowInput.get(y));

	}

	// copies most recent line data to main board
	private void updateBoardCol() {
		int x = 0;
		for (Line line: mCol) {
			for (int y = 0; y < mHeight; y++) {
				if (mBoard[y][x] == null && line.getConfirmed()[y] != null) {
					mBoard[y][x] = line.getConfirmed()[y];
				}
			}
			x++;
		}
	}
	private void updateBoardRow() {
		int y = 0;
		for (Line line: mRow) {
			for (int x = 0; x < mWidth; x++) {
				if (mBoard[y][x] == null && line.getConfirmed()[x] != null) {
					mBoard[y][x] = line.getConfirmed()[x];
				}
			}
			y++;
		}
	}
	
	// refines line data with board information
	private void updateLineCol() {
		int x = 0;
		for (Line line: mCol) {
			Boolean[] temp = new Boolean[mHeight];
			for (int y = 0; y < mHeight; y++) {
				temp[y] = mBoard[y][x];
			}
			line.refine(temp);
			x++;
		}		
	}
	private void updateLineRow() {
		int y = 0;
		for (Line line: mRow) {
			Boolean[] temp = new Boolean[mWidth];
			for (int x = 0; x < mWidth; x++) {
				temp[x] = mBoard[y][x];
			}
			line.refine(temp);
			y++;
		}		
	}
	
	private boolean isDone() {
		for (Line line: mCol)
			if (!line.isDone())
				return false;
		return true;
	}
		
	public void solve() {
		updateLineCol();
		updateBoardCol();
		updateBoardRow();
		updateLineRow();
		
		int max = 100;
		while (isDone() == false && max != 0) {		
			updateLineCol();
			updateBoardCol();
			updateBoardRow();
			updateLineRow();
			max--;
		}
		if (max == 0)
			throw new IllegalArgumentException();
	}
	
	public Boolean[][] getBoard() {
		return mBoard;
	}
	
	public void print() {
		for (Boolean[] row: mBoard) {
			for (Boolean state: row) {
				if (state == null) {
					System.out.print(".");
				}
				else {
					System.out.print(state ? "#" : " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	
}
