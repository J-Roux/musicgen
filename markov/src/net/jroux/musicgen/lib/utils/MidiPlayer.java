package net.jroux.musicgen.lib.utils;

import net.jroux.musicgen.lib.helper.Config;

//import sun.plugin.dom.exception.InvalidStateException;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Player class that is able to play a sequence of integer notes.
 */
public class MidiPlayer {

    /**
     * Midi synthesizer to perform output to.
     */
    private Synthesizer synthesizer;

    /**
     * Channel to perform output to.
     */
    private MidiChannel targetChannel;


    public MidiPlayer() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            targetChannel = synthesizer.getChannels()[0];
        } catch (MidiUnavailableException e) {
  //          InvalidStateException exception = new InvalidStateException("Unable to initialize midi synthesizer or find channel.");
  //         exception.initCause(e);
  //          throw exception;
        }
    }

    /**
     * Plays certain note sequence.
     * @param notes Integer note sequence to play.
     */
    public void play(ArrayList<Integer> notes) {
        for (int note : notes) {
            playNote(note);
        }
    }

    /**
     * Plays one note for a default period of time
     * @param note Note code to play
     */
    public void playNote(int note) {
        targetChannel.noteOn(note, Config.defaultPlayerVelocity);
        sleepWhileNotePlays();
        targetChannel.noteOff(note);
    }

    /**
     * Plays the test lick.
     * @see <a href="http://www.midimountain.com/midi/midi_note_numbers.html">MIDI notes</a>
     */
    public void playTestJingle() {
        play(new ArrayList<>(Arrays.asList(60, 61, 60, 55, 55, 57, 64, 57, 55, 54,
                50, 45, 48, 49, 52, 57, 48, 49, 45, 45, 52, 57)));
    }

    /**
     * Wrapper for Thread.sleep() method
     */
    private void sleepWhileNotePlays() {
        final int MS_IN_MINUTE = 60000;
        try {
            Thread.sleep(MS_IN_MINUTE / Config.defaultPlayerBpm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
