package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;

public class StartPokemonFightMessage implements GamePlayMessage {

    private final Pokemon pokemon;

    public StartPokemonFightMessage(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState game, ICMon.ICMonEventManager eventManager) {
        final PokemonFightEvent event = new PokemonFightEvent(game, eventManager, player, pokemon);
        game.send(new SuspendWithEventMessage(event));
    }

}
