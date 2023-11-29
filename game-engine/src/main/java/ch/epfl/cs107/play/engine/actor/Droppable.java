package ch.epfl.cs107.play.engine.actor;

public interface Droppable {
    /** @return (boolean): true if this is able to interact with a drop */
	boolean canDrop();
	
    /**
     * Interaction of a dropping
     * @param draggable the object that is dropped
     */
	void receiveDropFrom(Draggable draggable);
}
