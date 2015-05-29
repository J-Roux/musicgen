package net.jroux.musicgen.lib;

import net.jroux.musicgen.lib.helper.Config;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *  Static teacher that is used to teach Markov's chain based on a list of notes.
 */
public class Teacher {
	/**
	 * Performs teaching of a {@link Score} object based on a given midi file.
	 * @param trainingSample Array list of integer notes to learn on.
	 * @param score Markov's chain to teach.
	 */
	public static void teach(ArrayList<Integer> trainingSample, Score score) {
		ArrayList<Integer> teacherMemory = trainingSample.stream()
				.limit(Config.learnerMemory + 1)
				.collect(Collectors.toCollection(ArrayList::new));

		System.out.println(trainingSample.toString());
		for (int note : trainingSample) {
			score.updateWeights(teacherMemory.toArray(new Integer[teacherMemory.size()]));
			propagateTeacherMemory(teacherMemory, note);
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
