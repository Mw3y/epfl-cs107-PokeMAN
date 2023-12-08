package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.SetTrainerFightsAcceptance;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.engine.PauseMenu;

public class FirstInteractionWithGarryEvent extends ICMonEvent {

    private final Garry garry;

    public FirstInteractionWithGarryEvent(ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMonPlayer player, Garry garry) {
        super(gameState, eventManager, player);
        this.garry = garry;

        onStart(new LogAction("event.firstInteractionWithGarry.start"));
        onComplete(new LogAction("event.firstInteractionWithGarry.complete"));
        // Allow fighting garry only during the event
        onStart(new SetTrainerFightsAcceptance(garry, true));
        onComplete(new SetTrainerFightsAcceptance(garry, false));
    }

    public void update(float deltaTime) {
        // Check if Garry has been beaten
        if (!garry.hasHealthyPokemon()) {
            complete();
        }
    }
}
