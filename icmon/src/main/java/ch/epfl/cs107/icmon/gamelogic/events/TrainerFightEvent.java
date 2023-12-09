package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class TrainerFightEvent extends PokemonFightEvent {

    private final Trainer trainer;
    private final Pokemon trainerPokemon;

    public TrainerFightEvent(ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMonPlayer player, Trainer trainer, Pokemon playerPokemon, Pokemon trainerPokemon) {
        super(gameState, eventManager, player, playerPokemon, trainerPokemon);
        this.trainer = trainer;
        this.trainerPokemon = trainerPokemon;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isCompleted() && trainerPokemon.isKO()) {
            trainer.leaveArea();
        }
    }
}
