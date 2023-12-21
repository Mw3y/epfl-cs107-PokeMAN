package ch.epfl.cs107.icmon.gamelogic.actions;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;

public class StartDialogAction implements Action {

    private final String path;
    private final ICMonPlayer player;

    /**
     * Constructor for StartDialogAction
     * @param path (String)
     * @param player (ICMonPlayer)
     */
    public StartDialogAction(String path, ICMonPlayer player) {
        assert path != null;
        assert player != null;
        this.path = path;
        this.player = player;
    }

    /**
     * Opens the dialog from a given path
     */
    @Override
    public void perform() {
        System.out.println("action.player.start.dialog." + path);
        player.openDialog(path);
    }
}
