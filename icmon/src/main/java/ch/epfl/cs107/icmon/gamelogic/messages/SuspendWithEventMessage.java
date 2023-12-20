package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.PauseGameAction;
import ch.epfl.cs107.icmon.gamelogic.actions.ResumeGameAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;

public class SuspendWithEventMessage implements GamePlayMessage {

    private final ICMonEvent event;

    public SuspendWithEventMessage(ICMonEvent event) {
        assert event != null;
        this.event = event;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager) {
        assert player != null;
        assert gameState != null;
        assert eventManager != null;
        System.out.println("message.game.suspendWithEvent");
        if (event.hasPauseMenu()) {
            event.onStart(new PauseGameAction(gameState, event.getPauseMenu()));
            event.onComplete(new ResumeGameAction(gameState));
        }

        eventManager.registerSuspendEventActions(event);
        eventManager.registerResumeEventActions(event);
        event.start();
    }
}
