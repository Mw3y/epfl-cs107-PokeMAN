package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;

public class RegisterInAreaAction implements Action {
    private final ICMonArea area;
    private final AreaEntity entity;

    /**
     * Constructor for RegisterInAreaAction
     * @param area (ICMonArea)
     * @param entity (AreaEntity)
     */
    public RegisterInAreaAction(ICMonArea area, AreaEntity entity) {
        assert area != null;
        assert entity != null;
        this.area = area;
        this.entity = entity;
    }

    /**
     * Registers a given actor in a given area.
     */
    @Override
    public void perform() {
        area.registerActor(entity);
    }
}
