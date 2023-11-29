package ch.epfl.cs107.play.areagame.actor;

import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.engine.DragHelper;
import ch.epfl.cs107.play.engine.actor.Draggable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Mouse;

public abstract class DraggableAreaEntity extends AreaEntity implements Draggable {
	private final Mouse mouse;
	private boolean isDragging;
	private DiscreteCoordinates initialPosition;
	private Vector relativeMousePosition;
	private boolean wantsDropInteraction;
	
	public DraggableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		mouse = area.getMouse();
	}

    @Override
    public void update(float deltaTime) {
    	super.update(deltaTime);
    	
    	if(wantsDropInteraction) { // the drop failed since we do not get any acknowledgement
    		resetDrag();
    		setCurrentPosition(initialPosition.toVector());
    	}
    	
    	if(canDrag()) {
    		isDragging = true;
    		initialPosition = getCurrentMainCellCoordinates();
    		relativeMousePosition = getPosition().sub(getOwnerArea().getRelativeMousePosition());
    		DragHelper.setCurrentDraggedElement(this);
    	}
    	if(isDragging) {
    		if(mouse.getLeftButton().isReleased()) {
    			wantsDropInteraction = true;
    		}else {
        		setCurrentPosition(getOwnerArea().getRelativeMousePosition().add(relativeMousePosition));
    		}
    	}
    }
    
    private void resetDrag() {
		isDragging = false;
		wantsDropInteraction = false;
    	DragHelper.setCurrentDraggedElement(null);
    }
    
    public DiscreteCoordinates getInitialPosition() {
    	return initialPosition;
    }
    
    @Override
    public boolean canDrag() {
    	return mouse.getLeftButton().isPressed() && isMouseOver();
    }
    
    @Override
    public boolean isDragging() {
    	return isDragging;
    }
    
    @Override
    public boolean wantsDropInteraction() {
    	return wantsDropInteraction;
    }
    
    @Override
    public void acknowledgeDrop() {
    	resetDrag();
    }
}
