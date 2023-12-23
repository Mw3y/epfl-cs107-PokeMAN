package ch.epfl.cs107.icmon.area;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public abstract class ICMonArea extends Area {

    /**
     * Creates all content of the area on load.
     */
    protected abstract void createArea();

    /**
     * Gets the player spawn position in this area.
     * @return
     */
    public abstract DiscreteCoordinates getPlayerSpawnPosition();

    /**
     * Gets the name of the ambiant sound for this area.
     * @return the name of the sound file to be played.
     */
    // TODO: Document
    public String getAmbiantSound() {
        return null;
    }

    /**
     * Default behavior when the player enters the area.
     */
    public void onEnter(ICMonPlayer player) {}

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        assert window != null;
        assert fileSystem != null;
        if (super.begin(window, fileSystem)) {
            setBehavior(new ICMonBehavior(window, getTitle()));
            createArea();
            return true;
        }
        return false;
    }

    @Override
    public final float getCameraScaleFactor() {
        return ICMon.CAMERA_SCALE_FACTOR;
    }
}
