package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.messages.StartFightMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;

import java.util.ArrayList;
import java.util.List;

public abstract class Trainer extends NPCActor implements ICMonFightableActor {

    private final List<Pokemon> pokemons = new ArrayList<>();
    private boolean acceptsFights = false;

    /**
     * Represents a Pokémon trainer with a custom region of interest that the player can challenge.
     *
     * @param area        (Area): Owner area. Not null.
     * @param orientation (Orientation): Initial orientation of the entity. Not null.
     * @param position    (Coordinate): Initial position of the entity. Not null.
     * @param spriteName  (String): Initial sprite of the trainer.
     * @param regionOfInterest (RegionOfInterest): The region of interest of the image
     */
    public Trainer(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName, RegionOfInterest regionOfInterest) {
        super(area, orientation, position, spriteName, regionOfInterest);
    }

    /**
     * Represents a Pokémon trainer that the player can challenge.
     *
     * @param area        (Area): Owner area. Not null.
     * @param orientation (Orientation): Initial orientation of the entity. Not null.
     * @param position    (Coordinate): Initial position of the entity. Not null.
     * @param spriteName  (String): Initial sprite of the trainer.
     */
    public Trainer(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
        super(area, orientation, position, spriteName);
    }

    /**
     * Gives a Pokémon to the player.
     *
     * @param pokemon (Pokemon)
     * @return
     */
    protected boolean givePokemon(Pokemon pokemon) {
        assert pokemon != null;
        return pokemons.add(pokemon);
    }

    /**
     * Checks if the player has got Pokémon able to fight.
     *
     * @return true if the list of Pokémon is not empty and if not all of them are KO.
     */
    public boolean hasHealthyPokemon() {
        for (Pokemon pokemon : pokemons) {
            if (!pokemon.properties().isKO())
                return true;
        }
        return false;
    }

    /**
     * @return true if the player is able to fight.
     */
    public boolean acceptsFights() {
        return this.acceptsFights;
    }

    /**
     * Setter for acceptsFights.
     *
     * @param acceptance (boolean) : the capability of the player to accept a fight.
     */
    public void setFightsAcceptance(boolean acceptance) {
        acceptsFights = acceptance;
    }

    /**
     * Gives access to its Pokémon list to all Trainer subclasses.
     *
     * @return the trainer Pokémon list.
     */
    protected List<Pokemon> getPokemons() {
        return pokemons;
    }


}
