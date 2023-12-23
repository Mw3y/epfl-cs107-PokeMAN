package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonFightEvent extends ICMonEvent {

    private final ICMonFight fight;
    private final Pokemon opponentPokemon;
    private final Trainer trainer;

    /**
     * Constructor for PokemonFightEvent
     * @param gameState (ICMon.ICMonGameState)
     * @param playerPokemon (Pokemon)
     * @param opponentPokemon (Pokemon)
     * @param trainer (Trainer)
     */
    public PokemonFightEvent(ICMon.ICMonGameState gameState, Pokemon playerPokemon, Pokemon opponentPokemon, Trainer trainer) {
        assert playerPokemon != null;
        assert opponentPokemon != null;
        this.fight = new ICMonFight(gameState, playerPokemon, opponentPokemon, trainer);

        onStart(new LogAction("event.pokemonFight.start.with." + opponentPokemon));
        onComplete(new LogAction("event.pokemonFight.complete.with." + opponentPokemon));
        // No need to leave the area since the Pokémon has been registered in an empty pokeball
        onComplete(new LeaveAreaAction(opponentPokemon));
        this.opponentPokemon = opponentPokemon;
        this.trainer = trainer;
    }

    @Override
    public void update(float deltaTime) {
        if (!fight.isRunning()) {
            complete();
            // If it has to, make the trainer disappear after he lost the fight.
            if (trainer != null && !trainer.hasHealthyPokemon() && trainer.disappearsOnDefeat())
                trainer.leaveArea();
        }
    }

    @Override
    public boolean hasPauseMenu(){
        return true;
    }

    @Override
    public final PauseMenu getPauseMenu(){
        return fight;
    }

}
