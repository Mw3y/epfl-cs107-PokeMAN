package ch.epfl.cs107.play.tuto2.area;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.tuto2.Tuto2;
import ch.epfl.cs107.play.window.Window;

/**
 * ???
 */
public abstract class Tuto2Area extends Area {

    /**
     * ???
     */
    protected abstract void createArea();

    /**
     * ???
     * @return ???
     */
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /**
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            setBehavior(new Tuto2Behavior(window, getTitle()));
            createArea();
            return true;
        }
        return false;
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public final float getCameraScaleFactor() {
        return Tuto2.CAMERA_SCALE_FACTOR;
    }

}
