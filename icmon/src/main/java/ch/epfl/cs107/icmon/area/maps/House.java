package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class House extends ICMonArea {

    public static final String TITLE = "house";

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(2, 2);
    }

    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(Town.TITLE, new DiscreteCoordinates(7, 26), this,
                new DiscreteCoordinates(3, 1), new DiscreteCoordinates(4, 1)));
    }
}
