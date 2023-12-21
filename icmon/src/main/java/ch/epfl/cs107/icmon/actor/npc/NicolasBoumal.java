package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class NicolasBoumal extends Trainer{
    /**
     * Represents a Pokémon trainer that the player can challenge.
     *
     * @param area        (Area): Owner area. Not null.
     * @param orientation (Orientation): Initial orientation of the entity. Not null.
     * @param position    (Coordinate): Initial position of the entity. Not null.
     */
    public NicolasBoumal(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position, "actors/nicolas_boumal" );
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {

    }

    @Override
    public void fight(ICMon.ICMonGameState game, Pokemon playerPokemon) {

    }
}
