package net.jroux.musicgen.lib.helper;

import net.jroux.musicgen.lib.SequenceGenerator;
import net.jroux.musicgen.lib.Teacher;

/**
 * Static configurations class.
 */
public class Config {
	/**
	 * Contains project relative paths to learning sample documents for
	 * Markov's chain {@link Teacher}.
	 */
	public static String[] learningSamples = {
			"/resources/furelise.mid",
			"/resources/mozk281a.mid",
			"/resources/pathet2.mid",
			"/resources/pathet3.mid",
			"/resources/beeth9-1.mid",
			"/resources/beeth9-2.mid",
			"/resources/beeth9-3.mid",
			"/resources/liz_et3.mid",
			"/resources/liz_rhap02.mid",
			"/resources/liz_rhap09.mid"
	};

	/**
	 * Length of a note sequence to generate from with {@link SequenceGenerator}.
	 */
	public static int sequenceLength = 50;

	/**
	 * Amount of samples to use for the learning process.
	 */
	public static int sampleCount = 1;

	/**
	 * Markov's chain memory when learning from the sample.
	 * Note that increasing this number significantly increases
	 * the amount of memory required by the learning process.
	 * @see Teacher
	 */
	public static int learnerMemory = 2;

	/**
	 * Default midi {@link net.jroux.musicgen.lib.utils.MidiPlayer} note velocity.
	 */
	public static int defaultPlayerVelocity = 0x80;

	/**
	 * Default length of a {@link net.jroux.musicgen.lib.utils.MidiPlayer} note.
	 */
	public static int defaultPlayerBpm = 400;

	/**
	 * Default leading track number;
	 */
	public static int defaultLeadingVoice = 1;
}
