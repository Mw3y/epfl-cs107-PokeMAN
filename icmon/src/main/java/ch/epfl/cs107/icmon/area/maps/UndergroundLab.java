package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.actor.npc.league.AnnaLachowska;
import ch.epfl.cs107.icmon.actor.npc.league.TanjaKaser;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class UndergroundLab extends ICMonArea {

    public static final String TITLE = "underground_lab";

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(Town.TITLE, new DiscreteCoordinates(21,6), this, new DiscreteCoordinates(24, 11)));
        registerActor(new TanjaKaser(this, Orientation.DOWN, new DiscreteCoordinates(5, 25)));
        registerActor(new AnnaLachowska(this, Orientation.DOWN, new DiscreteCoordinates(8, 30)));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(19, 4);
    }
}
