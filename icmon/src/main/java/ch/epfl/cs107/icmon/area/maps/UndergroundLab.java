package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

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
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(19, 4);
    }
}
