package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendEventAction implements Action {

    private ICMonEvent event;

    public SuspendEventAction(ICMonEvent event){
        this.event = event;
    }

    @Override
    public void perform() {
        event.suspend();
    }
}
