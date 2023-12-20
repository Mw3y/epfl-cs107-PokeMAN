package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;

public class Attack implements ICMonFightAction {

    private String name;
    private int power;

    /**
     * Default Attack method.
     */
    public Attack() {
        this("Attack", 10);
    }

    public Attack(String name, int power) {
        assert name != null;
        assert power >= 0;
        this.name = name;
        this.power = power;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean doAction(Pokemon target, Pokemon pokemon) {
        assert target != null;
        assert pokemon != null;
        Pokemon.PokemonProperties atkProps = pokemon.properties();
        Pokemon.PokemonProperties defProps = target.properties();

        float damages = (float) (2 * (atkProps.attack() / defProps.defense())) / 50 + 2;
        target.dealDamages(damages);
        return true;
    }
}
