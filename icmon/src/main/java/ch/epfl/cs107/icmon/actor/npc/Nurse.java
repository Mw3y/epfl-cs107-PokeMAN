package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.*;

public class Nurse extends NPCActor {

    /**
     * Nurse constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Nurse(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "actors/nurse");
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        // The nurse takes the cell below to allow player view interactions
        return Arrays.asList(getCurrentMainCellCoordinates(), getCurrentMainCellCoordinates().down());
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    public boolean healPokemons(List<Pokemon> pokemons) {
        for (Pokemon pokemon : pokemons) {
            pokemon.heal(pokemon.properties().maxHp());
        }
        // TODO: Allow nurse to refuse
        return true;
    }
}
