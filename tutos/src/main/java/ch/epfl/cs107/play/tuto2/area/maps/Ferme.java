package ch.epfl.cs107.play.tuto2.area.maps;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.tuto2.area.Tuto2Area;

/**
 * ???
 */
public final class Ferme extends Tuto2Area {

    /**
     * ???
     * @return ???
     */
    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(2, 10);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "zelda/Ferme";
    }

}