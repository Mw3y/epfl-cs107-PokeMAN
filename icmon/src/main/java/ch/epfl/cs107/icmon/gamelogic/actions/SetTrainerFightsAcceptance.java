package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.npc.Trainer;

public class SetTrainerFightsAcceptance implements Action {

    private final Trainer trainer;
    private final boolean fightsAcceptance;

    public  SetTrainerFightsAcceptance(Trainer trainer, boolean fightsAcceptance) {
        this.trainer = trainer;
        this.fightsAcceptance = fightsAcceptance;
    }

    @Override
    public void perform() {
        trainer.setFightsAcceptance(fightsAcceptance);
    }
}
