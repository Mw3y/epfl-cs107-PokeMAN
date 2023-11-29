
package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import static ch.epfl.cs107.play.io.ResourcePath.getSprite;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class ICMonFighterInfoGraphics implements Graphics, Positionable {

    private final Vector position;

    private final ImageGraphics background;

    public ICMonFighterInfoGraphics(Vector position, Pokemon.PokemonProperties properties){
        // HR : set the position
        this.position = position;

        // HR : Add the background
        background = new ImageGraphics(getSprite("dialog"), 6f, 2f);
        background.setParent(this);
    }


    @Override
    public void draw(Canvas canvas) {
        // HR : Draw the background
        background.draw(canvas);
    }

    @Override
    public Transform getTransform() {
        return Transform.I.translated(getPosition().x, getPosition().y);
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public Vector getVelocity() {
        return Vector.ZERO;
    }
}