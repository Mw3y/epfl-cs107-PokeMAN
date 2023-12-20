package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class ICShopAssistant extends NPCActor {

    /**
     * Represents a shop assistant npc.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public ICShopAssistant(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "actors/assistant");
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        assert v != null;
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }
}
