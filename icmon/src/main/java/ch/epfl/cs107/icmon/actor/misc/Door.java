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

    private final List<DiscreteCoordinates> cellPositions;

    private final String destinationAreaTitle;
    private final DiscreteCoordinates destinationAreaSpawnPosition;

    public Door(String destinationAreaTitle, DiscreteCoordinates destinationAreaSpawnPosition, Area ownerArea, DiscreteCoordinates mainCellPosition, DiscreteCoordinates... cellPositions) {
        super(ownerArea, Orientation.UP, mainCellPosition);

        this.cellPositions = new ArrayList<>(Arrays.asList(cellPositions));
        this.cellPositions.add(mainCellPosition);

        this.destinationAreaTitle = destinationAreaTitle;
        this.destinationAreaSpawnPosition = destinationAreaSpawnPosition;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return cellPositions;
    }

    public String getDestinationAreaTitle() {
        return destinationAreaTitle;
    }

    public DiscreteCoordinates getDestinationAreaSpawnPosition() {
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
    public void draw(Canvas canvas) {}
}
