package ch.epfl.cs107.icmon.actor.npc.league;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.data.PokemonDataLoader;
import ch.epfl.cs107.icmon.gamelogic.messages.StartFightMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

public class NicolasBoumal extends Trainer {
    /**
     * Represents a Pokémon trainer that the player can challenge.
     *
     * @param area        (Area): Owner area. Not null.
     * @param orientation (Orientation): Initial orientation of the entity. Not null.
     * @param position    (Coordinate): Initial position of the entity. Not null.
     */
    public NicolasBoumal(Area area, Orientation orientation, DiscreteCoordinates position) {
        super("nicolas_boumal", area, orientation, position, "actors/nicolas_boumal", null, false);
        givePokemon(new PokemonDataLoader().load(115).toPokemon(getOwnerArea(), Orientation.DOWN, position));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void fight(ICMon.ICMonGameState game, Pokemon playerPokemon) {
        game.send(new StartFightMessage(this, getPokemons().get(0), playerPokemon));
    }
}
