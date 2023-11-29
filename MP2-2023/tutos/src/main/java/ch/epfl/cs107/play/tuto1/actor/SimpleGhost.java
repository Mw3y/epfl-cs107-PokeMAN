package ch.epfl.cs107.play.tuto1.actor;

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
    private float hp;
    /** ??? */
    private final TextGraphics hpText;
    /** ??? */
    private final Sprite sprite;

    /**
     * ???
     * @param position ???
     * @param spriteName ???
     */
    public SimpleGhost(Vector position, String spriteName) {
        super(position);
        this.hp = 10;
        sprite = new Sprite(spriteName, 1f, 1f, this);
        hpText = new TextGraphics(Integer.toString((int) hp), .4f, Color.BLUE);
        hpText.setParent(this);
        hpText.setAnchor(new Vector(-.3f, .1f));
    }

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (hp > 0f) {
            hp -= deltaTime;
            hpText.setText(Integer.toString((int) hp));
        }
        if (hp < 0f) hp = 0f;
    }

    /**
     * ???
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        hpText.draw(canvas);
    }

    /**
     * ???
     * @param delta ???
     */
    public void moveUp(float delta) {
        setCurrentPosition(getPosition().add(0f, delta));
    }

    /**
     * ???
     * @param delta ???
     */
    public void moveDown(float delta) {
        setCurrentPosition(getPosition().add(0f, -delta));
    }

    /**
     * ???
     * @param delta ???
     */
    public void moveLeft(float delta) {
        setCurrentPosition(getPosition().add(-delta, 0f));
    }

    /**
     * ???
     * @param delta ???
     */
    public void moveRight(float delta) {
        setCurrentPosition(getPosition().add(delta, 0f));
    }

    /**
     * ???
     * @return ???
     */
    public boolean isWeak() {
        return (hp == 0f);
    }

    /**
     * ???
     */
    public void strengthen() {
        hp = 10;
    }

}