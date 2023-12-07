package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartDialogAction;

public class IntroductionEvent extends ICMonEvent {

    public IntroductionEvent(ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMonPlayer player) {
        super(gameState, eventManager, player);
        onStart(new LogAction("event.introduction.start"));
        onComplete(new LogAction("event.introduction.complete"));
        onStart(new StartDialogAction("welcome_to_icmon", player));
    }

    @Override
    public void update(float deltaTime) {
        if (isStarted() && !player.isDialogInProgress()) {
            complete();
        }
    }
}
