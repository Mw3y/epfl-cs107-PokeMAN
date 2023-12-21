package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.play.engine.PauseMenu;

public class PauseGameAction implements Action {

    private final ICMon.ICMonGameState game;
    private final PauseMenu pauseMenu;

    /**
     * Constructor for PauseGameMenu
     * @param game (ICMon.ICMonGameState)
     * @param pauseMenu (PauseMenu)
     */
    public PauseGameAction(ICMon.ICMonGameState game, PauseMenu pauseMenu) {
        assert game != null;
        assert pauseMenu != null;
        this.game = game;
        this.pauseMenu = pauseMenu;
    }

    /**
     * Set the game state to pause.
     */
    @Override
    public void perform() {
        System.out.println("action.game.pause");
        game.pause(pauseMenu);
    }
}
