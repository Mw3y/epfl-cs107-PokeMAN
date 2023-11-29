package ch.epfl.cs107.play.areagame.area;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.cs107.play.engine.actor.Draggable;
import ch.epfl.cs107.play.engine.actor.Droppable;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.actor.Interactor;
import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;


/**
 * AreaBehavior is a basically a map made of Cells. Those cells are used for the game behavior
 * Note: implementation from Interactable.Listener not excpected from students
 */

public abstract class AreaBehavior implements Interactable.Listener, Interactor.Listener{

    /// The behavior is an Image of size height x width
    private final Image behaviorMap;
    private final int width, height;
    /// We will convert the image into an array of cells
    private final Cell[][] cells;

    /**
     * Default AreaBehavior Constructor
     * @param window (Window): graphic context, not null
     * @param name (String): name of the behavior image, not null
     */
    public AreaBehavior(Window window, String name){
        // Load the image
        //System.out.println(ResourcePath.getBehavior(name));
        behaviorMap = window.getImage(ResourcePath.getBehavior(name), null, false);
        // Get the corresponding dimension and init the array
        height = behaviorMap.getHeight();
        width = behaviorMap.getWidth();
        cells = new Cell[width][height];
    }

    public void dropInteractionOf(Draggable draggable, DiscreteCoordinates mouseCoordinates) {
    	if(mouseCoordinates.x >= 0 && mouseCoordinates.y >= 0 && mouseCoordinates.x < width && mouseCoordinates.y < height) {
    		cells[mouseCoordinates.x][mouseCoordinates.y].dropInteractionOf(draggable);
    	}
    }

    /// AreaBehavior implements Interactor.Listener

    @Override
    public void cellInteractionOf(Interactor interactor){
        for(DiscreteCoordinates dc : interactor.getCurrentCells()){
            if(dc.x < 0 || dc.y < 0 || dc.x >= width || dc.y >= height)
                continue;
            cells[dc.x][dc.y].cellInteractionOf(interactor);
        }
    }

    @Override
    public void viewInteractionOf(Interactor interactor){
        for(DiscreteCoordinates dc : interactor.getFieldOfViewCells()){
            if(dc.x < 0 || dc.y < 0 || dc.x >= width || dc.y >= height)
                continue;
            cells[dc.x][dc.y].viewInteractionOf(interactor);
        }
    }
    
    protected void setCell(int x,int y, Cell cell) {
    	cells[x][y] = cell;
    }
    
    protected Cell getCell(int x, int y) {
    	return cells[x][y];
    }
    protected int getRGB(int r, int c) {
    	return behaviorMap.getRGB(r, c);
    }
    
    protected int getHeight() {
    	return height;
    }
    
    protected int getWidth() {
    	return width;
    }
    

    /// AreaBehavior implements Interactable.Listener

    @Override
    public boolean canLeave(Interactable entity, List<DiscreteCoordinates> coordinates) {

        for(DiscreteCoordinates c : coordinates){
            if(c.x < 0 || c.y < 0 || c.x >= width || c.y >= height)
                return false;
            if(!cells[c.x][c.y].canLeave(entity))
                return false;
        }
        return true;
    }

    @Override
    public boolean canEnter(Interactable entity, List<DiscreteCoordinates> coordinates) {
        for(DiscreteCoordinates c : coordinates){
            if(c.x < 0 || c.y < 0 || c.x >= width || c.y >= height)
                return false;
            if(!cells[c.x][c.y].canEnter(entity))
                return false;
        }
        return true;
    }

    @Override
    public void leave(Interactable entity, List<DiscreteCoordinates> coordinates) {

        for(DiscreteCoordinates c : coordinates){
            cells[c.x][c.y].leave(entity);
        }

    }

    @Override
    public void enter(Interactable entity, List<DiscreteCoordinates> coordinates) {
        for(DiscreteCoordinates c : coordinates){
            cells[c.x][c.y].enter(entity);
        }
    }

    /**
     * Each AreaGame will have its own Cell extension.
     * At minimum a cell is linked to its content
     */
    public abstract class Cell implements Interactable{

        /// Content of the cell as a set of Interactable
        protected Set<Interactable> entities;
        protected DiscreteCoordinates coordinates;


        /**
         * Default Cell constructor
         * @param x (int): x-coordinate of this cell
         * @param y (int): y-coordinate of this cell
         */
        public Cell(int x, int y){
            entities = new HashSet<>();
            coordinates = new DiscreteCoordinates(x, y);
        }

        /**
         * Do the given draggableAreaEntity interacts with all Droppable sharing the same cell
         * @param interactor (Interactor), not null
         */
        private void dropInteractionOf(Draggable draggable) {
        	for(Interactable interactable : entities){
                if(interactable instanceof Droppable) {
                	Droppable droppable = (Droppable)interactable;
                	if(droppable.canDrop())
                		droppable.receiveDropFrom(draggable);
                }
            }
        	if(this instanceof Droppable) {
            	Droppable droppable = (Droppable)this;
            	if(droppable.canDrop())
            		droppable.receiveDropFrom(draggable);
        	}
        		
        }
        
        /**
         * Do the given interactor interacts with all Interactable sharing the same cell
         * @param interactor (Interactor), not null
         */
        private void cellInteractionOf(Interactor interactor){
            interactor.interactWith(this, true);
            for(Interactable interactable : entities){
                if(interactable.isCellInteractable())
                    interactor.interactWith(interactable, true);
            }
        }

        /**
         * Do the given interactor interacts with all Interactable sharing the same cell
         * @param interactor (Interactor), not null
         */
        private void viewInteractionOf(Interactor interactor){
            interactor.interactWith(this, false);
            for(Interactable interactable : entities){
                if(interactable.isViewInteractable())
                    interactor.interactWith(interactable, false);
            }
        }

        /**
         * Do the given interactable enter into this Cell
         * @param entity (Interactable), not null
         */
        protected void enter(Interactable entity) {
            entities.add(entity);
        }

        /**
         * Do the given Interactable leave this Cell
         * @param entity (Interactable), not null
         */
        protected void leave(Interactable entity) {
            entities.remove(entity);
        }

        /**
         * Indicate if the given Interactable can leave this Cell
         * @param entity (Interactable), not null
         * @return (boolean): true if entity can leave
         */
        protected abstract boolean canLeave(Interactable entity);

        /**
         * Indicate if the given Interactable can enter this Cell
         * @param entity (Interactable), not null
         * @return (boolean): true if entity can enter
         */
        protected abstract boolean canEnter(Interactable entity);

        /// Cell implements Interactable

        @Override
        public boolean takeCellSpace(){
            return false;
        }

        @Override
        public void onLeaving(List<DiscreteCoordinates> coordinates) {}

        @Override
        public void onEntering(List<DiscreteCoordinates> coordinates) {}

        @Override
        public List<DiscreteCoordinates> getCurrentCells() {
            return Collections.singletonList(coordinates);
        }

    }
}
