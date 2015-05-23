package net.jroux.musicgen.lib;

import net.jroux.musicgen.lib.helper.Config;
import net.jroux.musicgen.lib.utils.MidiReader;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *  Static teacher that is used to teach Markov's chain based on an input midi file.
 */
public class Teacher {
	/**
	 * Performs teaching of a {@link Score} object based on a given midi file.
	 * @param filePath Path to a learning file relative to an execution directory.
	 * @param score Markov's chain to teach.
	 */
	public static void teach(String filePath, Score score) {
		ArrayList<Integer> notes = MidiReader.readMidiFile(filePath);
		ArrayList<Integer> teacherMemory = notes.stream()
				.limit(Config.learnerMemory)
				.collect(Collectors.toCollection(ArrayList::new));

		for (int note : notes) {
			propagateTeacherMemory(teacherMemory, note);
			score.updateWeights((Integer[]) teacherMemory.toArray());
		}
	}

	/**
	 * Shifts contents of a teacher memory while retaining its original size.
	 * @param teacherMemory {@link ArrayList} to propagate.
	 * @param note Note integer to append to the end.
	 */
	private static void propagateTeacherMemory(ArrayList<Integer> teacherMemory, int note) {
		teacherMemory.add(note);
		teacherMemory.remove(0);
	}
}
