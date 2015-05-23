package net.jroux.musicgen.lib.utils;

import net.jroux.musicgen.lib.helper.Config;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Static reader that is used to parse midi file and extract note sequence from it.
 */
public class MidiReader {
    /**
     * Reads certain voice (track) of a midi file and returns a sequence of notes
     * within that voice as an array list of integers.
     * @param relativePath Path to a midi file relative to the execution directory.
     * @param leadingVoice Number of a leading track.
     * @return {@link ArrayList} of integer notes.
     */
    public static ArrayList<Integer> readMidiFile(String relativePath, int leadingVoice) {
        File midiFile = new File(expandFilePath(relativePath));
        try {
            Sequence sequence = MidiSystem.getSequence(midiFile);
            return extractNotes(sequence, leadingVoice);
        } catch (InvalidMidiDataException | IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Reads the default voice (track) of a midi file and returns a
     * sequence of notes within that voice as an array list of integers.
     * @param relativePath Path to a midi file relative to the execution directory.
     * @return {@link ArrayList} of integer notes.
     */
    public static ArrayList<Integer> readMidiFile(String relativePath) {
        return readMidiFile(relativePath, Config.defaultLeadingVoice);
    }

    /**
     * Extracts notes from a midi sequence.
     * @param sequence MidiSequence to extract notes from.
     * @param leadingVoice Track number to extract notes from.
     * @return {@link ArrayList} of integer notes on a track.
     */
    private static ArrayList<Integer> extractNotes(Sequence sequence, int leadingVoice) {
        Track track = sequence.getTracks()[leadingVoice];
        ArrayList<Integer> notes = new ArrayList<>();

        for (int eventIndex = 0; eventIndex < track.size(); eventIndex++) {
            MidiMessage message = track.get(eventIndex).getMessage();
            processEventMessage(message, notes);
        }

        return notes;
    }

    /**
     * Process single midi event.
     * @param message Message of the event to parse.
     * @param notes {@link ArrayList} of notes. <b>Can be modified!</b>
     */
    private static void processEventMessage(MidiMessage message, ArrayList<Integer> notes) {
        if (message instanceof ShortMessage) {
            ShortMessage shortMessage = (ShortMessage)message;

          switch (shortMessage.getCommand()) {
              case ShortMessage.NOTE_ON: {
                  notes.add(shortMessage.getData1());
              } break;

              default: { }
          }
        }
    }

    /**
     * Expands relative file path to a full one.
     * @param relativeFilePath file path to expand.
     * @return currentDir + relativeFilePath
     */
    private static String expandFilePath(String relativeFilePath) {
        return System.getProperty("user.dir") + relativeFilePath;
    }

}
