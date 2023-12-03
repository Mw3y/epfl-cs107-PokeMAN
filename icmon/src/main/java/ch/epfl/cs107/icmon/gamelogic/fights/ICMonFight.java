package ch.epfl.cs107.icmon.gamelogic.fights;

import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;

public class ICMonFight extends PauseMenu {
    private float compteur = 5f;

    @Override
    protected void drawMenu(Canvas c) {

    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isRunning())
            compteur -= deltaTime;
    }

    public boolean isRunning() {
        return compteur > 0;
    }
}
