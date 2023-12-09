package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public final class Town extends ICMonArea {
    public static final String TITLE = "town";

    @Override
    public DiscreteCoordinates getPlayerSpawnPosition() {
        return new DiscreteCoordinates(15, 15);
    }

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
        registerActor(new Door(Lab.TITLE, new DiscreteCoordinates(6, 2), this, new DiscreteCoordinates(15, 24)));
        registerActor(new Door(Arena.TITLE, new DiscreteCoordinates(4, 2), this, new DiscreteCoordinates(20, 16)));
        registerActor(new Door(House.TITLE, new DiscreteCoordinates(3, 2), this, new DiscreteCoordinates(7,27)));
        registerActor(new Door(Shop.TITLE, new DiscreteCoordinates(3,2), this, new DiscreteCoordinates(25, 20)));
    }

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
        return Town.TITLE;
    }


}
