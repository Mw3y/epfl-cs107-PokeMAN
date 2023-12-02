package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class Latios extends Pokemon {
    public static final String NAME = "latios";
    public static final int DAMAGES = 1;
    public static final int HP_MAX = 10;

    public Latios(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, NAME, DAMAGES, HP_MAX);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }

}
