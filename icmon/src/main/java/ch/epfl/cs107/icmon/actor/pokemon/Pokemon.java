package ch.epfl.cs107.icmon.actor.pokemon;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonFightableActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.messages.StartPokemonFightMessage;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

/**
 * ???
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public abstract class Pokemon extends ICMonActor implements ICMonFightableActor {

    private String name;
    private int hpMax;
    private int hp;
    private int damages;
    private RPGSprite sprite;

    private final List<ICMonFightAction> actionsList;

    public Pokemon(Area area, Orientation orientation, DiscreteCoordinates position, String name,
                   int damages, int hpMax, List<ICMonFightAction> actionsList) {
        super(area, orientation, position);
        this.name = name;
        this.hpMax = hpMax;
        this.hp = hpMax;
        this.damages = damages;
        this.sprite = new RPGSprite("pokemon/" + name, 1, 1, this);
        this.actionsList = actionsList;
    }

    @Override
    public void fight(ICMon.ICMonGameState game) {
        game.send(new StartPokemonFightMessage(this));
    }

    public PokemonProperties properties(){
        return new PokemonProperties();
    }

    public void dealDamages(int damages) {
        hp -= damages;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     */
    public final class PokemonProperties {

        public String name() {
            return name;
        }

        public float hp() {
            return hp;
        }

        public float maxHp() {
            return hpMax;
        }

        public int damage() {
            return damages;
        }

        public List<ICMonFightAction> actions() { return actionsList; }

    }
}