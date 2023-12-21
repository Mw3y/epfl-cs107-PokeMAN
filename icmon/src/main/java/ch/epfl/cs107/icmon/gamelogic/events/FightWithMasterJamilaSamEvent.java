package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.npc.league.JamilaSam;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.SetTrainerFightsAcceptance;

public class FightWithMasterJamilaSamEvent extends ICMonEvent {
    private JamilaSam jamilaSam;

    public FightWithMasterJamilaSamEvent(JamilaSam jamilaSam) {
        this.jamilaSam = jamilaSam;
        onStart(new LogAction("event.fightWithMasterJamilaSamEvent.start"));
        onComplete(new LogAction("event.fightWithMasterJamilaSamEvent.complete"));
        onStart(new SetTrainerFightsAcceptance(true, jamilaSam));
    }

    @Override
    public void update(float deltaTime) {
        if (!jamilaSam.hasHealthyPokemon()) {
            complete();
        }
    }
}
