package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
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
import java.util.Map;

/**
 * ???
 */
public final class ICMonPlayer extends MovableAreaEntity implements Interactor {

    enum PlayerSprite {
        GROUND
    }

    /**
     * ???
     */
    private final static int MOVE_DURATION = 2;
    private final static int ANIMATION_DURATION = 4;

    /**
     * ???
     */
    private OrientedAnimation currentSprite;
    private OrientedAnimation walkSprite;
    private OrientedAnimation surfSprite;


    private final ICMonPlayerInteractionHandler handler = new ICMonPlayerInteractionHandler();

    /**
     * ???
     *
     * @param owner       ???
     * @param orientation ???
     * @param coordinates ???
     */
    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        walkSprite = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, getOrientation(), this);
        surfSprite = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, getOrientation(), this);
        currentSprite = walkSprite;
        resetMotion();
    }

    private OrientedAnimation createSprite(String spriteName) {
        return new OrientedAnimation(spriteName, ANIMATION_DURATION / 2, getOrientation(), this);
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
        if (!isDisplacementOccurs()) currentSprite.reset();
        else currentSprite.update(deltaTime);

        super.update(deltaTime);
    }

    /**
     * ???
     *
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        currentSprite.draw(canvas);
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

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return getOwnerArea().getKeyboard().get(Keyboard.L).isDown();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    /**
     * ???
     *
     * @param v                 (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction ???
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
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
                currentSprite.orientate(orientation);
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

    private class ICMonPlayerInteractionHandler implements  ICMonInteractionVisitor {
        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            // Close range interaction
            if (isCellInteraction) {
               switch (cell.getWalkingType()) {
                   case SURF -> currentSprite = surfSprite;
                   case FEET -> currentSprite = walkSprite;
                   // default : Keep current sprite
               }
            }
        }

        @Override
        public void interactWith(ICBall ball, boolean isCellInteraction) {
            ball.collect();
        }
    }
}