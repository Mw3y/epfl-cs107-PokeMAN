package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class ResumeEventAction implements Action{
    private final ICMonEvent event;

    public ResumeEventAction(ICMonEvent event){
        assert event != null;
        this.event = event;
    }

    @Override
    public void perform() {
        event.resume();
    }
}
