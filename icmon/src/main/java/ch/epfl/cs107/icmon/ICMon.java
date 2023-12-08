package ch.epfl.cs107.icmon;

import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.actor.items.ICBall;
import ch.epfl.cs107.icmon.actor.npc.Garry;
import ch.epfl.cs107.icmon.area.ICMonArea;
import ch.epfl.cs107.icmon.area.maps.*;
import ch.epfl.cs107.icmon.gamelogic.actions.RegisterInAreaAction;
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

    public void end() {}

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

    private void events() {
            ICMonEvent introduction = new IntroductionEvent(gameState, eventManager, player);
            ICMonEvent firstInteractionWithProfOak = new FirstInteractionWithProfOakEvent(gameState, eventManager, player);

            Garry garry = new Garry(getCurrentArea(), Orientation.DOWN, new DiscreteCoordinates(1, 4));
            getCurrentArea().registerActor(garry);
            ICMonEvent firstInteractionWithGarry = new FirstInteractionWithGarryEvent(gameState, eventManager, player, garry);

            ICBall ball = new ICBall(areas.get(Town.TITLE), new DiscreteCoordinates(6, 6), "items/icball");
            ICMonEvent collectBall = new CollectItemEvent(gameState, eventManager, player, ball);
            collectBall.onStart(new RegisterInAreaAction(areas.get(Town.TITLE), ball));

            ICMonEvent endOfTheGame = new EndOfTheGameEvent(gameState, eventManager, player);

            ICMonChainedEvent mainScenario = new ICMonChainedEvent(gameState, eventManager, player, introduction, firstInteractionWithProfOak, collectBall, firstInteractionWithGarry, endOfTheGame);
            eventManager.registerEvent(mainScenario);
            mainScenario.start();
    }
    

    private void addEvent(ICMonEvent event) {
        events.add(event);
    }

    private void removeEvent(ICMonEvent event) {
        events.remove(event);
    }

    private void createAreas() {
        areas.put(Town.TITLE, new Town());
        areas.put(Lab.TITLE, new Lab());
        areas.put(Arena.TITLE, new Arena());
        areas.put(House.TITLE, new House());
        areas.put(Shop.TITLE, new Shop());

        for (ICMonArea area : areas.values()) {
            addArea(area);
        }
    }

    private void initArea(String areaTitle) {
        ICMonArea area = (ICMonArea) setCurrentArea(areaTitle, true);
        DiscreteCoordinates coords = area.getPlayerSpawnPosition();
        // Initialize player
        player = new ICMonPlayer(area, Orientation.DOWN, coords, gameState);
        player.enterArea(area, coords);
        player.centerCamera();
    }

    public class ICMonGameState implements Updatable {

        private final Queue<GamePlayMessage> messagesQueue = new LinkedList<>();

        private ICMonGameState() {
        }

        public void send(GamePlayMessage message) {
            messagesQueue.add(message);
        }

        public void acceptInteraction(Interactable interactable, boolean isCellInteraction) {
            for (var event : ICMon.this.events) {
                interactable.acceptInteraction(event, isCellInteraction);
            }
        }

        /**
         * ???
         */
        public void changeArea(String areaTitle, DiscreteCoordinates spawnPosition) {
            player.leaveArea();
            ICMonArea currentArea = (ICMonArea) setCurrentArea(areaTitle, false);
            player.enterArea(currentArea, spawnPosition);
        }

        public void pause(PauseMenu menu) {
            setPauseMenu(menu);
            requestPause();
        }

        public void resume() {
            requestResume();
        }

        @Override
        public void update(float deltaTime) {
            while (!messagesQueue.isEmpty()) {
                // .poll() returns the first value in the queue and removes it
                messagesQueue.poll().process(player, this, eventManager);
            }
        }
    }

    public class ICMonEventManager {

        private ICMonEventManager() {
        }

        public void registerSuspendEventActions(ICMonEvent event) {
            // LinkedList<ICMonEvent> suspendedEvents = new LinkedList<>(events);
            for(ICMonEvent eventToSuspend : events) {
                event.onStart(new SuspendEventAction(eventToSuspend));
            }
        }
        public void registerResumeEventActions(ICMonEvent event) {
            // LinkedList<ICMonEvent> resumedEvents = new LinkedList<>(events);
            for(ICMonEvent eventToResume : events) {
                event.onComplete(new SuspendEventAction(eventToResume));
            }
        }

        public boolean registerEvent(ICMonEvent event) {
            return registeredEvents.add(event);
        }

        public boolean unregisterEvent(ICMonEvent event) {
            return unregisteredEvents.add(event);
        }
    }

}
