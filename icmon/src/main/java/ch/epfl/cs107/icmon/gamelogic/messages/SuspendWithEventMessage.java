package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.PauseGameAction;
import ch.epfl.cs107.icmon.gamelogic.actions.ResumeGameAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendWithEventMessage implements GamePlayMessage {

    private final ICMonEvent event;

    public SuspendWithEventMessage(ICMonEvent event) {
        this.event = event;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState game, ICMon.ICMonEventManager eventManager) {
        if (event.hasPauseMenu()) {
            event.onStart(new PauseGameAction(game, event.getPauseMenu()));
            event.onComplete(new ResumeGameAction(game));
        }

        eventManager.registerSuspendEventActions(event);
        eventManager.registerResumeEventActions(event);
        event.start();
    }
}
