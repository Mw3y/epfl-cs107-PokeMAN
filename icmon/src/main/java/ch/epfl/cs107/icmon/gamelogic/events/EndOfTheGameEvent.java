package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;

public class EndOfTheGameEvent extends ICMonEvent {
    public EndOfTheGameEvent(ICMon.ICMonEventManager eventManager) {
        onStart(new LogAction("EndOfTheGameEvent started!"));
        onComplete(new LogAction("EndOfTheGameEvent completed!"));
        // TODO: Don't repeat this
        onStart(new RegisterEventAction(eventManager, this));
        onComplete(new UnregisterEventAction(eventManager, this));
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("I heard that you were able to implement this step successfully. Congrats!");
    }
}
