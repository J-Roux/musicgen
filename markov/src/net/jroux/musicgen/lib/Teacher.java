package net.jroux.musicgen.lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class Teacher {
	private static final int NOTE_ON = 0x90;
	
	public static void teach(String filePath, Score score) {
		try {
			int a = 0;
			Sequence  sequence = MidiSystem.getSequence(new File(System.getProperty("user.dir") + filePath));
			ArrayList<Integer> previos = new ArrayList<Integer>();
			int range = score.range;
			for(Track track : sequence.getTracks()) {
				for(int i = 0; i < track.size(); i++) {
					MidiEvent event = track.get(i);
					MidiMessage message = event.getMessage();
					if (message instanceof ShortMessage) {
						ShortMessage shortMessage = (ShortMessage) message;
						if (shortMessage.getCommand() == NOTE_ON) {
							int key = shortMessage.getData1();
							key--;
							if (range > -1) {
								range--;
								previos.add(key);
							} else {
								score.updateWeights(previos.toArray(new Integer[previos.size()]));
								previos.remove(0);
								previos.add(key);
								a++;
							}
						}
					}
				}
			}
			System.out.println(a);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
