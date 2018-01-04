package uk.ac.cam.gh455.projects.nono;

import java.util.ArrayList;

public class Choice {
	static ArrayList<ArrayList<Integer>> getGaps(int slots, int blocks) {
		ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
		
		if (slots == 1) {
			ArrayList<Integer> line = new ArrayList<>();
			line.add(blocks);
			out.add(line);
		}
		else {
			for (int n = blocks; n >= 0; n--) {
				for (ArrayList<Integer> list_end : getGaps(slots-1, blocks-n)) {
					ArrayList<Integer> line = new ArrayList<>(slots);
					line.add(n);
					line.addAll(list_end);
					out.add(line);					
				}
			}
		}
		return out;
	}
}
