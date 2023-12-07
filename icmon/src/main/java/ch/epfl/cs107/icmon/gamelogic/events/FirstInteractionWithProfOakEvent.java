package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Latios;
import ch.epfl.cs107.icmon.gamelogic.actions.GivePokemonToPlayerAction;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class FirstInteractionWithProfOakEvent extends ICMonEvent {

    private boolean hasDialogStarted = false;

    public FirstInteractionWithProfOakEvent(ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(gameState, eventManager, player);
        onComplete(new GivePokemonToPlayerAction(new Latios(gameState.getCurrentArea(), Orientation.DOWN, new DiscreteCoordinates(0, 0)), player));
    }

    @Override
    public void update(float deltaTime) {
        if (hasDialogStarted && !player.isDialogInProgress()) {
            complete();
        }
    }

    public void interactWith(ProfOak profOak, boolean isCellInteraction) {
        if (!hasDialogStarted) {
            player.openDialog("first_interaction_with_prof_oak");
            hasDialogStarted = true;
        }
    }
}
