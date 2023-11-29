package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.engine.actor.Acoustics;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Sound;

public class SoundAcoustics implements Acoustics {

    /// Image name
    private final String name;
    /// volume float value in percent : 0.0f no volume, 1.0f full volume
    private final float volume;
    /// Boolean flags : loop and stopOtherOnStart
    private final boolean randomFirstStart, fadeIn, loop, stopOthersOnStart;
    /// Should be played flag: set it true when you want to start the sound
    /// - use shouldBeStarted() method
    private boolean shouldBeStarted;

    /**
     * Default sound Acoustics constructor
     * @param name (String): name of the sound without path and extension. May be bull
     * @param volume (float): 0.0f no sound, 1.0f full audio
     * @param fadeIn (boolean): indicate if the song fade in until reaching its max volume
     * @param randomFirstStart (boolean): indicate if the first start is random in the sound
     * @param loop (boolean): indicate if the sound must loop on self ending
     * @param stopOthersOnStart (boolean): indicate if all other sound are stopped on given sound's start
     */
    public SoundAcoustics(String name, float volume, boolean fadeIn, boolean randomFirstStart, boolean loop, boolean stopOthersOnStart) {
        this.name = name;
        this.volume = volume;
        this.fadeIn = fadeIn;
        this.randomFirstStart = randomFirstStart;
        this.loop = loop;
        this.stopOthersOnStart = stopOthersOnStart;
        this.shouldBeStarted = false;
    }

    /**
     * Alternative sound Acoustics constructor
     * @param name (String): name of the sound without path and extension. May be null
     */
    public SoundAcoustics(String name) {
        this(name, 1.0f, false,false,false, false);
    }

    /** Set the "should be started" flag to true*/
    public void shouldBeStarted(){
        this.shouldBeStarted = true;
    }

    /// SoundAcoustics implements Acoustics

    @Override
    public void bip(Audio audio) {
        if (shouldBeStarted && audio.isSoundSupported()) {
            Sound sound = audio.getSound(name);
            audio.playSound(sound, randomFirstStart, volume, fadeIn, loop, stopOthersOnStart);
            shouldBeStarted = false;
        }
    }

    /// SoundAcoustics propose static tool

    /**
     * Stop all sounds from given audio context by sending a null sound that stop others on starts
     * @param audio (Audio): given audio context. Not null
     */
    public static void  stopAllSounds(Audio audio){
        audio.playSound(null, false, 0.0f, false,false, true);
    }
}