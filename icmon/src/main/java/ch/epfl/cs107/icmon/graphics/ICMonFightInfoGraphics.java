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
    private ShapeGraphics hpBar;
    private Vector hpBarStart;

    private enum HpBarColor {
        HEALTHY(new Color(112, 248, 168)),
        LOW(new Color(248, 224, 56)),
        CRITICAL(new Color(248, 88, 56));

        private final Color color;

        HpBarColor(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private Color hpBarColor = HpBarColor.HEALTHY.getColor();

    public ICMonFightInfoGraphics(Vector position, Pokemon.PokemonProperties properties, boolean isPlayerInfos){
        // HR : set the position
        this.position = position;
        this.properties = properties;

        float xOffset = isPlayerInfos ? 0.3f : 0f;
        String spriteName = isPlayerInfos
                ? "fight_player_pokemon_infos"
                : "fight_opponent_pokemon_infos";

        // HR : Add the background
        background = new ImageGraphics(getSprite(spriteName), 6f, 2f);
        background.setParent(this);

        // HR : Add the Pokémon's name
        name = new TextGraphics(properties.name().toUpperCase(), FONT_SIZE, Color.BLACK,
                null, 0.0f, false, false,
                new Vector((float) (xOffset * 1.55 + .35f),1.9f), TextAlign.Horizontal.LEFT,
                TextAlign.Vertical.TOP, 1.0f, 1001);
        name.setParent(this);

        // HR : Add the HP Bar
        // HR : It's background
        var hpAnchor = new Vector(xOffset + 1.55f,0.5f);
        hpBackground = new ImageGraphics(getSprite("hp_bar"), 3.75f, 0.45f, null, hpAnchor);
        hpBackground.setParent(this);
        hpBarStart = hpAnchor.add(1.05f, 0.24f);
        hpBar = new ShapeGraphics(new Polyline(hpBarStart, computeHpBarEnd(properties.hp(), properties.maxHp())),
                HpBarColor.HEALTHY.getColor(), HpBarColor.HEALTHY.getColor(), 0.2f);
        hpBar.setParent(this);
    }


    @Override
    public void draw(Canvas canvas) {
        // HR : Draw the background
        background.draw(canvas);
        // HR : Draw the name
        name.draw(canvas);
        // HR : Draw the pokemon in-battle menu
        hpBackground.draw(canvas);
        // HR : Update the hp bar
        hpBar.setShape(new Polyline(hpBarStart, computeHpBarEnd(properties.hp(), properties.maxHp())));
        // Change hp bar color based on pokemon health
        if (properties.hasLowHp()) {
            hpBarColor = HpBarColor.LOW.getColor();
        }
        if (properties.hasCriticalHp()) {
            hpBarColor = HpBarColor.CRITICAL.getColor();
        }
        hpBar.setFillColor(hpBarColor);
        hpBar.setOutlineColor(hpBarColor);
        // HR : Draw the hp bar
        hpBar.draw(canvas);
    }

    private Vector computeHpBarEnd(float hp, float maxhp){
        return hpBarStart.add((float) (HP_BAR_SIZE * hp / maxhp / 1.5), 0);
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
