package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartDialogAction;

public class IntroductionEvent extends ICMonEvent {

    private final ICMonPlayer player;

    /**
     * Constructor for IntroductionEvent
     * @param player (ICMonPlayer)
     */
    public IntroductionEvent(ICMonPlayer player) {
        assert player != null;
        this.player = player;
        onStart(new LogAction("event.introduction.start"));
        onComplete(new LogAction("event.introduction.complete"));
        onStart(new StartDialogAction("welcome_to_icmon", player));
    }

    @Override
    public void update(float deltaTime) {
        // Complete the event if no dialogs are in progress.
        if (isStarted() && !player.isDialogInProgress()) {
            complete();
        }
    }
}
