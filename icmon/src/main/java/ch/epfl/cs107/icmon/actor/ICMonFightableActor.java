package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public interface ICMonFightableActor {

    /**
     * Default fight method
     * @param game (ICMon.ICMonGameState)
     * @param playerPokemon (Pokemon)
     */
    void fight(ICMon.ICMonGameState game, Pokemon playerPokemon);

}
