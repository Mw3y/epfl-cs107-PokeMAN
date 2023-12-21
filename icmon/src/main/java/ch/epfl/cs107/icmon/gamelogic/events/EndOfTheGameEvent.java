package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;

public class EndOfTheGameEvent extends ICMonEvent {

    private final ICMonPlayer player;

    /**
     * Constructor for EndOfGameEvent
     * @param player (ICMonPlayer)
     */
    public EndOfTheGameEvent(ICMonPlayer player) {
        assert player != null;
        this.player = player;
        onStart(new LogAction("event.endOfTheGame.start"));
        onComplete(new LogAction("event.endOfTheGame.complete"));
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        assert assistant != null;
        System.out.println("interaction.with.icshopAssistant.from.endOfTheGameEvent");
        player.openDialog("end_of_game_event_interaction_with_icshopassistant");
    }
}
