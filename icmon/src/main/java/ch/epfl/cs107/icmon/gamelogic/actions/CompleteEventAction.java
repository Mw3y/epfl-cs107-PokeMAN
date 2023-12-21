package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class CompleteEventAction implements Action {

    private final ICMonEvent event;

    public CompleteEventAction(ICMonEvent eventToComplete) {
        assert eventToComplete != null;
        this.event = eventToComplete;
    }

    @Override
    public void perform() {
        event.complete();
    }
}
