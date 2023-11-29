package ch.epfl.cs107.play.engine.actor;

public interface Draggable {
    /** @return (boolean): true if this is able to be drag */
	boolean canDrag();
	
    /** @return (boolean): true if this is dragging */
	boolean isDragging();

    /** @return (boolean): true if this wants to be dropped */
    boolean wantsDropInteraction();

    /** acknowledge that a drop do happened */
    void acknowledgeDrop();
}
