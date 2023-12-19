package ch.epfl.cs107.icmon.area.cellBehaviors;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Nidoqueen;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Pokeball;
import ch.epfl.cs107.icmon.gamelogic.messages.StartFightMessage;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.Game;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.random.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class TallGrass {

    public static boolean hasHiddenPokemon() {
        return RandomGenerator.getInstance().nextInt(100) <= 7.5;
    }

    public static void hiJackPlayer(ICMonPlayer player, Area area){
        Pokemon wildPokemon = new Bulbizarre(area, Orientation.DOWN, new DiscreteCoordinates(0,0));
        player.interactWith(wildPokemon, true);
    }
}
