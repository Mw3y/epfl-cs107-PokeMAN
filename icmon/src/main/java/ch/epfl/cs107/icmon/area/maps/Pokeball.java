package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public final class Pokeball extends ICMonArea {

    public static final String TITLE = "pokeball";

    public Pokeball() {}

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(0, 0);
    }

    @Override
    protected void createArea() {}

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public String getTitle() {
        return Pokeball.TITLE;
    }
}
