package net.jroux.musicgen.lib;

import java.util.ArrayList;

public class SequenceGenerator {
	private ArrayList<Integer> notes;
	
	public SequenceGenerator(ArrayList<Integer> firstNotes) {
		notes = firstNotes;
	}
	
	public ArrayList<Integer> generate(int count, Score score) {
	    ArrayList<Integer> sequence = new ArrayList<>();
		Integer[] temp = new Integer[notes.size()];
	    for(int i = 0; i <  count;  i++) {
	    	int note = score.nextNote(notes.toArray(temp));
	    	sequence.add(note);
	    	notes.add(note);
	    	notes.remove(0);	    	
		}
		return sequence;
	}	
}
