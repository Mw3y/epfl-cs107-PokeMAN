package ch.epfl.cs107.play.tuto1.area.maps;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.tuto1.area.SimpleArea;

/**
 * ???
 */
public final class Ferme extends SimpleArea {

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
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