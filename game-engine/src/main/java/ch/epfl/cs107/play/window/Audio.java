package ch.epfl.cs107.play.window;

/**
 * Represents a audio context, with playing capability.
 */
public interface Audio {

    /**
     * Gets sound from file system.
     * @param name (String): full name of image, not null
     * @return (Sound): a sound object, null on error
     */
    Sound getSound(String name);

    /**
     * Play specified sound.
     * @param sound (Sound): any sound associated to this context, may be null
     * @param randomFirstStart (boolean): indicate if the first start is random in the sound
     * @param volume (float): 0.0f no sound, 1.0f full audio
     * @param fadeIn (boolean): indicate if the song fade in until reaching its max volume
     * @param loop (boolean): indicate if the sound must loop on self ending
     * @param stopOthersOnStart (boolean): indicate if all other sound are stopped on given sound's start
     */
    void playSound(Sound sound, boolean randomFirstStart, float volume, boolean fadeIn, boolean loop, boolean stopOthersOnStart);

    /**@return (boolean): true if the sound is supported by the Audio*/
    boolean isSoundSupported();
}
