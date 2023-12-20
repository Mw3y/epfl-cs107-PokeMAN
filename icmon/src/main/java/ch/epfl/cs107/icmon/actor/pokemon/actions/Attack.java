package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonMoveType;
import ch.epfl.cs107.play.math.random.RandomGenerator;

public class Attack implements ICMonFightAction {

    private String name;
    private int power;

    /**
     * Default Attack method.
     */
    public Attack() {
        this("Attack", 50);
    }

    public Attack(String name, int power) {
        assert name != null;
        assert power >= 0;
        this.name = name;
        this.power = power;
    }

    @Override
    public PokemonMoveType type() {
        return PokemonMoveType.PHYSICAL;
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

        System.out.println(power);

        // Calculate the damages of this attack based on Pokémon properties
        int random = RandomGenerator.getInstance().nextInt(85, 100) / 20;
        float damages = (float) (2 * ((atkProps.attack() / defProps.defense())) * power / 50 + 2)* atkProps.getAttackTypeCoeff(defProps) * random;

        target.dealDamages(damages);
        return true;
    }
}
