package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightActionSelectionGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonFight extends PauseMenu {

    private final Pokemon playerPokemon;
    private final Pokemon opponentPokemon;

    private ICMonFightActionSelectionGraphics playerActionsMenu;

    private final ICMonFightArenaGraphics arena;
    private ICMonFightAction nextPlayerAction;
    private FightState state;

    public ICMonFight(Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponentPokemon.properties());
        this.state = FightState.INTRO;
    }

    @Override
    protected void drawMenu(Canvas c) {
        arena.draw(c);
    }

    public void update(float deltaTime) {
        Keyboard keyboard = getKeyboard();

        switch (state) {
            case INTRO: {
                drawText("Welcome to the fight");
                if (keyboard.get(Keyboard.SPACE).isPressed())
                    state = FightState.SELECT_ACTION;
                break;
            }
            case SELECT_ACTION: {
                // TODO: Better code
                if (playerActionsMenu == null || nextPlayerAction != null) {
                    this.nextPlayerAction = null;
                    this.playerActionsMenu = new ICMonFightActionSelectionGraphics(CAMERA_SCALE_FACTOR, getKeyboard(), playerPokemon.properties().actions());
                    arena.setInteractionGraphics(playerActionsMenu);
                }
                playerActionsMenu.update(deltaTime);

                final ICMonFightAction attack = playerActionsMenu.choice();
                if (attack != null) {
                    nextPlayerAction = attack;
                    state = FightState.EXEC_ACTION;
                }
                break;
            }
            case EXEC_ACTION: {
                // TODO: Execute action
                final boolean hasSucceeded = nextPlayerAction.doAction(opponentPokemon);
                // The player has won
                if (opponentPokemon.properties().hp() <= 0) {
                    state = FightState.ENDING;
                    drawText("The player has won the fight!");
                    break;
                }
                // The attack didn't finish
                if (!hasSucceeded) {
                    state = FightState.ENDING;
                    drawText("The player decided not to continue the fight!");
                    break;
                }
                
                state = FightState.OPPONENT_ACTION;
                break;
            }
            case OPPONENT_ACTION: {
                // Check if the pokemon can attack
                final ICMonFightAction attack = opponentPokemon.properties().actions().stream()
                        .filter(action -> action.name().equals("Attack")).findFirst().orElse(null);

                if (attack != null) {
                    // The attack didn't finish
                    if (!attack.doAction(playerPokemon)) {
                        state = FightState.ENDING;
                        drawText("The opponent decided not to continue the fight!");
                        break;
                    }

                    // The pokemon of the player is KO
                    if (opponentPokemon.properties().hp() <= 0) {
                        state = FightState.ENDING;
                        drawText("The opponent has won the fight!");
                        break;
                    }
                }

                state = FightState.SELECT_ACTION;

                break;
            }
            case ENDING: {
                if (keyboard.get(Keyboard.SPACE).isPressed())
                    end();
                break;
            }
        }

        super.update(deltaTime);
    }

    private void drawText(String message) {
        arena.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, message));
    }

    @Override
    public void end() {
        state = FightState.FINISHED;
    }

    public boolean isRunning() {
        return state != FightState.FINISHED;
    }

    public enum FightState {
        INTRO,
        SELECT_ACTION,
        EXEC_ACTION,
        OPPONENT_ACTION,
        ENDING,
        FINISHED
    }
}
