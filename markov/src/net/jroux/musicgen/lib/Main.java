package net.jroux.musicgen.lib;

import net.jroux.musicgen.lib.helper.*;
import net.jroux.musicgen.lib.utils.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Entry point of the program.
 */
public class Main {

	/**
	 * Midi player used to play the generation results.
	 */
	private static MidiPlayer player;

	/**
	 * List of notes read from the input files.
	 */
	private static ArrayList<Integer> learningSample;

	/**
	 * Sequence generator used to extrapolate the input sequence of notes.
	 */
	private static SequenceGenerator sequenceGenerator;

	/**
	 * Creates new {@link Score} object (Markov's chain descriptor) and
	 * teaches it with the learning sample.
	 * @param sample learning sample to teach with.
	 * @return Markov's chain ready to extrapolate the notes.
	 */
	private static Score teachNewScoreObj(ArrayList<Integer> sample) {
		Score score = new Score(Config.learnerMemory);
		Teacher.teach(sample, score);
		score.normalizeMatrix();
		return score;
	}

	/**
	 * Selects first <b>Config.learnerMemory</b> notes from the learning sample.
	 * @param sample sample to take notes from.
	 * @return {@link ArrayList} of integer notes.
	 */
	public static ArrayList<Integer> getInitialNotes(ArrayList<Integer> sample) {
		return sample.stream()
				.limit(Config.learnerMemory)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Creates the midi player and plays the test jingle.
	 */
	private static void initializePlayer() {
		player = new MidiPlayer();
		player.playTestJingle();
	}

	/**
	 * Creates the sequence generator based on the learning sample.
	 */
	private static void initializeSequenceGenerator() {
		sequenceGenerator = new SequenceGenerator(getInitialNotes(learningSample));
	}

	/**
	 * Reads the sample midi file to retrieve list of notes to learn on.
	 */
	private static void readLearningSample() {
		learningSample = MidiReader.readMidiFile(Config.learningSamples[0], 1);
	}

	/**
	 * Entry point
	 * @param args command line args.
	 */
	public static void main(String[] args) {
		initializePlayer();
		readLearningSample();
		initializeSequenceGenerator();

		ArrayList<Integer> midiSequence = sequenceGenerator
				.generate(Config.sequenceLength, teachNewScoreObj(learningSample));

		player.play(midiSequence);
	}
}
