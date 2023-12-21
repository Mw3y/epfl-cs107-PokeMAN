package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.npc.league.AnnaLachowska;
import ch.epfl.cs107.icmon.actor.npc.league.FredericBlanc;
import ch.epfl.cs107.icmon.actor.npc.league.NicolasBoumal;
import ch.epfl.cs107.icmon.actor.npc.league.TanjaKaser;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.SetTrainerFightsAcceptance;

public class FightWithEliteFourEvent extends ICMonEvent {
    private AnnaLachowska annaLachowska;
    private NicolasBoumal nicolasBoumal;
    private TanjaKaser tanjaKaser;
    private FredericBlanc fredericBlanc;

    public FightWithEliteFourEvent(AnnaLachowska annaLachowska, NicolasBoumal nicolasBoumal, TanjaKaser tanjaKaser, FredericBlanc fredericBlanc) {

        onStart(new LogAction("event.fightWithEliteFour.start"));
        onComplete(new LogAction("event.fightWithEliteFour.complete"));
        onStart(new SetTrainerFightsAcceptance(true, annaLachowska, nicolasBoumal, fredericBlanc, tanjaKaser));
    }

    @Override
    public void update(float deltaTime) {

    }
}
