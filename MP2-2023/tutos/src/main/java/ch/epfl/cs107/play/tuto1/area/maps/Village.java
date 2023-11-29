package ch.epfl.cs107.play.tuto1.area.maps;

import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.tuto1.actor.SimpleGhost;
import ch.epfl.cs107.play.tuto1.area.SimpleArea;

/**
 * ???
 */
public final class Village extends SimpleArea {

    /**
     * ???
     */
    @Override
    protected void createArea() {
        registerActor(new Background(this));
        registerActor(new SimpleGhost(new Vector(20, 10), "ghost.2"));
    }

    /**
     * ???
     *
     * @return ???
     */
    @Override
    public String getTitle() {
        return "zelda/Village";
    }

}