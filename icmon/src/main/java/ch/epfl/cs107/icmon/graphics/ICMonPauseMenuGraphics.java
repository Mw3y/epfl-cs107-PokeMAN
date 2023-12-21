package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonPauseMenuGraphics implements Graphics {

    private ImageGraphics background;

    /**
     * The graphics of the main menu.
     * @param scaleFactor
     */
    public ICMonPauseMenuGraphics(float scaleFactor) {
        background = new ImageGraphics(ResourcePath.getBackground("main_menu"), scaleFactor, scaleFactor);
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
    }
}
