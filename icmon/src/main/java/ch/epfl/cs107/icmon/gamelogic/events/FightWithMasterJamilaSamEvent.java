package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.league.JamilaSam;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;

public class FightWithMasterJamilaSamEvent extends ICMonEvent {

    private final ICMon.ICMonGameState gameState;
    private final ICMonPlayer player;

    private JamilaSam jamilaSam;

    private boolean hasInteractionBeenRegistered;

    public FightWithMasterJamilaSamEvent(ICMon.ICMonGameState gameState, ICMonPlayer player) {
        this.gameState = gameState;
        this.player = player;

        onStart(new LogAction("event.fightWithMasterJamilaSamEvent.start"));
        onComplete(new LogAction("event.fightWithMasterJamilaSamEvent.complete"));
        // onStart(new SetTrainerFightsAcceptanceAction(true, jamilaSam));
    }

    @Override
    public void update(float deltaTime) {

        if (jamilaSam == null)
            return;

        if (!jamilaSam.hadADialogWithPlayer()) {
            // Open dialog before the fight with the player
            jamilaSam.openDialogWith(player, "welcome_to_icmon");
            jamilaSam.setFightsAcceptance(true);
        } else if (hasInteractionBeenRegistered && !player.isDialogInProgress()) {
            player.interactWith(jamilaSam, false);
            hasInteractionBeenRegistered = false;
        }

        if (!jamilaSam.hasHealthyPokemon()) {
            jamilaSam.setFightsAcceptance(false);
            // Open dialog after the fight with the player
            jamilaSam.openDialogWith(player, "welcome_to_icmon");
            complete();
        }
    }

    @Override
    public void interactWith(JamilaSam jamilaSam, boolean isCellInteraction) {
        this.jamilaSam = jamilaSam;
        hasInteractionBeenRegistered = true;
    }
}
