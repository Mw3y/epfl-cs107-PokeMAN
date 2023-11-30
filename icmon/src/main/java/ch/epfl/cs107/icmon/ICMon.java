package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class ICMon extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 12.5f;
    private ICMonPlayer player;

    private void createAreas() {
        addArea(new Town());
    }

    @Override
    public String getTitle() {
        return "Pokebyte";
    }

    public void end() {
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            initArea("Town");
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        // "Reset" development feature
        Keyboard keyboard = getWindow().getKeyboard();
        if (keyboard.get(Keyboard.R).isDown()) {
            // There's no need to unregister anything since the areas map
            // will automatically be overridden by the begin() method
            begin(getWindow(), getFileSystem());
        }

        super.update(deltaTime);
    }

    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        // Initialize player
        player = new ICMonPlayer(area, Orientation.DOWN, coords, "actors/player");
        player.enterArea(area, coords);
        player.centerCamera();
    }

}
