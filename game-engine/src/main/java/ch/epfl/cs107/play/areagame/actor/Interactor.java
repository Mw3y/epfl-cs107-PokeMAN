package ch.epfl.cs107.play.areagame.actor;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.List;


/**
 * Represents Interactor object (i.e. it can interact with some Interactable)
 * @see Interactable
 * This interface makes sense only in the "Area Context" with Actor contained into Area Cell
 */
public interface Interactor {

    /**
     * Get this Interactor's current occupying cells coordinates
     * @return (List of DiscreteCoordinates). May be empty but not null
     */
    List<DiscreteCoordinates> getCurrentCells();


    /**
     * Get this Interactor's current field of view cells coordinates
     * @return (List of DiscreteCoordinates). May be empty but not null
     */
    List<DiscreteCoordinates> getFieldOfViewCells();


    /**@return (boolean): true if this require cell interaction */
    boolean wantsCellInteraction();
    /**@return (boolean): true if this require view interaction */
    boolean wantsViewInteraction();

    /**
     * Do this Interactor interact with the given Interactable
     * The interaction is implemented on the interactor side !
     * @param other (Interactable). Not null
     * @param isCellInteraction True if this is a cell interaction
     */
    void interactWith(Interactable other, boolean isCellInteraction);


    /// Interactable Listener
    interface Listener {

        /**
         * Do the interactor interact with Interactable (i.e. Interactable sharing the same cell)
         * @param interactor (Interactor). Not null
         */
        void cellInteractionOf(Interactor interactor);

        /**
         * Do the interactor interact with Interactable (i.e. Interactable in its field of view cells)
         * @param interactor (Interactor). Not null
         */
        void viewInteractionOf(Interactor interactor);
    }
}
