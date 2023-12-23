package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class CompleteEventAction implements Action {

    private final ICMonEvent event;

    /**
     * Constructor for CompleteEventAction
     * @param eventToComplete (ICMonEvent)
     */
    public CompleteEventAction(ICMonEvent eventToComplete) {
        assert eventToComplete != null;
        this.event = eventToComplete;
    }

    /**
     * Completes the given event.
     */
    @Override
    public void perform() {
        event.complete();
    }
}
