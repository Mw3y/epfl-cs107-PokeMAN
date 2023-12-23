package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class StartEventAction implements Action {

    private final ICMonEvent event;

    /**
     * Constructor for StartEventAction
     * @param eventToStart (ICMonEvent)
     */
    public StartEventAction(ICMonEvent eventToStart) {
        assert eventToStart != null;
        this.event = eventToStart;
    }

    /**
     * Starts a given event.
     */
    @Override
    public void perform() {
        event.start();
    }
}
