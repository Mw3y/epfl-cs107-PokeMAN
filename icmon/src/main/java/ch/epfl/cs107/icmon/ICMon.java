package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.*;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterInAreaAction;
import ch.epfl.cs107.icmon.gamelogic.actions.ResumeEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.StartEventAction;
import ch.epfl.cs107.icmon.gamelogic.actions.SuspendEventAction;
import ch.epfl.cs107.icmon.gamelogic.events.*;
import ch.epfl.cs107.icmon.gamelogic.messages.GamePlayMessage;
import ch.epfl.cs107.play.areagame.AreaGame;
import ch.epfl.cs107.play.areagame.actor.Interactable;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.*;

public class ICMon extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 12.5f;
    private final List<ICMonEvent> registeredEvents = new LinkedList<>();
    private final List<ICMonEvent> unregisteredEvents = new LinkedList<>();
    private final List<ICMonEvent> events = new LinkedList<>();
    private ICMonPlayer player;

    private final ICMonGameState gameState = new ICMonGameState();
    private final ICMonEventManager eventManager = new ICMonEventManager();
    private final Map<String, ICMonArea> areas = new HashMap<>();

    @Override
    public String getTitle() {
        return "PokéMAN: Majorez les tous !";
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            createAreas();
            initArea(House.TITLE);
            events();
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

    /**
     * Creates the main scenario chained event.
     */
    private void events() {
        ICMonEvent introduction = new IntroductionEvent(player);
        ICMonEvent firstInteractionWithProfOak = new FirstInteractionWithProfOakEvent(player);

        Garry garry = new Garry(getCurrentArea(), Orientation.DOWN, new DiscreteCoordinates(1, 4));
        getCurrentArea().registerActor(garry);
        ICMonEvent firstInteractionWithGarry = new FirstInteractionWithGarryEvent(garry);

        ICBall ball = new ICBall(areas.get(Town.TITLE), new DiscreteCoordinates(6, 6), "items/icball");
        ICMonEvent collectBall = new CollectItemEvent(player, ball);
        collectBall.onStart(new RegisterInAreaAction(areas.get(Town.TITLE), ball));

        ICMonEvent endOfTheGame = new EndOfTheGameEvent(player);

        ICMonChainedEvent mainScenario = new ICMonChainedEvent(eventManager, introduction, firstInteractionWithProfOak, collectBall, firstInteractionWithGarry, endOfTheGame);
        eventManager.registerEvent(mainScenario);
        mainScenario.start();
    }

    /**
     * Adds an event to the events list.
     *
     * @param event - The event to add
     */
    private void addEvent(ICMonEvent event) {
        events.add(event);
    }

    /**
     * Removes an event to the events list.
     *
     * @param event - The event to remove
     */
    private void removeEvent(ICMonEvent event) {
        events.remove(event);
    }

    /**
     * Creates all game areas and registers them.
     */
    private void createAreas() {
        areas.put(Town.TITLE, new Town());
        areas.put(Lab.TITLE, new Lab());
        areas.put(Arena.TITLE, new Arena());
        areas.put(House.TITLE, new House());
        areas.put(Shop.TITLE, new Shop());
        // Register areas
        for (ICMonArea area : areas.values()) {
            addArea(area);
        }
    }

    /**
     * Initializes the first area of the game.
     *
     * @param areaTitle - The name of the area the player will spawn in
     */
    private void initArea(String areaTitle) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaTitle, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        // Initialize player
        player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState);
        player.enterArea(area, coords);
        player.centerCamera();
    }

    /**
     * State manager of the game
     * Provides access to the current state of the game.
     */
    public class ICMonGameState implements Updatable {

        private final Queue<GamePlayMessage> messagesQueue = new LinkedList<>();

        private ICMonGameState() {
        }

        /**
         * Adds a new action to perform to the game state.
         * @param message - The action to perform using the game state data
         */
        public void send(GamePlayMessage message) {
            messagesQueue.add(message);
        }

        /**
         * Forces an entity to accept the player interaction and dispatches the information to all running events.
         * @param interactable - The entity the player interacted with
         * @param isCellInteraction - Whether it was a contact interaction or not
         */
        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }

        /**
         * Changes the area in which the player currently evolves.
         * @param areaTitle - The name of the new area to load
         * @param spawnPosition - Where will the player appear in the new area
         */
        public void changeArea(String areaTitle, DiscreteCoordinates spawnPosition) {
            player.leaveArea();
            ICMonArea currentArea = (ICMonArea) setCurrentArea(areaTitle, false);
            player.enterArea(currentArea, spawnPosition);
        }

        /**
         * Asks to pause the game while displaying a pause menu.
         * @param menu - The pause menu to display
         */
        public void pause(PauseMenu menu) {
            if (!isPaused()) {
                setPauseMenu(menu);
                requestPause();
            }
        }

        /**
         * Asks the game to resume.
         */
        public void resume() {
            if (isPaused())
                requestResume();
        }

        @Override
        public void update(float deltaTime) {
            // Perform every request of the messagesQueue
            while (!messagesQueue.isEmpty()) {
                // .poll() returns the first value in the queue and removes it
                messagesQueue.poll().process(player, this, eventManager);
            }
        }
    }

    /**
     * Event manager of the game.
     * Provides access to event specific game utilities.
     */
    public class ICMonEventManager {

        private ICMonEventManager() {
        }

        /**
         * Creates a suspend event action for each game event currently active.
         *
         * @param event - The event that requires all events to pause
         */
        public void registerSuspendEventActions(ICMonEvent event) {
            for (ICMonEvent eventToSuspend : events) {
                if (!event.isSuspended())
                    event.onStart(new SuspendEventAction(eventToSuspend));
            }
        }

        /**
         * Creates a resume event action for each game event currently active.
         *
         * @param event - The event that requires all events to pause
         */
        public void registerResumeEventActions(ICMonEvent event) {
            for (ICMonEvent eventToResume : events) {
                if (!event.isSuspended())
                    event.onComplete(new ResumeEventAction(eventToResume));
            }
        }

        /**
         * Registers a new event in the game event loop.
         * @param event - The event to register
         * @return whether the operation was successful or not.
         */
        public boolean registerEvent(ICMonEvent event) {
            return registeredEvents.add(event);
        }

        /**
         * Unregisters a new event in the game event loop.
         * @param event - The event to unregister
         * @return whether the operation was successful or not.
         */
        public boolean unregisterEvent(ICMonEvent event) {
            return unregisteredEvents.add(event);
        }
    }

}
