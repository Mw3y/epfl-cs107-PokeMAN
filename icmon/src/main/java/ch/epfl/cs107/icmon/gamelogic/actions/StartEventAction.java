package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class StartEventAction implements Action {

    private final ICMonEvent event;

    public StartEventAction(ICMonEvent eventToStart) {
        assert eventToStart != null;
        this.event = eventToStart;
    }

    @Override
    public void perform() {
        event.start();
    }
}
