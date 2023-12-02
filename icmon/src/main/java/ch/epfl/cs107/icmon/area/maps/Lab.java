package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Lab extends ICMonArea {
    @Override
    public String getTitle() {
        return "Lab";
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door("Town", new DiscreteCoordinates(15, 23), this,
                new DiscreteCoordinates(6, 1), new DiscreteCoordinates(7, 1)));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(6, 2);
    }
}
