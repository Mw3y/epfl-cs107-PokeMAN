package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.actor.npc.Nurse;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Lab extends ICMonArea {
    public static final String TITLE = "lab";
    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new ProfOak(this, Orientation.DOWN, new DiscreteCoordinates(11, 7)));
        registerActor(new Door(Town.TITLE, new DiscreteCoordinates(20, 16), this,
                new DiscreteCoordinates(6, 1), new DiscreteCoordinates(7, 1)));
        registerActor(new Nurse(this, Orientation.DOWN, new DiscreteCoordinates(7, 7)));
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(6, 2);
    }
}
