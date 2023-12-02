package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.misc.Door;

public class PassDoorMessage implements GamePlayMessage {

    private final Door door;

    public PassDoorMessage(Door door) {
        this.door = door;
    }

    @Override
    public void process(ICMon.ICMonGameState gameState, ICMonPlayer player) {
        gameState.changeArea(door.getDestinationAreaTitle(), door.getDestinationAreaSpawnPosition());
    }
}
