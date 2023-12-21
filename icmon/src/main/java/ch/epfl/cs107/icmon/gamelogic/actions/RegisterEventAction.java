package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class RegisterEventAction implements Action {
    private final ICMon.ICMonEventManager eventManager;
    private final ICMonEvent event;

    /**
     * Constructor for RegisterEventAction
     * @param eventManager (ICMon.ICMonEventManager)
     * @param eventToRegister (ICMonEvent)
     */
    public RegisterEventAction(ICMon.ICMonEventManager eventManager, ICMonEvent eventToRegister) {
        assert eventManager != null;
        assert eventToRegister != null;
        this.eventManager = eventManager;
        this.event = eventToRegister;
    }

    /**
     * Registers a given event.
     */
    @Override
    public void perform() {
        eventManager.registerEvent(event);
    }
}
