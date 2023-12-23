package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendEventAction implements Action {

    private final ICMonEvent event;

    /**
     * Constructor for SuspendEventWithAction
     * @param event (ICMonEvent)
     */
    public SuspendEventAction(ICMonEvent event){
        assert event != null;
        this.event = event;
    }

    /**
     * Suspends a given event.
     */
    @Override
    public void perform() {
        event.suspend();
    }
}
