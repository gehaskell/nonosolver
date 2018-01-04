package uk.ac.cam.gh455.projects.nono;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Line {
	private int mLength;
	private List<Integer> mBlocks;
	private Boolean[] mConfirmed;
	private List<Boolean[]> mPossible;
	
	public Line(int length, List<Integer> blocks) {
		mLength = length;
		mBlocks = blocks;
		mPossible = new ArrayList<Boolean[]>();
		initiate();
	}
	
	private void initiate() {
		int filled_count = 0;
		for (int block_length: mBlocks) {
			filled_count += block_length;
		}
		
		// empty_count -> number 'free' spaces, does not include separating frees  
		// between adjacent blocks. 
		// gap_count -> number of slots these frees can goto (includes ends)
		
		int empty_count = mLength - filled_count - mBlocks.size() + 1; 
		int slots_count = mBlocks.size() + 1;
		
		if (empty_count < 0) {
			throw new IllegalArgumentException();
		}
		
		for (List<Integer> gaps : Choice.getGaps(slots_count, empty_count)) {	
			List<Boolean> temp_out = new ArrayList<>(mLength);
			
			for (int n = 0; n < gaps.get(0); n++) {
				temp_out.add(false);
			}
			for (int i = 0; i < mBlocks.size(); i++) {
				for (int m = 0; m < mBlocks.get(i); m++) {
					temp_out.add(true);
				}
				temp_out.add(false);
				for (int p = 0; p < gaps.get(i+1); p++) {
					temp_out.add(false);
				}				
			}
			// accounts for extra gap placed at end of line
			temp_out.remove(temp_out.size()-1);

			mPossible.add(temp_out.toArray(new Boolean[0]));
			
		}
	}
	
	private void updateDef() {
		mConfirmed = new Boolean[mLength];
		Boolean[] on  = new Boolean[mLength];
		Boolean[] off = new Boolean[mLength];
			
		for (int i = 0; i < mLength; i++) {
			on[i] = true;
			off[i] = true;
		}
		
		for (Boolean[] line: mPossible) {
			for (int i = 0; i < mLength; i++) {
				if (line[i] == false) {
					on[i] = false;
				}
				else {
					off[i] = false;
				}
			}
		}
		
		for (int j = 0; j < mLength; j++) {
			if (on[j])
				mConfirmed[j] = true;
			if (off[j])
				mConfirmed[j] = false;
		}
	}
	
	public Boolean isDone() {
		return mPossible.size() == 1;
	}
	
	public Boolean[] getConfirmed() {
		updateDef();
		return mConfirmed;
	}
	
	public void refine(Boolean[] template) {
		 Iterator<Boolean[]> itr = mPossible.iterator();

		 while (itr.hasNext()) {
			 Boolean[] line = itr.next();
			 for (int n = 0; n < line.length; n++) {
				 if (template[n] != null && template[n] != line[n]) {
					 itr.remove();
					 break;
				 }
			 }
		 }
	}
	
}
