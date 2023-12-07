package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;

public class StartDialogAction implements Action {

    private final String path;
    private ICMonPlayer player;

    public StartDialogAction(String path, ICMonPlayer player) {
        this.path = path;
        this.player = player;
    }

    @Override
    public void perform() {
        player.openDialog(path);
    }
}
