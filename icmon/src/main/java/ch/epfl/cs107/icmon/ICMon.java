package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterInAreaAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public class ICMon extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 12.5f;
    private ICMonPlayer player;
    private final List<ICMonEvent> events = new ArrayList<>();
    private ICMonArea currentArea;
    private ICMonGameState gameState;

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
            ball = new ICBall(currentArea, new DiscreteCoordinates(6, 6), "items/icball");
            events.add(new CollectItemEvent(ball));
            RegisterinAreaAction actionRegister = new RegisterinAreaAction(currentArea, ball);
            actionRegister.perform();
            for (ICMonEvent event : events) {
                event.start();
            }
            return true;
        }
        return false;
    }

    @Override
    public void update(float deltaTime) {
        // "Reset" development feature
        Keyboard keyboard = getWindow().getKeyboard();
        if (keyboard.get(Keyboard.R).isPressed()) {
            // There's no need to unregister anything since the areas map
            // will automatically be overridden by the begin() method
            begin(getWindow(), getFileSystem());
        }

        super.update(deltaTime);
        for (ICMonEvent event : events) {
            event.update(deltaTime);
        }
    }

    private void initArea(String areaKey) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaKey, true);
        currentArea = area;
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        // Initialize player
        player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState);
        player.enterArea(area, coords);
        player.centerCamera();
    }

    public class ICMonGameState {
        private ICMonGameState() {

        }

        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }
    }

}
