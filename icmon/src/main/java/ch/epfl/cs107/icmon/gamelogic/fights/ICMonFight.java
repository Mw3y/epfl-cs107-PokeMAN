package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.math.random.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.List;

public class ICMonFight extends PauseMenu {

    private final Pokemon playerPokemon;
    private final Pokemon opponentPokemon;
    private ICMonFightAction nextPlayerAction;
    private FightState state;

    private ICMonFightActionSelectionGraphics playerActionsMenu;
    private final ICMonFightArenaGraphics arena;

    /**
     * Simulates a Pokémon fight.
     * @param playerPokemon - The Pokémon of the player
     * @param opponentPokemon - A wild Pokémon or a trainer's Pokémon
     */
    public ICMonFight(Pokemon playerPokemon, Pokemon opponentPokemon) {
        assert playerPokemon != null;
        assert opponentPokemon != null;
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponentPokemon.properties());
        this.state = FightState.INTRO;
    }

    @Override
    protected void drawMenu(Canvas canvas) {
        assert canvas != null;
        arena.draw(canvas);
    }

    /**
     * Executes the intro phase of the fight.
     * @param keyboard - The keyboard that the user uses
     */
    private void intro(Keyboard keyboard) {
        assert keyboard != null;
        drawText("Welcome to the fight");
        if (keyboard.get(Keyboard.SPACE).isPressed())
            state = FightState.SELECT_ACTION;
    }

    /**
     * Allows the player to select a fight action.
     * @param keyboard - The keyboard that the user uses
     */
    private void selectPlayerAction(Keyboard keyboard, float deltaTime) {
        assert keyboard != null;
        // Instantiate a new menu only when needed
        if (playerActionsMenu == null) {
            playerActionsMenu = new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, keyboard, playerPokemon.properties().actions());
            arena.setInteractionGraphics(playerActionsMenu);
        }
        // Update the selection fight action menu
        playerActionsMenu.update(deltaTime);

        // Fetch the player choice from the menu
        ICMonFightAction attack = playerActionsMenu.choice();
        if (attack != null) {
            nextPlayerAction = attack;
            // Reset the menu for next turn
            playerActionsMenu = null;
            state = FightState.EXEC_ACTION;
        }
    }

    /**
     * Executes the fight action that the player has selected in the previous phase.
     */
    private void executePlayerAction() {
        boolean hasSucceeded = nextPlayerAction.doAction(opponentPokemon, playerPokemon, true);
        // The player has won
        if (opponentPokemon.properties().isKO()) {
            state = FightState.ENDING;
            drawText("The player has won the fight!");
            return;
        }
        // The attack didn't finish
        if (!hasSucceeded) {
            state = FightState.ENDING;
            drawText("The player decided not to continue the fight!");
            return;
        }
        // Reset the player action
        nextPlayerAction = null;
        state = FightState.OPPONENT_ACTION;
    }

    /**
     * Makes the opponent of the player attack if possible.
     */
    private void executeOpponentAction() {
        // Check if the Pokémon can attack
        List<ICMonFightAction> attacks = opponentPokemon.properties().actions().stream()
                .filter(action -> action.type().equals(PokemonMoveType.PHYSICAL)).toList();

        // Check if the opponent can attack
        if (!attacks.isEmpty()) {
            // Use a random attack
            ICMonFightAction attack = attacks.get(RandomGenerator.getInstance().nextInt(opponentPokemon.properties().actions().size() - 1));
            // The attack didn't finish
            if (!attack.doAction(playerPokemon, opponentPokemon)) {
                state = FightState.ENDING;
                drawText("The opponent decided not to continue the fight!");
                return;
            }
            // The Pokémon of the player is KO
            if (playerPokemon.properties().isKO()) {
                state = FightState.ENDING;
                drawText("The opponent has won the fight!");
                return;
            }
        }
        state = FightState.SELECT_ACTION;
    }

    /**
     * Executes the ending phase of the fight.
     * @param keyboard - The keyboard that the user uses
     */
    private void ending(Keyboard keyboard) {
        assert keyboard != null;
        if (keyboard.get(Keyboard.SPACE).isPressed())
            end();
    }

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getKeyboard();
        // Fight sequence
        switch (state) {
            case INTRO -> intro(keyboard);
            case SELECT_ACTION -> selectPlayerAction(keyboard, deltaTime);
            case EXEC_ACTION -> executePlayerAction();
            case OPPONENT_ACTION -> executeOpponentAction();
            case ENDING -> ending(keyboard);
        }
        // Update the pause menu
        super.update(deltaTime);
    }

    /**
     * Utility method to display text on the bottom of the fight screen.
     * @param message - The message to display
     */
    private void drawText(String message) {
        assert message != null;
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, message));
    }

    @Override
    public void end() {
        state = FightState.FINISHED;
    }

    /**
     * Whether there's an ongoing fight or not.
     * @return true if the fight isn't finished.
     */
    public boolean isRunning() {
        return state != FightState.FINISHED;
    }

    /**
     * Represents the state of the fight at a given time.
     */
    public enum FightState {
        INTRO,
        SELECT_ACTION,
        EXEC_ACTION,
        OPPONENT_ACTION,
        ENDING,
        FINISHED
    }
}
