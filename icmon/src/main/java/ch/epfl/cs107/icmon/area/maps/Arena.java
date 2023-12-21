package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.data.PokemonDataLoader;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Arena extends ICMonArea {

    public static final String TITLE = "arena";

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(Town.TITLE, new DiscreteCoordinates(20, 15), this,
                new DiscreteCoordinates(4, 1), new DiscreteCoordinates(5, 1)));

        registerActor(new PokemonDataLoader().load(1).toPokemon(this, Orientation.DOWN, new DiscreteCoordinates(6, 6)));
        registerActor(new PokemonDataLoader().load(381).toPokemon(this, Orientation.DOWN, new DiscreteCoordinates(4, 6)));
        registerActor(new PokemonDataLoader().load(31).toPokemon(this, Orientation.DOWN, new DiscreteCoordinates(2, 6)));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return null;
    }
}
