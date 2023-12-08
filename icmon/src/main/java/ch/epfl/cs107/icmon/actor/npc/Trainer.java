package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;

import java.util.ArrayList;

public abstract class Trainer extends NPCActor implements ICMonFightableActor {

    protected final ArrayList<Pokemon> pokemons = new ArrayList<>();
    private boolean acceptsFights = false;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     * @param spriteName
     */
    public Trainer(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
        super(area, orientation, position, spriteName);
    }

    protected boolean givePokemon(Pokemon pokemon) {
        return pokemons.add(pokemon);
    }

    public boolean hasHealthyPokemon() {
        return pokemons.stream().noneMatch(Pokemon::isKO);
    }

    public boolean acceptsFights() {
        return this.acceptsFights;
    }

    public void setFightsAcceptance(boolean acceptance) {
        acceptsFights = acceptance;
    }
}
