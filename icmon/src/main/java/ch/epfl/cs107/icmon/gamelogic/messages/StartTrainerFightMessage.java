package ch.epfl.cs107.icmon.gamelogic.messages;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.events.TrainerFightEvent;

public class StartTrainerFightMessage implements GamePlayMessage {

    private final Garry trainer;
    private final Pokemon playerPokemon;
    private final Pokemon opponentPokemon;


    public StartTrainerFightMessage(Garry trainer, Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.trainer = trainer;
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
    }

    @Override
    public void process(ICMonPlayer player, ICMon.ICMonGameState gameState, ICMon.ICMonEventManager eventManager) {
        ICMonEvent event = new TrainerFightEvent(gameState, eventManager, player, trainer, playerPokemon, opponentPokemon);
        event.onStart(new RegisterEventAction(eventManager, event));
        event.onComplete(new UnregisterEventAction(eventManager, event));
        gameState.send(new SuspendWithEventMessage(event));
    }

}
