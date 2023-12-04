package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.gamelogic.actions.LeaveAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.LogAction;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.UnregisterEventAction;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFight;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PokemonFightEvent extends ICMonEvent {

    private final ICMonFight pauseMenu;

    public PokemonFightEvent(ICMon.ICMonEventManager eventManager, ICMonPlayer player, Pokemon pokemon) {
        super(eventManager, player);

        this.pauseMenu = new ICMonFight(player.getPokemons().get(0), pokemon);

        onStart(new RegisterEventAction(eventManager, this));
        onComplete(new UnregisterEventAction(eventManager, this));

        onComplete(new LeaveAreaAction(pokemon));

        onStart(new LogAction("PokemonFightEvent started!"));
        onComplete(new LogAction("PokemonFightEvent completed!"));
    }

    @Override
    public void update(float deltaTime) {
        if (!pauseMenu.isRunning()) {
            complete();
        }
    }

    @Override
    public boolean hasPauseMenu(){
        return true;
    }

    @Override
    public final PauseMenu getPauseMenu(){
        return pauseMenu;
    }

}
