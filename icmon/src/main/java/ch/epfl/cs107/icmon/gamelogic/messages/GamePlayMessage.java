package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;

public interface GamePlayMessage {
    void process(ICMon.ICMonGameState game, ICMonPlayer player);
}
