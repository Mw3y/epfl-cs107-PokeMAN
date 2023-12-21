package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.npc.league.AnnaLachowska;
import ch.epfl.cs107.icmon.actor.npc.league.FredericBlanc;
import ch.epfl.cs107.icmon.actor.npc.league.NicolasBoumal;
import ch.epfl.cs107.icmon.actor.npc.league.TanjaKaser;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;

import java.util.ArrayList;
import java.util.List;

public class FightWithEliteFourEvent extends ICMonEvent {

    private final ICMon.ICMonGameState gameState;
    private final ICMonPlayer player;

    private List<Trainer> trainers = new ArrayList<>();

    private List<String> hasWonAgainst = new ArrayList<>();

    private Trainer currentTrainer;

    /**
     * Constructor for FightWithEliteFourEvent;
     * @param gameState (ICMon.ICMonGameState)
     * @param player (ICMonPlayer)
     */

    public FightWithEliteFourEvent(ICMon.ICMonGameState gameState, ICMonPlayer player) {
        this.gameState = gameState;
        this.player = player;

        onStart(new LogAction("event.fightWithEliteFour.start"));
        onComplete(new LogAction("event.fightWithEliteFour.complete"));
    }

    /**
     * Fills the trainers list with trainer.
     * @param trainer
     * @return
     */
    private boolean registerTrainer(Trainer trainer) {
        currentTrainer = trainer;
        if (!trainers.contains(trainer)) {
            return trainers.add(trainer);
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        for (Trainer trainer : trainers) {
            if (!trainer.hasHealthyPokemon() && !hasWonAgainst.contains(trainer.name())) {
                // Open dialog after the win of the player
                trainer.openDialogWith(player, "end_fight_"+trainer.name());
                hasWonAgainst.add(trainer.name());
            }
        }

        if (currentTrainer != null) {
            if (!currentTrainer.hadADialogWithPlayer())
                // Open dialog before the fight with the player
                currentTrainer.openDialogWith(player, "start_fight_"+ currentTrainer.name());
            else if (!player.isDialogInProgress()) {
                currentTrainer.setFightsAcceptance(true);
                if (!hasWonAgainst.contains(currentTrainer))
                    player.interactWith(currentTrainer, false);
                currentTrainer = null;
            }
        }

        if (hasWonAgainst.size() == 4) {
            for (Trainer trainer : trainers) {
                trainer.setFightsAcceptance(false);
            }
            complete();
        }
    }

    @Override
    public void interactWith(AnnaLachowska annaLachowska, boolean isCellInteraction) {
        registerTrainer(annaLachowska);
    }

    @Override
    public void interactWith(NicolasBoumal nicolasBoumal, boolean isCellInteraction) {
        registerTrainer(nicolasBoumal);
    }

    @Override
    public void interactWith(FredericBlanc fredericBlanc, boolean isCellInteraction) {
        registerTrainer(fredericBlanc);
    }

    @Override
    public void interactWith(TanjaKaser tanjaKaser, boolean isCellInteraction) {
        registerTrainer(tanjaKaser);
    }
}
