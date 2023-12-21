package ch.epfl.cs107.icmon.actor.pokemon.actions;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.icmon.gamelogic.fights.PokemonMoveType;

public class RunAway implements ICMonFightAction {

    /**
     * Default RunAway method.
     */
    public void RunAway(){
    }

    @Override
    public String name() {
        return "Run away";
    }

    @Override
    public PokemonMoveType type() {
        return PokemonMoveType.SPECIAL;
    }

    @Override
    public boolean doAction(Pokemon target, Pokemon pokemon) {
        assert target != null;
        assert pokemon != null;
        return false;
    }
}
