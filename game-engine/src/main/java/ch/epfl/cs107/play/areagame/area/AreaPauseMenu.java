package ch.epfl.cs107.play.areagame.area;


import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

/**
 * AreaPauseMenu extends standard a Pause menu. It is a context pause menu. Pause menu not of the game
 * but of a specific area.
 * When a game is in pause mode, you can update this instead of the currentArea
 */
public abstract class AreaPauseMenu extends PauseMenu {

    /// Owner Area
    private boolean isResumeRequested;

    /** Setter for the resume request*/
    protected void requestAreaResume(){
        getOwner().requestResume();
        isResumeRequested = true;
    }

    /** @return (boolean): true if the resume is requested*/
    protected boolean isResumeRequested(){
        return isResumeRequested;
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        isResumeRequested = false;
        return super.begin(window, fileSystem);
    }

    @Override
    public String getTitle() {
        return "Area Pause Menu";
    }
}
