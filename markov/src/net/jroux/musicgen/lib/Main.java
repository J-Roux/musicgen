package net.jroux.musicgen.lib;

import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Main {
	
	private static Score teachNewScoreObj() {
		Score score = new Score(Config.learnerMemory);
		
		Teacher.teach(Config.learningSaples[0], score);
		
		score.normalizeMatrix();
		return score;		
	}
	
	private static ArrayList<Integer> getStartingSequence() {
		return Teacher.startSequence ;
	}
	
	public static void main(String[] args) {
	    Score score = teachNewScoreObj();
		ArrayList<Integer> sequence = getStartingSequence();
		System.out.println(sequence.toString());
		SequenceGenerator gen = new SequenceGenerator(sequence);
		ArrayList<Integer> midiSequence = gen.generate(Config.sequenceLength, score);
		try {
			Synthesizer synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			final MidiChannel[] channels = synthesizer.getChannels();
			for (int i = 0; i < Config.sequenceLength; i++) {
				
				channels[0].noteOn(midiSequence.get(i) + 1 , 127);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
		    	}
				channels[0].noteOff(midiSequence.get(i) + 1);
			}
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
