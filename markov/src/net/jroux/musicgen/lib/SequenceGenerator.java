package net.jroux.musicgen.lib;

import java.util.ArrayList;

/**
 * Generates the sequence of notes based on a Markov's chain state.
 */
public class SequenceGenerator {

	/**
	 * Internal generator memory. Contains last <b>Config.generatorMemory</b>
	 * notes and propagates with each new note generated.
	 */
	private ArrayList<Integer> generatorMemory;

	public SequenceGenerator(ArrayList<Integer> firstNotes) {
		generatorMemory = new ArrayList<>(firstNotes);
	}

	/**
	 * Propagates the generator memory after new note has been generated.
	 * @param note The integer note to fill new space with.
	 */
	private void propagateGeneratorMemory(int note) {
		generatorMemory.add(note);
		generatorMemory.remove(0);
	}

	/**
	 * Generates the sequence of notes based on the Markov's chain state.
	 * @param count Amount of notes to generate.
	 * @param score The Markov's chain to generate from.
	 * @return List of integer notes.
	 */
	public ArrayList<Integer> generate(int count, Score score) {
		ArrayList<Integer> sequence = new ArrayList<>();

		while (--count >= 0) {
			int note = score.nextNote(generatorMemory.toArray(new Integer[generatorMemory.size()]));
			sequence.add(note);
			propagateGeneratorMemory(note);
		}

		return sequence;
	}
}
