package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * An action that can be executed during a Pokémon fight.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public interface ICMonFightAction {

    /**
     * Gets the type of the fight action.
     * @return the type of the action.
     */
    // TODO: Document
    PokemonMoveType type();

    /**
     * Gets the name of the fight action.
     * @return the name of the action.
     */
    String name();

    /**
     * Executes the fight action.
     * @param target - The Pokémon upon which the action is to be applied.
     * @return whether the action was successful or not.
     */
    // TODO: Pass other pokemon
    boolean doAction(Pokemon target, Pokemon pokemon);
}
