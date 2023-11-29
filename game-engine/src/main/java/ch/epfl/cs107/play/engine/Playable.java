package ch.epfl.cs107.play.engine;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;


public interface Playable extends Updatable{

    /**
     * Initialises game state : display and controls
     * Note: Need to be Override
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return (boolean): whether the game was successfully started
     */
    boolean begin(Window window, FileSystem fileSystem);

    /** Cleans up things, called even if initialisation failed.
     * Note: Need to be Override
     */
    void end();

    /**
     * Getter for game title
     * Note: Need to be Override
     * @return (String) the game title
     */
    String getTitle();
}
