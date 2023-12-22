package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.area.maps.UndergroundLab;

public class PassDoorMessage implements GamePlayMessage {

    private final Door door;

    /**
     * Allows to change area by using a Door.
     * @param door - The door that the player uses.
     */
    public PassDoorMessage(Door door) {
        assert door != null;
        this.door = door;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager) {
        assert player != null;
        assert gameState != null;
        assert eventManager != null;
        System.out.println("message.player.passDoor");
        gameState.changeArea(door.getDestinationAreaTitle(), door.getDestinationAreaSpawnPosition());
    }
}
