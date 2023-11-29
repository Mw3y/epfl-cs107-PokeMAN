package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.window.Canvas;

import static ch.epfl.cs107.play.io.ResourcePath.getBackground;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class ICMonFightInteractionGraphics implements Graphics {

    private final ImageGraphics background;

    protected ICMonFightInteractionGraphics(float scaleFactor){
        background = new ImageGraphics(getBackground("fight_interaction_background"), scaleFactor, scaleFactor / 3);
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
    }

}
