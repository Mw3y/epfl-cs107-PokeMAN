package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.Collections;
import java.util.List;

public class ICBall extends ICMonItem {

    /**
     * ICBall constructor
     * @param area (Area) : owner area. Not null
     * @param position (DiscreteCoordinate): Initial position of the ball in the Area. Not null
     * @param spriteName (String) : sprite of the ball. Not null
     */
    public ICBall(Area area, DiscreteCoordinates position, String spriteName) {
        super(area, Orientation.DOWN, position, spriteName);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
