package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.Lab;
import ch.epfl.cs107.icmon.area.maps.Town;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.CollectItemEvent;
import ch.epfl.cs107.icmon.gamelogic.events.EndOfTheGameEvent;
import ch.epfl.cs107.icmon.gamelogic.events.ICMonEvent;
import ch.epfl.cs107.icmon.gamelogic.messages.GamePlayMessage;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.LinkedList;
import java.util.List;

public class ICMon extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 12.5f;
    private ICMonPlayer player;
    private final List<ICMonEvent> registeredEvents = new LinkedList<>();
    private final List<ICMonEvent> unregisteredEvents = new LinkedList<>();

    private final List<ICMonEvent> events = new LinkedList<>();
    private final ICMonGameState gameState = new ICMonGameState();
    private final ICMonEventManager eventManager = new ICMonEventManager();

    @Override
    public String getTitle() {
        return "Pokebyte";
    }

    public void end() {
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            gameState.createAreas();
            gameState.initArea("Town");

            final ICBall ball = new ICBall(getCurrentArea(), new DiscreteCoordinates(6, 6), "items/icball");
            final CollectItemEvent ballCollectEvent = new CollectItemEvent(eventManager, ball);
            getCurrentArea().registerActor(ball);

            final EndOfTheGameEvent endOfTheGameEvent = new EndOfTheGameEvent(eventManager);
            ballCollectEvent.onComplete(new StartEventAction(endOfTheGameEvent));

            ballCollectEvent.start();

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
            registeredEvents.clear();
            unregisteredEvents.clear();
            events.clear();
            begin(getWindow(), getFileSystem());
        }

        for (ICMonEvent event : registeredEvents) {
            addEvent(event);
        }
        registeredEvents.clear();

        for (ICMonEvent event : unregisteredEvents) {
            removeEvent(event);
        }
        unregisteredEvents.clear();

        for (ICMonEvent event : events) {
            event.update(deltaTime);
        }

        gameState.update(deltaTime);
        super.update(deltaTime);
    }

    private void addEvent(ICMonEvent event) {
        events.add(event);
    }

    private void removeEvent(ICMonEvent event) {
        events.remove(event);
    }

    public class ICMonGameState implements Updatable {

        private GamePlayMessage playerMessage;

        private ICMonGameState() {}

        public void send(GamePlayMessage message) {
            playerMessage = message;
        }

        public void clear() {
            playerMessage = null;
        }

        private void createAreas() {
            addArea(new Town());
            addArea(new Lab());
        }

        private void initArea(String areaTitle) {
            ICMonArea area = (ICMonArea) setCurrentArea(areaTitle, true);
            DiscreteCoordinates coords = area.getPlayerSpawnPosition();
            // Initialize player
            player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState);
            player.enterArea(area, coords);
            player.centerCamera();
        }

        /**
         * ???
         */
        public void changeArea(String areaTitle, DiscreteCoordinates spawnPosition) {
            player.leaveArea();
            ICMonArea currentArea = (ICMonArea) setCurrentArea(areaTitle, false);
            player.enterArea(currentArea, spawnPosition);
        }

        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }

        @Override
        public void update(float deltaTime) {
            if (playerMessage != null)
                playerMessage.process(this, player);
                clear();
        }
    }

    public class ICMonEventManager {

        private ICMonEventManager() {}

        public final boolean registerEvent(ICMonEvent event) {
            return registeredEvents.add(event);
        }

        public final boolean unregisterEvent(ICMonEvent event) {
            return unregisteredEvents.add(event);
        }

        public final ICMonPlayer getPlayer() {
            return player;
        }

        public ICMon getGame() {
            return ICMon.this;
        }
    }

}
