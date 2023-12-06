package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class House extends ICMonArea {

    public static final String TITLE = "house";

    @Override
    public String getTitle() {
        return TITLE;
    }

    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor()
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return null;
    }
}
