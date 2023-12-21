package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;

public class LeaveAreaAction implements Action {

    private final ICMonActor actor;

    public LeaveAreaAction(ICMonActor actor) {
        assert actor != null;
        this.actor = actor;
    }

    @Override
    public void perform() {
        System.out.println("action.player.leaveArea");
        actor.leaveArea();
    }
}
