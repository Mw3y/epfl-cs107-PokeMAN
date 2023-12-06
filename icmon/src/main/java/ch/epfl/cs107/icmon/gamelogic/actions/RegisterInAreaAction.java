package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;

public class RegisterInAreaAction implements Action {
    private final ICMonArea area;
    private final AreaEntity entity;

    public RegisterInAreaAction(ICMonArea area, AreaEntity entity) {
        this.area = area;
        this.entity = entity;
    }

    @Override
    public void perform() {
        area.registerActor(entity);
    }
}
