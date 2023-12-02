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
        super(eventManager);
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
        System.out.println("interaction.with.icshopAssistant.from.collectItemEvent");
        getEventManager().getPlayer().openDialog("collect_item_event_interaction_with_icshopassistant");
    }

    public void interactWith(ICBall ball, boolean isCellInteraction) {
        System.out.println("interaction.with.icball.from.collectItemEvent");
    }
}
