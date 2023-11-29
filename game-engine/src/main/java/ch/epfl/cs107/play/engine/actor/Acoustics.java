package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.window.Audio;


public interface Acoustics {

    /**
     * Play itself on specified Audio context.
     * @param audio (Audio) target, not null
     */
    void bip(Audio audio);
}
