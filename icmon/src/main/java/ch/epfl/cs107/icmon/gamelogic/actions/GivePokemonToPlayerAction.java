package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Latios;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;

public class GivePokemonToPlayerAction implements Action{
    private Pokemon pokemon;
    private ICMonPlayer player;

    public GivePokemonToPlayerAction(Pokemon pokemon, ICMonPlayer player){
        this.pokemon = pokemon;
        this.player = player;
    }

    @Override
    public void perform() {
        System.out.println("action.give." + pokemon.toString() + ".to.player");
        player.getPokemons().add(pokemon);
    }
}
