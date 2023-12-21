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
    public String sfx() {
        return "cut";
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean doAction(Pokemon target, Pokemon pokemon) {
        return doAction(target, pokemon, false);
    }

    @Override
    public boolean doAction(Pokemon target, Pokemon pokemon, boolean boost) {
        assert target != null;
        assert pokemon != null;

        Pokemon.PokemonProperties atkProps = pokemon.properties();
        Pokemon.PokemonProperties defProps = target.properties();

        // Calculate the damages of this attack based on Pokémon properties
        int random = RandomGenerator.getInstance().nextInt(85, 100) / 20;
        float damages = (float) (2 * ((atkProps.attack() / defProps.defense())) * power / 50 + 2)* atkProps.getAttackTypeCoeff(defProps) * random;

        // Artificially boost player to compensate level difference with legendary Pokémon
        // if(boost)
            // damages *= 2;

        System.out.println("-------------------------");
        System.out.println(atkProps.name().toUpperCase() + "'s turn:");
        System.out.println("Random: " + random);
        System.out.println("Attack: " + atkProps.attack());
        System.out.println("Type coeff: " + atkProps.getAttackTypeCoeff(defProps));
        System.out.println("Power: " + power);
        System.out.println("Damages done: " + damages);
        System.out.println("-------------------------");

        target.dealDamages(damages);
        return true;
    }
}
