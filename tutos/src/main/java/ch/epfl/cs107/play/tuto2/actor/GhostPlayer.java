package ch.epfl.cs107.play.tuto2.actor;

import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Sprite;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * ???
 */
public final class GhostPlayer extends MovableAreaEntity {

    /** ??? */
    private final static int MOVE_DURATION = 8;
    /** ??? */
    private final TextGraphics message;
    /** ??? */
    private final Sprite sprite;
    /** ??? */
    private float hp;

    /**
     * ???
     * @param owner ???
     * @param orientation ???
     * @param coordinates ???
     * @param spriteName ???
     */
    public GhostPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
        this.hp = 10;
        message = new TextGraphics(Integer.toString((int) hp), 0.4f, Color.BLUE);
        message.setParent(this);
        message.setAnchor(new Vector(-0.3f, 0.1f));
        sprite = new Sprite(spriteName, 1.f, 1.f, this);
        resetMotion();
    }

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (hp > 0) {
            hp -= deltaTime;
            message.setText(Integer.toString((int) hp));
        }
        if (hp < 0) hp = 0.f;
        Keyboard keyboard = getOwnerArea().getKeyboard();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        super.update(deltaTime);
    }

    /**
     * ???
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        message.draw(canvas);
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public boolean isViewInteractable() {
        return true;
    }

    /**
     * ???
     */
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * ???
     * @param v (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction ???
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * ???
     * @param area     (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        area.setViewCandidate(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
    }

    /**
     * ???
     * @return ???
     */
    public boolean isWeak() {
        return (hp <= 0.f);
    }

    /**
     * Center the camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    /**
     * ???
     */
    public void strengthen() {
        hp = 10;
    }

}