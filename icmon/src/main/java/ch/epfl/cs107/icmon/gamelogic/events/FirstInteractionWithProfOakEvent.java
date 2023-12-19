package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Latios;
import ch.epfl.cs107.icmon.area.maps.Pokeball;
import ch.epfl.cs107.icmon.gamelogic.actions.GivePokemonToPlayerAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class FirstInteractionWithProfOakEvent extends ICMonEvent {

    private boolean hasDialogStarted = false;
    private ICMonPlayer player;

    public FirstInteractionWithProfOakEvent(ICMonPlayer player) {
        this.player = player;
        onStart(new LogAction("event.firstInteractionWithProfOak.start"));
        onComplete(new LogAction("event.firstInteractionWithProfOak.complete"));
        onComplete(new GivePokemonToPlayerAction(new Latios(new Pokeball(), Orientation.DOWN, new DiscreteCoordinates(0, 0)), player));
    }

    @Override
    public void update(float deltaTime) {
        if (hasDialogStarted && !player.isDialogInProgress()) {
            complete();
        }
    }

    @Override
    public void interactWith(ProfOak profOak, boolean isCellInteraction) {
        System.out.println("interaction.with.profOak.from.firstInteractionWithProfOakEvent");
        if (!hasDialogStarted) {
            player.openDialog("first_interaction_with_prof_oak");
            hasDialogStarted = true;
        }
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        player.openDialog("first_interaction_with_oak_event_icshopassistant");
    }
}
