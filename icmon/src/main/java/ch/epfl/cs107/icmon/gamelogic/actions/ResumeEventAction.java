package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class ResumeEventAction implements Action{
    private final ICMonEvent event;

    /**
     * Constructor for ResumeEventAction
     * @param event (ICMonEvent)
     */
    public ResumeEventAction(ICMonEvent event){
        assert event != null;
        this.event = event;
    }

    /**
     * Resumes a given event.
     */
    @Override
    public void perform() {
        event.resume();
    }
}
