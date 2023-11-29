package ch.epfl.cs107.play.tuto2;

import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.tuto2.actor.GhostPlayer;
import ch.epfl.cs107.play.tuto2.area.Tuto2Area;
import ch.epfl.cs107.play.tuto2.area.maps.Ferme;
import ch.epfl.cs107.play.tuto2.area.maps.Village;
import ch.epfl.cs107.play.window.Window;

/**
 * ???
 */
public final class Tuto2 extends AreaGame {

    /** ??? */
    public final static float CAMERA_SCALE_FACTOR = 13.f;
    /** ??? */
    private final String[] areas = {"zelda/Ferme", "zelda/Village"};
    /** ??? */
    private GhostPlayer player;
    /** ??? */
    private int areaIndex;

    /**
     * ???
     */
    private void createAreas() {
        addArea(new Ferme());
        addArea(new Village());
    }

    /**
     * ???
     * @param window (Window): display context. Not null
     * @param fileSystem (FileSystem): given file system. Not null
     * @return ???
     */
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            areaIndex = 0;
            initArea(areas[areaIndex]);
            return true;
        }
        return false;
    }

    /**
     * ???
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    @Override
    public void update(float deltaTime) {
        if (player.isWeak())
            switchArea();
        super.update(deltaTime);
    }

    /**
     * ???
     */
    @Override
    public void end() {

    }

    /**
     * ???
     * @return ???
     */
    @Override
    public String getTitle() {
        return "Tuto2";
    }

    /**
     * ???
     * @param areaKey ???
     */
    private void initArea(String areaKey) {
        Tuto2Area area = (Tuto2Area) setCurrentArea(areaKey, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        player = new GhostPlayer(area, Orientation.DOWN, coords, "ghost.1");
        player.enterArea(area, coords);
        player.centerCamera();
    }

    /**
     * ???
     */
    private void switchArea() {
        player.leaveArea();
        areaIndex = (areaIndex == 0) ? 1 : 0;
        Tuto2Area currentArea = (Tuto2Area) setCurrentArea(areas[areaIndex], false);
        player.enterArea(currentArea, currentArea.getPlayerSpawnPosition());
        player.strengthen();
    }

}