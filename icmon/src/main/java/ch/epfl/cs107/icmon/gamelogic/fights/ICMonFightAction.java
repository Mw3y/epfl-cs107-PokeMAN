package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public interface ICMonFightAction {

    String name();

    boolean doAction(Pokemon target);

}
