package ch.epfl.cs107.play.tuto2.actor;

import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

/**
 * ???
 */
public final class SimpleGhost extends Entity {

    /** ??? */
    private final TextGraphics message;
    /** ??? */
    private final Sprite sprite;
    /** ??? */
    private float hp;

    /**
     * ???
     *
     * @param position   ???
     * @param spriteName ???
     */
    public SimpleGhost(Vector position, String spriteName) {
        super(position);
        this.hp = 10;
        sprite = new Sprite(spriteName, 1.f, 1.f, this);
        message = new TextGraphics(Integer.toString((int) hp), 0.4f, Color.BLUE);
        message.setParent(this);
        message.setAnchor(new Vector(-0.3f, 0.1f));
    }

    /**
     * ???
     *
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (hp > 0) {
            hp -= deltaTime;
            message.setText(Integer.toString((int) hp));
        }
        if (hp < 0) hp = 0.f;

    }

    /**
     * ???
     *
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        message.draw(canvas);
    }

}