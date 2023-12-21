package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.audio.AudioPreset;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.math.random.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ICMonFight extends PauseMenu {

    private final ICMon.ICMonGameState gameState;
    private final Pokemon playerPokemon;
    private final Pokemon opponentPokemon;
    private final Trainer trainer;
    private final ICMonFightArenaGraphics arena;
    private ICMonFightAction nextPlayerAction;
    private ICMonFightAction nextOpponentAction;

    private FightState state;
    private ICMonFightActionSelectionGraphics playerActionsMenu;
    private boolean isPlayingVictorySound;

    private Queue<String> introMessages = new LinkedList<>();

    /**
     * Simulates a Pokémon fight.
     *
     * @param playerPokemon   - The Pokémon of the player
     * @param opponentPokemon - A wild Pokémon or a trainer's Pokémon
     */
    public ICMonFight(ICMon.ICMonGameState gameState, Pokemon playerPokemon, Pokemon opponentPokemon) {
        this(gameState, playerPokemon, opponentPokemon, null);
    }

    /**
     * Simulates a Pokémon fight.
     *
     * @param playerPokemon   - The Pokémon of the player
     * @param opponentPokemon - A wild Pokémon or a trainer's Pokémon
     * @param trainer         - The trainer that the player challenged
     */
    public ICMonFight(ICMon.ICMonGameState gameState, Pokemon playerPokemon, Pokemon opponentPokemon, Trainer trainer) {
        assert gameState != null;
        assert playerPokemon != null;
        assert opponentPokemon != null;

        this.gameState = gameState;
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponentPokemon.properties());
        this.state = FightState.INTRO;
        this.trainer = trainer;

        introMessages.add("Welcome to the fight!");
        if (trainer != null)
            introMessages.add(trainer.name().toUpperCase() + " sent out " + opponentPokemon.properties().name().toUpperCase() + ".");

        gameState.stopAllSounds();
        gameState.playSound("battle_wild_pokemon", AudioPreset.FIGHT_MUSIC);
    }

    @Override
    protected void drawMenu(Canvas canvas) {
        assert canvas != null;
        arena.draw(canvas);
    }

    /**
     * Executes the intro phase of the fight.
     *
     * @param keyboard - The keyboard that the user uses
     */
    private void intro(Keyboard keyboard) {
        assert keyboard != null;

        drawText(introMessages.peek());
        if (keyboard.get(Keyboard.SPACE).isPressed()) {
            introMessages.poll();
            if (introMessages.isEmpty())
                state = FightState.SELECT_ACTION;
        }
    }

    /**
     * Allows the player to select a fight action.
     *
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
    private void executePlayerAction(Keyboard keyboard) {
        // Display attack infos
        if (!keyboard.get(Keyboard.SPACE).isPressed()) {
            drawAttackAnnouncementText(playerPokemon.properties().name(), nextPlayerAction);
            return;
        } else gameState.playSound("button", AudioPreset.SFX);

        gameState.playSound(nextPlayerAction.sfx(), AudioPreset.SFX);
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
     *
     * @param keyboard - The keyboard that the user uses
     */
    private void executeOpponentAction(Keyboard keyboard) {
        // Check if the Pokémon can attack
        List<ICMonFightAction> attacks = opponentPokemon.properties().actions().stream()
                .filter(action -> action.type().equals(PokemonMoveType.PHYSICAL)).toList();

        // Check if the opponent can attack
        if (!attacks.isEmpty()) {
            // Only select a random attack if necessary
            if (nextOpponentAction == null)
                // Use a random attack
                nextOpponentAction = attacks.get(RandomGenerator.getInstance().nextInt(opponentPokemon.properties().actions().size() - 1));

            // Display attack infos
            if (!keyboard.get(Keyboard.SPACE).isPressed()) {
                String name = trainer == null
                        ? opponentPokemon.properties().name()
                        : trainer.name();

                drawAttackAnnouncementText(name, nextOpponentAction);
                return;
            } else gameState.playSound("button", AudioPreset.SFX);

            gameState.playSound(nextOpponentAction.sfx(), AudioPreset.SFX);
            // The attack didn't finish
            if (!nextOpponentAction.doAction(playerPokemon, opponentPokemon)) {
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
            // Reset next action
            nextOpponentAction = null;
        }
        state = FightState.SELECT_ACTION;
    }

    /**
     * Executes the ending phase of the fight.
     *
     * @param keyboard - The keyboard that the user uses
     */
    private void ending(Keyboard keyboard) {
        assert keyboard != null;
        if (!isPlayingVictorySound) {
            isPlayingVictorySound = true;
            try {
                // Wait for the other sound effects to finish
                Thread.sleep((long) 5e2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameState.stopAllSounds();
            gameState.playSound("victory_against_wild_pokemon", AudioPreset.SFX);
        }
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
            case EXEC_ACTION -> executePlayerAction(keyboard);
            case OPPONENT_ACTION -> executeOpponentAction(keyboard);
            case ENDING -> ending(keyboard);
        }
        // Update the pause menu
        super.update(deltaTime);
    }

    /**
     * Draws the attack announcement dialog.
     *
     * @param name       - The name of entity attacking
     * @param nextAction - The action to execute for this round
     */
    private void drawAttackAnnouncementText(String name, ICMonFightAction nextAction) {
        drawText(name.toUpperCase() + " uses " + nextAction.name() + "!");
    }

    /**
     * Utility method to display text on the bottom of the fight screen.
     *
     * @param message - The message to display
     */
    private void drawText(String message) {
        assert message != null;
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, message));
    }

    @Override
    public void end() {
        state = FightState.FINISHED;
        gameState.stopAllSounds();
    }

    /**
     * Whether there's an ongoing fight or not.
     *
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
