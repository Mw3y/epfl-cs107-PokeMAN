package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;

public class CollectItemEvent extends ICMonEvent{

    private final ICMonItem item;

    public CollectItemEvent(ICMonItem itemToCollect) {
        item = itemToCollect;
        onStart(new LogAction("CollectItemEvent started!"));
        onComplete(new LogAction("CollectItemEvent completed!"));
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            complete();
        }
    }
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction){
        System.out.println("This is an interaction between the player and ICShopAssistant based on events !");
    }
}
