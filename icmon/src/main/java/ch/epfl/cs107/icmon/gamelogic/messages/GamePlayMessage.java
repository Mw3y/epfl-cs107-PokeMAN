package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;

/**
 * A message that can be dispatched to the game state for execution with enhanced game access.
 */
public interface GamePlayMessage {

    /**
     * Executes the gameplay message content.
     * @param player - The main character of the game
     * @param gameState - The game state
     * @param eventManager - The event manager of the game
     */
    void process(ICMonPlayer player, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager);
}
