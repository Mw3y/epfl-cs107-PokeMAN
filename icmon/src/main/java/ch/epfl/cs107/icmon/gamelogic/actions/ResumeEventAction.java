package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class ResumeEventAction implements Action{
    private ICMonEvent event;

    public ResumeEventAction(ICMonEvent event){
        this.event = event;
    }

    @Override
    public void perform() {
        event.resume();
    }
}
