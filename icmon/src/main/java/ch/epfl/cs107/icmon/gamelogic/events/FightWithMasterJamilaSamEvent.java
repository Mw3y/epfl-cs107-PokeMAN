package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.league.JamilaSam;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.SetTrainerFightsAcceptanceAction;

public class FightWithMasterJamilaSamEvent extends ICMonEvent {

    private final ICMon.ICMonGameState gameState;
    private final ICMonPlayer player;

    private boolean toggledFights;

    private JamilaSam jamilaSam;

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

        if (!toggledFights) {
            jamilaSam.setFightsAcceptance(true);
            toggledFights = true;
        }

        if (!jamilaSam.hasHealthyPokemon()) {
          complete();
        }
    }

    @Override
    public void interactWith(JamilaSam jamilaSam, boolean isCellInteraction) {
        this.jamilaSam = jamilaSam;
    }
}
