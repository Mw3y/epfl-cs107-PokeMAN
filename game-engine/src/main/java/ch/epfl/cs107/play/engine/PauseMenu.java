package ch.epfl.cs107.play.engine;

import ch.epfl.cs107.play.engine.actor.Acoustics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public abstract class PauseMenu implements Playable, Acoustics{

    public interface Pausable{
        void requestResume();
        void requestPause();
        boolean isPaused();
    }

    // Owner which called this pause. Initially null, need to call setOwner
    private Pausable owner;
    /// Display dimension scale
    /// Context objects
    private Window window;
    //private FileSystem fileSystem; // TODO link it to save concept

    protected static final float CAMERA_SCALE_FACTOR = 13;

    /**
     * Draw the entire menu (background, texts, etc.)
     * @param c (Canvas): the context canvas : here the Window
     */
    protected abstract void drawMenu(Canvas c);

    /** @return (Keyboard): the Window Keyboard for inputs */
    protected Keyboard getKeyboard(){
        return window.getKeyboard();
    }

    /** @return (Pausable): the owner which starts this pause */
    protected Pausable getOwner(){
        return owner;
    }

    /**
     * Set the owner Pausable
     * @param owner (Pausable): the owner which starts this pause. Not null
     */
    public void setOwner(Pausable owner){
        this.owner = owner;
    }

    /// PauseMenu Implements Acoustics

    @Override
    public void bip(Audio audio) {
        // By default there is nothing to bip
        // Must be overridden by children who wants beep
    }


    /// PauseMenu implements Playable

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        this.window = window;
        //this.fileSystem = fileSystem;
        return true;
    }

    @Override
    public void update(float deltaTime) {

        // Center the view on the center of the menu
        Transform viewTransform = Transform.I.scaled(CAMERA_SCALE_FACTOR).translated(CAMERA_SCALE_FACTOR /2, CAMERA_SCALE_FACTOR /2);
        window.setRelativeTransform(viewTransform);

        // Draw the menu
        drawMenu(window);
        bip(window);
    }

    @Override
    public void end() {}

    @Override
    public String getTitle() {
        return "Pause Menu";
    }
}
