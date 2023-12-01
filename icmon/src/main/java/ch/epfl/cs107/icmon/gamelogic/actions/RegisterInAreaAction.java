package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;

public class RegisterInAreaAction implements Action {
    private ICMonArea area;
    private ICBall ball;

    public RegisterInAreaAction(ICMonArea area, ICBall actorToRegister) {
        this.area = area;
        this.ball = actorToRegister;
    }

    @Override
    public void perform() {
        area.registerActor(ball);
    }
}
