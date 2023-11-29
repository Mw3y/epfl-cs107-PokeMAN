package ch.epfl.cs107.icmon.graphics;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.engine.actor.Graphics;
import ch.epfl.cs107.play.engine.actor.ImageGraphics;
import ch.epfl.cs107.play.engine.actor.ShapeGraphics;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.shape.Polyline;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

import static ch.epfl.cs107.play.io.ResourcePath.getSprite;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class ICMonFightInfoGraphics implements Graphics, Positionable {

    private static final float FONT_SIZE = .6f;
    private static final float HP_BAR_SIZE = 3.7f;

    private final Vector position;
    private final Pokemon.PokemonProperties properties;

    private final ImageGraphics background;
    private final TextGraphics name;
    private final ImageGraphics hpBackground;
    private final ShapeGraphics hpBar;
    private final Vector hpBarStart;

    public ICMonFightInfoGraphics(Vector position, Pokemon.PokemonProperties properties){
        // HR : set the position
        this.position = position;
        this.properties = properties;

        // HR : Add the background
        background = new ImageGraphics(getSprite("dialog"), 6f, 2f);
        background.setParent(this);

        // HR : Add the Pokémon's name
        name = new TextGraphics(properties.name(), FONT_SIZE, Color.BLACK,
                null, 0.0f, false, false,
                new Vector(0.3f,2f), TextAlign.Horizontal.LEFT,
                TextAlign.Vertical.TOP, 1.0f, 1001);
        name.setParent(this);

        // HR : Add the HP Bar
        // HR : It's background
        var hpAnchor = new Vector(0.3f,0.3f);
        hpBackground = new ImageGraphics(getSprite("hp_bar"), 5.4f, 0.5f, null, hpAnchor);
        hpBackground.setParent(this);
        hpBarStart = hpAnchor.add(1.3f, 0.25f);
        hpBar = new ShapeGraphics(new Polyline(hpBarStart, computeHpBarEnd(properties.hp(), properties.maxHp())), Color.green, Color.green, 0.4f);
        hpBar.setParent(this);
    }


    @Override
    public void draw(Canvas canvas) {
        // HR : Draw the background
        background.draw(canvas);
        // HR : Draw the name
        name.draw(canvas);
        // HR : Draw the HP Bar
        hpBackground.draw(canvas);
        // HR : Update the hp bar
        hpBar.setShape(new Polyline(hpBarStart, computeHpBarEnd(properties.hp(), properties.maxHp())));
        hpBar.draw(canvas);
    }

    private Vector computeHpBarEnd(float hp, float maxhp){
        return hpBarStart.add(HP_BAR_SIZE * hp / maxhp, 0);
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
