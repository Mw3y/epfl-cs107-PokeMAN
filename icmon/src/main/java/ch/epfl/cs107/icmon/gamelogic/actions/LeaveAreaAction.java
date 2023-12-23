package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonActor;

public class LeaveAreaAction implements Action {

    private final ICMonActor actor;

    /**
     * Constructor for LeaveAreaAction
     * @param actor (ICMonActor)
     */
    public LeaveAreaAction(ICMonActor actor) {
        assert actor != null;
        this.actor = actor;
    }

    /**
     * Makes the given actor leave the area.
     */
    @Override
    public void perform() {
        System.out.println("action.actor.leaveArea");
        actor.leaveArea();
    }
}
