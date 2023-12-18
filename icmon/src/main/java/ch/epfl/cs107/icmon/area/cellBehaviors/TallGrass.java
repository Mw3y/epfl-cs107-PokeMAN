package ch.epfl.cs107.icmon.area.cellBehaviors;

import ch.epfl.cs107.play.math.random.RandomGenerator;

public class TallGrass {

    public static boolean hasHiddenPokemon() {
        return RandomGenerator.getInstance().nextInt(100) <= 20;
    }
}
