package ch.epfl.cs107.play.engine;

public interface Game extends Playable {
    /**
     * Getter for the game frame rate
     * @return (int): the desired number of frame per second
     */
    default int getFrameRate() {
    	return 24;
    }
}
