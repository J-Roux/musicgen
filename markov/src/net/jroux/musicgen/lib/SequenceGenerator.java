package net.jroux.musicgen.lib;

import java.util.ArrayList;

public class SequenceGenerator {
	private ArrayList<Integer> notes;
	
	public SequenceGenerator(ArrayList<Integer> firstNotes) {
		notes = firstNotes;
	}
	
	public ArrayList<Integer> generate(int count, Score score) {
	    ArrayList<Integer> sequence = new ArrayList<Integer>();
		Integer note = new Integer(0);
		Integer[] temp = new Integer[notes.size()];
	    for(int i = 0; i <  count;  i++) {
	    	//System.out.println("Before  " + note);
	    	//System.out.println("Before  " + notes);
	    	note = score.nextNote(notes.toArray(temp));
	    	//System.out.println("After   " + note);
	    	//System.out.println("After   " + notes);
	    	sequence.add(note);
	    	notes.add(note);
	    	notes.remove(0);	    	
		}
		return sequence;
	}	
}
