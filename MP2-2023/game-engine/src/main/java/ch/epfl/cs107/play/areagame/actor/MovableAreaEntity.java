package ch.epfl.cs107.play.areagame.actor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;

/**
 * MovableAreaEntity represent AreaEntity which can move on the grid
 */
public abstract class MovableAreaEntity extends AreaEntity {

    /// Indicate if a displacement occurs right now
    private boolean displacementOccurs;
    /// Indicate how many frames the current move is supposed to take
    private int framesForCurrentMove;
    /// Indicate how many remaining frames the current move has
    private int remainingFramesForCurrentMove;

    // The cells the entity left
    private List<DiscreteCoordinates> leftCells;
    // The cells the entity entered
    private List<DiscreteCoordinates> enteredCells;

    private Vector targetPosition;
    private Vector originPosition;
    
    /**
     * Default MovableAreaEntity constructor
     * @param area (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     */
    public MovableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        resetMotion();
    }

    /**
     * Initialize or reset (if some) the current motion information
     */
    protected void resetMotion(){
        this.displacementOccurs = false;
        this.framesForCurrentMove = 0;
        this.remainingFramesForCurrentMove = 0;
    }

    /**
     * Final move method
     * If no displacement occurs or if the displacement just ends now,
     * start movement of one Cell in the current Orientation direction
     * Note the movement is possible only if this MovableAreaEntity can:
     * - leave the cells this motion implies to leave
     * - enter the cells this motion implies to enter
     * @param frameForMove (int): the frame. This value will be cropped to 1 if smaller
     * @return (boolean): indicate if the move is initiated
     */
    protected final boolean move(int frameForMove){
    	return move(frameForMove, 0);
    }
    
    /**
     * Final move method
     * If no displacement occurs or if the displacement just ends now,
     * start movement of one Cell in the current Orientation direction
     * Note the movement is possible only if this MovableAreaEntity can:
     * - leave the cells this motion implies to leave
     * - enter the cells this motion implies to enter
     * @param frameForMove (int): the frame. This value will be cropped to 1 if smaller
     * @param startingFrame (int): start the movement directly from this frame
     * @return (boolean): indicate if the move is initiated
     */
    protected final boolean move(int frameForMove, int startingFrame){
    	if(!displacementOccurs || isTargetReached() ) {

        	List<DiscreteCoordinates> leavingCells = getLeavingCells();
        	List<DiscreteCoordinates> enteringCells = getEnteringCells();

            if(getOwnerArea().enterAreaCells(this, enteringCells) && getOwnerArea().leaveAreaCells(this, leavingCells)){

            	leftCells = leavingCells;
            	enteredCells = enteringCells;
            	
                displacementOccurs = true;
                this.framesForCurrentMove = Math.max(1, frameForMove);
                startingFrame = Math.min(startingFrame, frameForMove);
                remainingFramesForCurrentMove = framesForCurrentMove - startingFrame;
                
                originPosition = getPosition();
                targetPosition = getPosition().add(getOrientation().toVector());
                
                increasePositionOf(startingFrame);
                
                return true;
            }
        }
        return false;
    }
    /**
     * Change the unit position to the one specified
     * @param newPosition new unit's position
     * @return true if the move was successful, false otherwise
     */
    public boolean changePosition(DiscreteCoordinates newPosition) {
        if (newPosition.equals(getCurrentMainCellCoordinates()))
            return true;

        if (!getOwnerArea().canEnterAreaCells(this, List.of(newPosition)))
            return false;


        getOwnerArea().leaveAreaCells(this, getCurrentCells());
        setCurrentPosition(newPosition.toVector());
        getOwnerArea().enterAreaCells(this, getCurrentCells());

        return true;
    }

    
    /**
     * Final abortCurrentMove method
     * If a displacement occurs and if the displacement is not end,
     * abort the current move, returning to the previous cell
     * Note the abort is possible only if this MovableAreaEntity can:
     * - return to the cells it left 
     * - leave the cells it entered
     * @return (boolean): indicate if the abort is initiated
     */
    protected final boolean abortCurrentMove(){
        if(displacementOccurs && !isTargetReached() && leftCells != null && enteredCells != null) {
            if(getOwnerArea().enterAreaCells(this, leftCells) && getOwnerArea().leaveAreaCells(this, enteredCells)){
                
                remainingFramesForCurrentMove = framesForCurrentMove - remainingFramesForCurrentMove;
                
                Vector tempPos = originPosition;
                originPosition = targetPosition;
                targetPosition = tempPos;
                
				List<DiscreteCoordinates> tempCells = leftCells;
				leftCells = enteredCells;
				enteredCells = tempCells;
				
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the current cells after the move
     * by default we jump each current cell by one cell in the orientation vector
     * @return (List): the cells after the move
     */
    protected List<DiscreteCoordinates> getNextCurrentCells() {
    	List<DiscreteCoordinates> nextCells = new ArrayList<>();
    	for(DiscreteCoordinates coord : getCurrentCells()) {
    		nextCells.add(coord.jump(getOrientation().toVector()));
    	}
    	return nextCells;
    }
    
    /** @return (List of DiscreteCoordinates): the cells a movement will implies to leave. May be empty but not null */
    private List<DiscreteCoordinates> getLeavingCells(){
    	Set<DiscreteCoordinates> leavingCells = new HashSet<>(getCurrentCells());
    	List<DiscreteCoordinates> nextCells = new ArrayList<>();
    	for(DiscreteCoordinates coord : getCurrentCells()) {
    		nextCells.add(coord.jump(getOrientation().toVector()));
    	}

    	leavingCells.removeAll(getNextCurrentCells());
    	
    	return new ArrayList<>(leavingCells);
    }

    /** @return (List of DiscreteCoordinates): the cells a movement will implies to enter. May be empty but not null*/
    private List<DiscreteCoordinates> getEnteringCells(){
    	Set<DiscreteCoordinates> enteringCells = new HashSet<>(getNextCurrentCells());
    	
    	enteringCells.removeAll(getCurrentCells());
    	
    	return new ArrayList<>(enteringCells);
    }

    /** @return (List of DiscreteCoordinates): the cells previous movement entered */
    public List<DiscreteCoordinates> getEnteredCells(){
    	return enteredCells;
    }

    /** @return (List of DiscreteCoordinates): the cells previous movement left */
    public List<DiscreteCoordinates> getLeftCells(){
    	return leftCells;
    }


    /**
     * Indicate if a displacement is occurring
     * @return (boolean)
     */
    protected boolean isDisplacementOccurs(){
        return displacementOccurs;
    }

    /**@return (boolean): true when the target cell is just reaching now*/
    protected boolean isTargetReached(){
        return remainingFramesForCurrentMove == 0;
    }
    
    /**
     * Increase the position of a certain amount of frame
     * @param frame
     */
    private void increasePositionOf(int frame) {
        setCurrentPosition(getPosition().add(getOrientation().toVector().mul(frame / (float)framesForCurrentMove)));
    }

    /// MovableAreaEntity extends AreaEntity
    @Override
    protected boolean orientate(Orientation orientation) {
        // Allow reorientation only if no displacement is occurring or if abort current move (opposite orientation)
    	if(getOrientation().opposite().equals(orientation)) {
    		if(abortCurrentMove())
    			return super.orientate(orientation);
    	}
        return !displacementOccurs && super.orientate(orientation);
    }


    /// MovableAreaEntity implements Actor

    @Override
    public void update(float deltaTime) {
        if (displacementOccurs) {
            if (!isTargetReached()) {
            	increasePositionOf(1);
            } else {
                setCurrentPosition(targetPosition);
                resetMotion();
            }
        }
    	remainingFramesForCurrentMove = Math.max(remainingFramesForCurrentMove - 1, 0);
    }

    /// Implements Positionable

    @Override
    public Vector getVelocity() {
        return getOrientation().toVector().mul(framesForCurrentMove);
    }
}
