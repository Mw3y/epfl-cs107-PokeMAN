package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.npc.Trainer;

import java.util.Arrays;
import java.util.List;

public class SetTrainerFightsAcceptanceAction implements Action {

    private final List<Trainer> trainers;
    private final boolean fightsAcceptance;

    public SetTrainerFightsAcceptanceAction(boolean fightsAcceptance, Trainer... trainers) {
        assert trainers != null;
        this.trainers = Arrays.stream(trainers).toList();
        this.fightsAcceptance = fightsAcceptance;
    }

    @Override
    public void perform() {
        for (Trainer trainers : trainers) {
            trainers.setFightsAcceptance(fightsAcceptance);
        }
    }
}