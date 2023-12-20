package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.Nurse;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.icmon.area.cells.behaviors.TallGrass;
import ch.epfl.cs107.icmon.gamelogic.messages.GamePlayMessage;
import ch.epfl.cs107.icmon.gamelogic.messages.PassDoorMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.engine.actor.OrientedAnimation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ICMonPlayer extends ICMonActor implements Interactor {

    private final static int WALK_MOVE_DURATION = 4;
    private final static int SPRINT_MOVE_DURATION = 2;
    private final static int ANIMATION_DURATION = 4;

    private final OrientedAnimation walkSprite;
    private final OrientedAnimation sprintSprite;
    private final OrientedAnimation surfSprite;
    private final ICMonPlayerInteractionHandler handler = new ICMonPlayerInteractionHandler();
    private final ICMon.ICMonGameState gameState;
    private final List<Pokemon> pokemons = new ArrayList<>();
    private Dialog dialog;
    private OrientedAnimation currentSprite;
    private boolean isSprinting = false;

    /**
     * The main character of the ICMon game.
     *
     * @param owner       - The area in which the player currently evolves
     * @param orientation - The default orientation of the player sprite
     * @param coordinates - The default coordinates of the player in its owner area.
     */
    public ICMonPlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, ICMon.ICMonGameState gameState) {
        super(owner, orientation, coordinates);
        walkSprite = new OrientedAnimation("actors/player", ANIMATION_DURATION / 2, getOrientation(), this);
        sprintSprite = new OrientedAnimation("actors/player_sprint", ANIMATION_DURATION / 2, getOrientation(), this);
        surfSprite = new OrientedAnimation("actors/player_water", ANIMATION_DURATION / 2, getOrientation(), this);
        currentSprite = walkSprite;
        resetMotion();

        this.gameState = gameState;
    }

    private void setSprite(OrientedAnimation sprite) {
        assert sprite != null;
        currentSprite = sprite;
    }

    /**
     * Gives a Pokémon to the player.
     * @param pokemon - The Pokémon to add to the player inventory.
     * @return true if the operation was successful.
     */
    public boolean givePokemon(Pokemon pokemon) {
        assert pokemon != null;
        return pokemons.add(pokemon);
    }

    /**
     * Whether the player has at least one Pokémon capable of fighting.
     * @return true if a Pokémon has more than 0 hp.
     */
    public boolean hasHealthyPokemon() {
        for (Pokemon pokemon : pokemons) {
            if (!pokemon.properties().isKO())
                return true;
        }
        return false;
    }

    /**
     * Whether the player has at least one Pokémon that does not have full hp.
     * @return true if a Pokémon has lost some hp.
     */
    public boolean hasPokemonNeedingHeal() {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.properties().hp() < pokemon.properties().maxHp())
                return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        // Handle the dialog state
        if (isDialogInProgress()) {
            if (keyboard.get(Keyboard.SPACE).isPressed())
                dialog.update(deltaTime);
            if (dialog.isCompleted())
                closeDialog();
            return;
        }

        isSprinting = keyboard.get(Keyboard.S).isDown();
        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        // Animate the player sprite on movement
        if (!isDisplacementOccurs()) currentSprite.reset();
        else currentSprite.update(deltaTime);

        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        assert canvas != null;
        if (isDialogInProgress()) {
            dialog.draw(canvas);
        }
        currentSprite.draw(canvas);
    }

    /**
     * Requests heal from a PokeCenter nurse.
     * @param nurse - The nurse NPC
     * @return true if the nurse accepted to heal the player Pokémon
     */
    public boolean requestHealFromNurse(Nurse nurse) {
        assert nurse != null;
        return nurse.healPokemons(pokemons);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        assert v != null;
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
        return !isDialogInProgress() && getOwnerArea().getKeyboard().get(Keyboard.L).isPressed();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        assert other != null;
        other.acceptInteraction(handler, true);
    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, Button b) {
        assert orientation != null;
        assert b != null;
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(isSprinting ? SPRINT_MOVE_DURATION : WALK_MOVE_DURATION);
            }
        }
    }

    /**
     * Starts a new dialog with the player.
     * @param name - The name of the dialog file (without extension).
     */
    public void openDialog(String name) {
        assert name != null;
        dialog = new Dialog(name);
    }

    /**
     * Forcefully closes an ongoing dialog.
     */
    public void closeDialog() {
        dialog = null;
    }

    /**
     * Gets the dialog state of the player.
     * @return true if the player is currently engaged in a dialog.
     */
    public boolean isDialogInProgress() {
        return dialog != null;
    }

    private class ICMonPlayerInteractionHandler implements ICMonInteractionVisitor {

        /**
         * The interation handler of the player.
         * Defines the behavior for interacting with a certain entity.
         */
        private ICMonPlayerInteractionHandler() {}

        @Override
        public void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {
            assert cell != null;
            // Close range interaction
            if (isCellInteraction) {
                switch (cell.getWalkingType()) {
                    case SURF -> setSprite(surfSprite);
                    case FEET ->
                            setSprite(isSprinting ? sprintSprite : walkSprite);
                    // default : Keep current sprite
                }

                if (isDisplacementOccurs() && hasHealthyPokemon()) {
                    if (cell.getType() == ICMonBehavior.ICMonCellType.TALL_GRASS && TallGrass.hasHiddenPokemon())
                        TallGrass.hiJackPlayer(ICMonPlayer.this, ICMonPlayer.this.getOwnerArea());
                }

                currentSprite.orientate(getOrientation());
            }
        }

        @Override
        public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
            assert assistant != null;
            gameState.acceptInteraction(assistant, isCellInteraction);
        }

        @Override
        public void interactWith(Door door, boolean isCellInteraction) {
            assert door != null;
            if (isCellInteraction) {
                GamePlayMessage message = new PassDoorMessage(door);
                gameState.send(message);
            }
            gameState.acceptInteraction(door, isCellInteraction);
        }

        @Override
        public void interactWith(ICBall ball, boolean isCellInteraction) {
            assert ball != null;
            ball.collect();
            gameState.acceptInteraction(ball, isCellInteraction);
        }

        @Override
        public void interactWith(Pokemon pokemon, boolean isCellInteraction) {
            assert pokemon != null;
            if (hasHealthyPokemon()) {
                pokemon.fight(gameState, pokemons.get(0));
            }
            else openDialog("fight_impossible");
            gameState.acceptInteraction(pokemon, isCellInteraction);
        }

        @Override
        public void interactWith(ProfOak profOak, boolean isCellInteraction) {
            assert profOak != null;
            gameState.acceptInteraction(profOak, isCellInteraction);
        }

        @Override
        public void interactWith(Trainer trainer, boolean isCellInteraction) {
            assert trainer != null;
            if (hasHealthyPokemon() && trainer.acceptsFights()) {
                trainer.fight(gameState, pokemons.get(0));
            }
            else openDialog("fight_impossible");
            gameState.acceptInteraction(trainer, isCellInteraction);
        }

        @Override
        public void interactWith(Nurse nurse, boolean isCellInteraction) {
            assert nurse != null;
            if (hasPokemonNeedingHeal()) {
                openDialog("nurse_heal_pokemon");
                requestHealFromNurse(nurse);
            } 
            else openDialog("nurse_no_pokemon_to_heal");
            gameState.acceptInteraction(nurse, isCellInteraction);
        }
    }
}