package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class UnregisterEventAction implements Action {
    private final ICMon.ICMonEventManager eventManager;
    private final ICMonEvent event;

    /**
     * Constructor for UnregisterEventActon
     * @param eventManager
     * @param eventToRegister
     */
    public UnregisterEventAction(ICMon.ICMonEventManager eventManager, ICMonEvent eventToRegister) {
        assert eventManager != null;
        assert eventToRegister != null;
        this.eventManager = eventManager;
        this.event = eventToRegister;
    }

    /**
     * Unregisters a given event.
     */
    @Override
    public void perform() {
        eventManager.unregisterEvent(event);
    }
}
