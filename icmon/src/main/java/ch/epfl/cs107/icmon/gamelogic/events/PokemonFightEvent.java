package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
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

    public PokemonFightEvent(ICMon.ICMonGameState gameState, Pokemon playerPokemon, Pokemon pokemon) {
        assert playerPokemon != null;
        assert pokemon != null;
        this.fight = new ICMonFight(gameState, playerPokemon, pokemon);

        onStart(new LogAction("event.pokemonFight.start.with." + pokemon));
        onComplete(new LogAction("event.pokemonFight.complete.with." + pokemon));
        // No need to leave the area since the Pokémon has been registered in an empty pokeball
        // onComplete(new LeaveAreaAction(pokemon));
    }

    @Override
    public void update(float deltaTime) {
        if (!fight.isRunning()) {
            complete();
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
