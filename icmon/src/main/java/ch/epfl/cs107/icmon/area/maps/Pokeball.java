package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public final class Pokeball extends ICMonArea {

    public static final String TITLE = "pokeball";

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {}

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    /**
     * ???
     *
     * @return ???
     */
    @Override
    public String getTitle() {
        return Pokeball.TITLE;
    }
}
