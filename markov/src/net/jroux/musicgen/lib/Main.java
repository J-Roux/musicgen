package net.jroux.musicgen.lib;

import net.jroux.musicgen.lib.helper.*;
import net.jroux.musicgen.lib.utils.MidiPlayer;

import java.util.ArrayList;

public class Main {

	private static Score teachNewScoreObj() {
		Score score = new Score(Config.learnerMemory);
		Teacher.teach(Config.learningSamples[0], score);
		score.normalizeMatrix();
		return score;
	}

	public static void main(String[] args) {
		MidiPlayer player = new MidiPlayer();
		player.playTestJingle();

	    Score score = teachNewScoreObj();
		ArrayList<Integer> sequence = new ArrayList<>();

		SequenceGenerator gen = new SequenceGenerator(sequence);
		ArrayList<Integer> midiSequence = gen.generate(Config.sequenceLength, score);
		player.play(midiSequence);
	}

}
