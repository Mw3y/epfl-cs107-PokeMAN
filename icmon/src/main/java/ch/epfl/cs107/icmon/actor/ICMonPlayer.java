package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.Collections;
import java.util.List;

/**
 * ???
 */
public final class ICMonPlayer extends ICMonActor implements Interactor {

    /**
     * ???
     */
    private final static int MOVE_DURATION = 2;
    private final static int ANIMATION_DURATION = 4;

    private final OrientedAnimation walkSprite;
    private final OrientedAnimation surfSprite;
    private final ICMonPlayerInteractionHandler handler = new ICMonPlayerInteractionHandler();
    private final ICMon.ICMonGameState gameState;
    /**
     * ???
     */
    private OrientedAnimation currentSprite;

    /**
     * ???
     *
     * @param owner       ???
     * @param orientation ???
     * @param coordinates ???
     */
    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICMon.ICMonGameState gameState) {
        super(owner, orientation, coordinates);
        walkSprite = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, getOrientation(), this);
        surfSprite = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, getOrientation(), this);
        currentSprite = walkSprite;
        resetMotion();

        this.gameState = gameState;
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
     * @param v                 (AreaInteractionVisitor) : the visitor
     * @param isCellInteraction ???
     */
    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
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
        return getOwnerArea().getKeyboard().get(Keyboard.L).isPressed();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, true);
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

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {

        private ICMonPlayerInteractionHandler() {}

        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            // Close range interaction
            if (isCellInteraction) {
                switch (cell.getWalkingType()) {
                    case SURF -> currentSprite = surfSprite;
                    case FEET -> currentSprite = walkSprite;
                    // default : Keep current sprite
                }
                currentSprite.orientate(getOrientation());
            }
        }

        @Override
        public void interactWith(ICShopAssistant npc, boolean isCellInteraction) {
            gameState.acceptInteraction(npc, isCellInteraction);
        }

        @Override
        public void interactWith(ICBall ball, boolean isCellInteraction) {
            gameState.acceptInteraction(ball, isCellInteraction);
            ball.collect();
        }
    }
}