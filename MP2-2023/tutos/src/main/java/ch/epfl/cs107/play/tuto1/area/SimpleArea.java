package ch.epfl.cs107.play.tuto1.area;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.tuto1.Tuto1;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;

/**
 * ???
 */
public abstract class SimpleArea extends Area {

	/** ??? */
    private Window window;

	/**
	 * ???
	 */
    protected abstract void createArea();

	/**
	 * ???
	 * @param window (Window): display context. Not null
	 * @param fileSystem (FileSystem): given file system. Not null
	 * @return ???
	 */
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		if (super.begin(window, fileSystem)) {
			// Set the behavior map
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
    public int getWidth() {
        Image behaviorMap = window.getImage(ResourcePath.getBehavior(getTitle()), null, false);
        return behaviorMap.getWidth();
    }

	/**
	 * ???
	 * @return ???
	 */
    @Override
    public int getHeight() {
        Image behaviorMap = window.getImage(ResourcePath.getBehavior(getTitle()), null, false);
        return behaviorMap.getHeight();
    }

	/**
	 * ???
	 * @return ???
	 */
    @Override
    public final float getCameraScaleFactor() {
        return Tuto1.CAMERA_SCALE_FACTOR;
    }

}
