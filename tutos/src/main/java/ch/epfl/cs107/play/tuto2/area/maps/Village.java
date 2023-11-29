package ch.epfl.cs107.play.tuto2.area.maps;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.tuto2.actor.SimpleGhost;
import ch.epfl.cs107.play.tuto2.area.Tuto2Area;

/**
 * ???
 */
public final class Village extends Tuto2Area {

    /**
     * ???
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(5, 15);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new SimpleGhost(new Vector(20, 10), "ghost.2"));
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "zelda/Village";
    }

}