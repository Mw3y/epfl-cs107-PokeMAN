package ch.epfl.cs107.play.window.swing;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import ch.epfl.cs107.play.math.random.RandomGenerator;
import ch.epfl.cs107.play.window.Sound;


public final class SwingSound implements Sound{

    /// Audio format, info and number of bytes of the sound
    private final AudioFormat audioFormat;
    private final DataLine.Info info;
    private final int size;
    /// audio array containing a save of the initial stream
    /// - can be used multiple times
    private final byte[] audio;

    /**
     * Default SwingSound Constructor
     * - store the input stream into an audio byte array
     * - keep also format, info and size in memory
     * @param input (InputStream): corresponding to the sound file. Not null
     * @throws IOException : if the AudioInputStream cannot be read
     * @throws UnsupportedAudioFileException : if the URL does not point to valid audio file data recognized by the system
     */
    public SwingSound(InputStream input) throws IOException, UnsupportedAudioFileException {

        // Indicate the input stream is an AudioInputStream
        final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(input);
        // Save the format
        audioFormat = audioInputStream.getFormat();
        // Compute and save the byte size of the stream
        size = (int) (audioFormat.getFrameSize() * audioInputStream.getFrameLength());
        // Create the audio array
        audio = new byte[size];
        // Compute and save the information
        info = new DataLine.Info(Clip.class, audioFormat, size);

        // Copy the audio stream into the audio array and throw exception on error
        int i = audioInputStream.read(audio, 0, size);
        if(i == -1) throw new IOException("AudioInputStream cannot be read");
    }


    /**
     * Create and open a new audio Clip containing the swing sound
     * @param offset (int): the point at which to start copying, expressed in bytes from the beginning of the array
     * @return (Clip): the opened swing sound ready to start or null on error
     */
    public Clip openedClip(int offset){
        try {
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioFormat, audio, offset, size-offset);
            return audioClip;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Compute a random integer in the range defined by the sound size
     * @return (int): a random int between 0 (inclusive) and size (exclusive)
     */
    public int randomOffSet(){
        return RandomGenerator.getInstance().nextInt(size);
    }

}
