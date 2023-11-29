package ch.epfl.cs107.play.areagame.handler;

import ch.epfl.cs107.play.areagame.actor.Interactable;


public interface AreaInteractionVisitor {

    /// Add Interaction method with all non Abstract Interactable

    /**
     * Default interaction between something and an interactable
     * Notice: if this method is used, then you probably forget to cast the AreaInteractionVisitor into its correct child
     * @param other (Interactable): interactable to interact with, not null
     */
    default void interactWith(Interactable other, boolean isCellInteraction){
        System.out.println("Specific Interaction is not yet implemented or you simply forget a cast");
    }
}
