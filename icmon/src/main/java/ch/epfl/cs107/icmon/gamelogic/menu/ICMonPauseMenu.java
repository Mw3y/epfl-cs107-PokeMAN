package ch.epfl.cs107.icmon.gamelogic.menu;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.graphics.ICMonPauseMenuGraphics;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class ICMonPauseMenu extends PauseMenu {

    private ICMon.ICMonGameState gameState;

    private ICMonPauseMenuGraphics graphics;

    /**
     * The ICMon game main menu.
     * @param gameState - The state manager of the game
     */
    public ICMonPauseMenu(ICMon.ICMonGameState gameState) {
        this.gameState = gameState;
        graphics = new ICMonPauseMenuGraphics(CAMERA_SCALE_FACTOR);
    }

    @Override
    public void update(float deltaTime) {
        Keyboard keyboard = getKeyboard();
        if (keyboard.get(Keyboard.ENTER).isPressed()) {
            gameState.resume();
        }
        super.update(deltaTime);
    }

    @Override
    protected void drawMenu(Canvas c) {
        graphics.draw(c);
    }
}
