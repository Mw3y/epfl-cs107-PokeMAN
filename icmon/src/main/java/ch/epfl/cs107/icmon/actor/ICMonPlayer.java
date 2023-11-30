package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.play.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
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
public final class ICMonPlayer extends MovableAreaEntity {

    /**
     * ???
     */
    private final static int MOVE_DURATION = 2;
    private final static int ANIMATION_DURATION = 4;

    /**
     * ???
     */
    private final OrientedAnimation sprite;

    /**
     * ???
     *
     * @param owner       ???
     * @param orientation ???
     * @param coordinates ???
     * @param spriteName  ???
     */
    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);
        sprite = new OrientedAnimation(spriteName, ANIMATION_DURATION / 2, orientation, this);
        resetMotion();
    }

    /**
     * ???
     *
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        // Animate the player sprite on movement
        if (!isDisplacementOccurs()) sprite.reset();
        else sprite.update(deltaTime);

        super.update(deltaTime);
    }

    /**
     * ???
     *
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    /**
     * ???
     *
     * @return ???
     */
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    /**
     * ???
     *
     * @return ???
     */
    @Override
    public boolean isCellInteractable() {
        return true;
    }

    /**
     * ???
     *
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
     *
     * @param v                 (AreaInteractionVisitor) : the visitor
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
                sprite.orientate(orientation);
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
     *
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
     * Center the camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }
}