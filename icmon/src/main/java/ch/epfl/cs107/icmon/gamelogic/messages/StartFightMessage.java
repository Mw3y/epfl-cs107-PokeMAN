package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Trainer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.PokemonFightEvent;
import ch.epfl.cs107.icmon.gamelogic.events.TrainerFightEvent;

public class StartFightMessage implements GamePlayMessage {

    private Trainer trainer;
    private final Pokemon opponentPokemon;
    private final Pokemon playerPokemon;

    public StartFightMessage(Pokemon opponentPokemon, Pokemon playerPokemon) {
        this.opponentPokemon = opponentPokemon;
        this.playerPokemon = playerPokemon;
    }

    public StartFightMessage(Trainer trainer, Pokemon trainerPokemon, Pokemon playerPokemon) {
        this.trainer = trainer;
        this.opponentPokemon = trainerPokemon;
        this.playerPokemon = playerPokemon;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager) {
        System.out.println("message.player.startFight");
        // Create the fight event depending on the opponent to fight
        PokemonFightEvent event = trainer == null
                ? new PokemonFightEvent(gameState, eventManager, player, playerPokemon, opponentPokemon)
                : new TrainerFightEvent(gameState, eventManager, player, trainer, playerPokemon, opponentPokemon);

        // Automatically register and unregister this event
        event.onStart(new RegisterEventAction(eventManager, event));
        event.onComplete(new UnregisterEventAction(eventManager, event));
        // Ask the game to suspend all ongoing events and pause itself
        gameState.send(new SuspendWithEventMessage(event));
    }

}
