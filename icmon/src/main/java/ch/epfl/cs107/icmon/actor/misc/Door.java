package ch.epfl.cs107.icmon.actor.misc;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Door extends AreaEntity {

    /// Coordinates of the doors
    private final List<DiscreteCoordinates> cellPositions;
    /// Title of the destination area
    private final String destinationAreaTitle;
    /// Spawn position coordinates in the destination area
    private final DiscreteCoordinates destinationAreaSpawnPosition;

    /**
     * Represents a door that allows to change the player area.
     *
     * @param destinationAreaTitle         (String) : title of the destination area. Not null.
     * @param destinationAreaSpawnPosition (DiscreteCoordinates) : spawn position coordinates in the destination area. Not null.
     * @param ownerArea                    (Area) : area of the door. Nor null.
     * @param mainCellPosition             (DiscreteCoordinates) : coordinates of the main cell linked to the door. Not null.
     * @param cellPositions                (DiscreteCoordinates) : coordinates of the extra cells needed
     */
    public Door(String destinationAreaTitle, DiscreteCoordinates destinationAreaSpawnPosition, Area ownerArea,
                DiscreteCoordinates mainCellPosition, DiscreteCoordinates... cellPositions) {
        super(ownerArea, Orientation.UP, mainCellPosition);

        this.cellPositions = new ArrayList<>(Arrays.asList(cellPositions));
        this.cellPositions.add(mainCellPosition);

        this.destinationAreaTitle = destinationAreaTitle;
        this.destinationAreaSpawnPosition = destinationAreaSpawnPosition;
    }

    @Override
    public final List<DiscreteCoordinates> getCurrentCells() {
        return cellPositions;
    }

    /**
     * Getter for DestinationAreaTitle
     *
     * @return (String) the title of the destination area
     */
    public final String getDestinationAreaTitle() {
        return destinationAreaTitle;
    }

    /**
     * Getter for DestinationAreaSpawnPosition
     *
     * @return (DiscreteCoordinates) the spawn position in the destination area
     */
    public final DiscreteCoordinates getDestinationAreaSpawnPosition() {
        return destinationAreaSpawnPosition;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }


    @Override
    public void draw(Canvas canvas) {
    }
}
