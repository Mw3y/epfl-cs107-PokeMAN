package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.ICMon;

public class ResumeGameAction implements Action {

    private final ICMon.ICMonGameState game;

    /**
     * Constructor for ResumeGameState
     * @param game (ICMon.ICMonGameState)
     */
    public ResumeGameAction(ICMon.ICMonGameState game) {
        assert game != null;
        this.game = game;
    }

    /**
     * Resumes a given game state.
     */
    @Override
    public void perform() {
        game.resume();
    }
}
