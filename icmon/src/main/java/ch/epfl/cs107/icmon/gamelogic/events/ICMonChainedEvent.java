package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.CompleteEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;

public class ICMonChainedEvent extends ICMonEvent {

    public ICMonChainedEvent(ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager, ICMonPlayer player, ICMonEvent... chain) {
        super(gameState, eventManager, player);

        for (int i = 0; i < chain.length; ++i) {
            // Automatically register and unregister the event
            chain[i].onStart(new RegisterEventAction(eventManager, chain[i]));
            chain[i].onComplete(new UnregisterEventAction(eventManager, chain[i]));

            if (i < chain.length - 1) {
                chain[i].onComplete(new StartEventAction(chain[i + 1]));
            }
            // When the last event of the chain completes, this event needs to complete too
            else chain[i].onComplete(new CompleteEventAction(this));
        }

        chain[0].start();
    }

    @Override
    public void update(float deltaTime) {
    }
}
