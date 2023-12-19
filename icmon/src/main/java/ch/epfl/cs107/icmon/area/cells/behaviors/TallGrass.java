package ch.epfl.cs107.icmon.area.cells.behaviors;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.random.RandomGenerator;

/**
 * Represents a tall grass cell behavior.
 */
public class TallGrass {

    /**
     * Whether the tall grass should hijack the player or not.
     * @return true if the randomly generated number is under a certain threshold.
     */
    public static boolean hasHiddenPokemon() {
        return RandomGenerator.getInstance().nextDouble(100) <= 7.5;
    }

    /**
     * Forces the player to engage in a fight against a wild Pokémon.
     * @param player - The player
     * @param area - The area in which the Pokémon should spawn
     */
    public static void hiJackPlayer(ICMonPlayer player, Area area){
        Pokemon wildPokemon = new Bulbizarre(area, Orientation.DOWN, new DiscreteCoordinates(0,0));
        player.interactWith(wildPokemon, true);
    }
}
