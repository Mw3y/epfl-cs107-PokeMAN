package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.SetTrainerFightsAcceptanceAction;

public class FirstInteractionWithGarryEvent extends ICMonEvent {
    private final Garry garry;

    public FirstInteractionWithGarryEvent(Garry garry) {
        assert garry != null;
        this.garry = garry;
        onStart(new LogAction("event.firstInteractionWithGarry.start"));
        onComplete(new LogAction("event.firstInteractionWithGarry.complete"));
        // Allow fighting garry only during the event
        onStart(new SetTrainerFightsAcceptanceAction(true, garry));
        onComplete(new SetTrainerFightsAcceptanceAction(false, garry));
    }

    public void update(float deltaTime) {
        // Check if Garry has been beaten
        if (!garry.hasHealthyPokemon()) {
            complete();
        }
    }
}
