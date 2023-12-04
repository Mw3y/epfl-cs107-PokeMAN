package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.graphics.ICMonFightArenaGraphics;
import ch.epfl.cs107.icmon.graphics.ICMonFightTextGraphics;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {

    private float compteur = 5f;
    private ICMonFightArenaGraphics area;

    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;

    public ICMonFight(Pokemon playerPokemon, Pokemon opponentPokemon){
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.area = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponentPokemon.properties());
        this.area.setInteractionGraphics(new ICMonFightTextGraphics(CAMERA_SCALE_FACTOR, "Hello world"));
    }

    @Override
    protected void drawMenu(Canvas c) {
        area.draw(c);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isRunning())
            compteur -= deltaTime;
    }

    public boolean isRunning() {
        return compteur > 0;
    }

    public void drawMenu(){
        ICMonFightArenaGraphics arena = new ICMonFightArenaGraphics(CAMERA_SCALE_FACTOR, playerPokemon.properties(), opponentPokemon.properties());
    }

}
