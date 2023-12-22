package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;

public class StartFightMessage implements GamePlayMessage {

    private Trainer trainer;
    private final Pokemon opponentPokemon;
    private final Pokemon playerPokemon;

    /**
     * Starts a fight with a Pokémon.
     * @param opponentPokemon - The opponent Pokémon
     * @param playerPokemon - The Pokémon of the player
     */
    public StartFightMessage(Pokemon opponentPokemon, Pokemon playerPokemon) {
        assert opponentPokemon != null;
        assert playerPokemon != null;
        this.opponentPokemon = opponentPokemon;
        this.playerPokemon = playerPokemon;
    }

    /**
     * Starts a fight with a trainer.
     * @param trainer - The trainer to fight
     * @param trainerPokemon - The opponent Pokémon
     * @param playerPokemon - The Pokémon of the player
     */
    public StartFightMessage(Trainer trainer, Pokemon trainerPokemon, Pokemon playerPokemon) {
        assert trainer != null;
        assert trainerPokemon != null;
        assert playerPokemon != null;
        this.trainer = trainer;
        this.opponentPokemon = trainerPokemon;
        this.playerPokemon = playerPokemon;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager) {
        assert player != null;
        assert gameState != null;
        assert eventManager != null;
        System.out.println("message.player.startFight");
        // Create the fight event depending on the opponent to fight
        PokemonFightEvent event = new PokemonFightEvent(gameState, playerPokemon, opponentPokemon, trainer);
        // Automatically register and unregister this event
        event.onStart(new RegisterEventAction(eventManager, event));
        event.onComplete(new UnregisterEventAction(eventManager, event));
        // Ask the game to suspend all ongoing events and pause itself
        gameState.send(new SuspendWithEventMessage(event));
    }

}
