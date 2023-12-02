package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;

public class CollectItemEvent extends ICMonEvent {

    private final ICMonItem item;

    public CollectItemEvent(ICMon.ICMonEventManager eventManager, ICMonItem itemToCollect) {
        item = itemToCollect;
        onStart(new LogAction("CollectItemEvent started!"));
        onComplete(new LogAction("CollectItemEvent completed!"));
        // TODO: Don't repeat this
        onStart(new RegisterEventAction(eventManager, this));
        onComplete(new UnregisterEventAction(eventManager, this));
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            complete();
        }
    }

    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("This is an interaction between the player and ICShopAssistant based on events !");
    }

    public void interactWith(ICBall ball, boolean isCellInteraction) {
        System.out.println("This is an interaction between the player and ICBall based on events !");
    }
}
