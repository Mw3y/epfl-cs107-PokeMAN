package ch.epfl.cs107.play.areagame.actor;

import java.util.List;

import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;


/**
 * Area Entities are assigned to at least one Area Cell which make them Interactable
 */
public abstract class AreaEntity extends Entity implements Interactable {

    /// AreaEntity are disposed inside an Area
    private Area ownerArea;
    /// Orientation in the Area
    private Orientation orientation;
    /// Coordinate of the main Cell linked to the entity
    private DiscreteCoordinates currentMainCellCoordinates;

    /**
     * Default AreaEntity constructor
     * @param area (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public AreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {

        super(position.toVector());

        if(area == null){
            throw new NullPointerException();
        }

        this.ownerArea = area;
        this.orientation = orientation;
        this.currentMainCellCoordinates = position;
    }

    /**
     * Getter for the owner area
     * @return (Area)
     */
    protected Area getOwnerArea() {
        return ownerArea;
    }

    /**
     * Set the owner area with new value
     * @param newArea (Area): the new value. Not null
     */
    protected void setOwnerArea(Area newArea) {
        this.ownerArea = newArea;
    }

    /**
     * Getter for the orientation
     * @return (Orientation): current orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Orientate the AreaEntity to a new orientation
     * @param orientation (Orientation): The new orientation. Not null
     * @return (boolean): if the orientation change happens, by default always true
     */
    protected boolean orientate(Orientation orientation) {
        this.orientation = orientation;
        return true;
    }

    /**
     * Getter for the coordinates of the main cell occupied by the AreaEntity
     * @return (DiscreteCoordinates)
     */
    protected DiscreteCoordinates getCurrentMainCellCoordinates(){
        return currentMainCellCoordinates;
    }
    
    /**
     * Tell if the mouse is over any of the currentCells of the entity
     * @return (boolean)
     */
    protected boolean isMouseOver() {
    	List<DiscreteCoordinates>cells = getCurrentCells();
    	DiscreteCoordinates mouseCoordinate = ownerArea.getRelativeMouseCoordinates();
    	for(DiscreteCoordinates cell : cells) {
    		if(cell.equals(mouseCoordinate)) {
    			return true;
    		}
    	}
    	return false;
    }

    /// AreaEntity extends Entity

    @Override
    protected void setCurrentPosition(Vector v){
        // When updating the current position, also check if we need to update the main cell coordinates
        if(DiscreteCoordinates.isCoordinates(v)){
            this.currentMainCellCoordinates = new DiscreteCoordinates(Math.round(v.x), Math.round(v.y));
            v = v.round();
        }
        super.setCurrentPosition(v);
    }

    @Override
    public void onLeaving(List<DiscreteCoordinates> coordinates) {}

    @Override
    public void onEntering(List<DiscreteCoordinates> coordinates) {}
}
