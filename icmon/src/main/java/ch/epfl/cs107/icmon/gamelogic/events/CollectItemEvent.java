package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.items.ICMonItem;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterInAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;

public class CollectItemEvent extends ICMonEvent {

    private ICMonPlayer player;
    private final ICMonItem item;

    public CollectItemEvent(ICMonPlayer player, ICMonItem itemToCollect) {
        this.player = player;
        this.item = itemToCollect;

        onStart(new LogAction("event.collectItem.start." + itemToCollect.toString()));
        onComplete(new LogAction("event.collectItem.complete." + itemToCollect));
    }

    @Override
    public void update(float deltaTime) {
        if (item.isCollected()) {
            complete();
        }
    }

    @Override
    public void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {
        System.out.println("interaction.with.icshopAssistant.from.collectItemEvent");
        player.openDialog("collect_item_event_interaction_with_icshopassistant");
    }
    @Override
    public void interactWith(ICBall ball, boolean isCellInteraction) {
        System.out.println("interaction.with.icball.from.collectItemEvent");
    }

}
