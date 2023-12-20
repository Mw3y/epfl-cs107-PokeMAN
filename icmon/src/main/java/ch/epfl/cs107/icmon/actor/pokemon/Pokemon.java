package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.data.PokemonType;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.messages.StartFightMessage;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Audio;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

/**
 * The Pokémon class.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public class Pokemon extends ICMonActor implements ICMonFightableActor {

    private final String name;
    // TODO: Readme justify usage of float
    private final float hpMax;
    private final int attack;
    private final int defense;
    private final RPGSprite sprite;
    private final List<ICMonFightAction> actionsList;
    private int pokedexId;
    private List<PokemonType> types;
    // TODO: Readme justify usage of float
    private float hp;

    /**
     * Represents a Pokémon.
     *
     * @param area        - The area where the Pokémon belongs
     * @param orientation - The default orientation of the Pokémon
     * @param position    - The spawn position of the Pokémon in its area
     * @param name        - The name of the Pokémon
     * @param pokedexId   - The Pokédex id of the Pokémon
     * @param types       - The types of the Pokémon
     * @param attack      - The damages that the Pokémon deals
     * @param defense     - The damages that the Pokémon deals
     * @param hpMax       - The maximum health of the Pokémon
     * @param actionsList - The list of possible fight actions for this Pokémon
     */
    public Pokemon(Area area, Orientation orientation, DiscreteCoordinates position, String name,
                   int pokedexId, List<PokemonType> types, int attack, int defense, int hpMax, List<ICMonFightAction> actionsList) {
        super(area, orientation, position);
        this.name = name;
        this.pokedexId = pokedexId;
        this.types = types;
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.attack = attack;
        this.defense = defense;
        this.sprite = new RPGSprite("pokemons/" + pokedexId, 1, 1, this);
        this.actionsList = actionsList;
    }

    @Override
    public void fight(ICMon.ICMonGameState game, Pokemon playerPokemon) {
        game.send(new StartFightMessage(this, playerPokemon));
    }

    /**
     * Gets the properties of this Pokémon.
     *
     * @return the properties list of this Pokémon.
     */
    public PokemonProperties properties() {
        return new PokemonProperties();
    }

    /**
     * Deals damages to this Pokémon.
     *
     * @param damages - The amount of health to remove from this Pokémon.
     */
    public void dealDamages(float damages) {
        hp -= damages;
    }

    /**
     * Restores the health of this Pokémon by a certain amount.
     *
     * @param hp - The hp to restore
     */
    public void heal(float hp) {
        // Prevent from adding too much hp
        this.hp = Math.min(hp, hpMax);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICMonInteractionVisitor) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void bip(Audio audio) {
        super.bip(audio);
    }

    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {

        public int pokedexId() {
            return pokedexId;
        }

        public List<PokemonType> types() {
            return types;
        }

        public String name() {
            return name;
        }

        public float hp() {
            return hp;
        }

        public float maxHp() {
            return hpMax;
        }

        public int attack() {
            return attack;
        }

        public int defense() {
            return defense;
        }

        public List<ICMonFightAction> actions() {
            return actionsList;
        }

        public boolean hasLowHp() {
            return hp <= .35 * hpMax;
        }

        public boolean hasCriticalHp() {
            return hp <= .15 * hpMax;
        }

        public boolean isKO() {
            return hp <= 0;
        }

        public float getAttackTypeCoeff(PokemonProperties targetProps) {
            float attackCoeff = types.get(0).effectiveness().get(targetProps.types().get(0).name());
            if (types.size() > 1 && targetProps.types().size() > 1)
                attackCoeff *= types.get(1).effectiveness().get(targetProps.types().get(1).name());
            return attackCoeff;
        }

    }
}