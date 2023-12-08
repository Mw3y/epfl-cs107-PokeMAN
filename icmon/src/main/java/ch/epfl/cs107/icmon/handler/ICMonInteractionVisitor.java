package ch.epfl.cs107.icmon.handler;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.misc.Door;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.npc.ICShopAssistant;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.ProfOak;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonBehavior;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;

public interface ICMonInteractionVisitor extends AreaInteractionVisitor {
    /// Add Interaction method with all non Abstract Interactable

    /**
     * Default interaction between something and an interactable
     * Notice: if this method is used, then you probably forget to cast the AreaInteractionVisitor into its correct child
     *
     * @param other (Interactable): interactable to interact with, not null
     */
    default void interactWith(Interactable other, boolean isCellInteraction) {}

    default void interactWith(ICMonBehavior.ICMonCell cell, boolean isCellInteraction) {}

    default void interactWith(ICMonPlayer player, boolean isCellInteraction) {}

    default void interactWith(ICBall ball, boolean isCellInteraction) {}

    default void interactWith(ICShopAssistant assistant, boolean isCellInteraction) {}

    default void interactWith(Door door, boolean isCellInteraction) {}

    default void interactWith(Pokemon pokemon, boolean isCellInteraction) {}

    default void interactWith(ProfOak profOak, boolean isCellInteraction) {}

    default void interactWith(Garry garry, boolean isCellInteraction) {}

}
