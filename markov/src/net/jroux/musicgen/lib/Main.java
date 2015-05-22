package net.jroux.musicgen.lib;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class Main {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\JRoux\\Downloads\\furelise.mid";
		String filePath1 = "C:\\Users\\JRoux\\Downloads\\mozk281a.mid";
		String filePath2 = "C:\\Users\\JRoux\\Downloads\\pathet2.mid";
		String filePath3 = "C:\\Users\\JRoux\\Downloads\\pathet3.mid";
		String filePath4 = "C:\\Users\\JRoux\\Downloads\\beeth9-1.mid";
		String filePath5 = "C:\\Users\\JRoux\\Downloads\\beeth9-2.mid";
		String filePath6 = "C:\\Users\\JRoux\\Downloads\\beeth9-3.mid";
		System.out.println("Hello!");
		Score score = new Score(2);
 		Learn learn = new Learn(filePath, score);
 		//learn = new Learn(filePath2, score);
 	    //learn = new Learn(filePath3, score);
 	    //learn = new Learn(filePath1, score);
 	    //learn = new Learn(filePath2, score);
 	    //learn = new Learn(filePath3, score);
 		score.normalizeMatrix();
		try {
			Synthesizer synthesizer = MidiSystem.getSynthesizer();
			synthesizer.open();
			final MidiChannel[] channels = synthesizer.getChannels();
			Integer previos[] = {42,43,47};
			//Integer previos1[] = {42,43};
			int nn, nn1;
			for (int i = 0; i < 200; i++) {
				nn = score.nextNote(previos);
				//nn1 = score.nextNote(previos1);
			//System.out.println(nn + " " + nn1);
				channels[0].noteOn(nn, 127);
				//channels[1].noteOn(nn1, 127);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		    	}
				channels[0].noteOff(nn);
				//channels[1].noteOff(nn1);
				previos[2] = previos[1];
				//previos1[1] = previos1[0];
				//previos1[0] = nn1;
				previos[1] = previos[0];
				previos[0] = nn;
				

			}
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
