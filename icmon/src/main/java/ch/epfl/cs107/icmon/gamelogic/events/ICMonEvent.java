package ch.epfl.cs107.icmon.gamelogic.events;

import ch.epfl.cs107.icmon.ICMon;
import ch.epfl.cs107.icmon.actor.ICMonPlayer;
import ch.epfl.cs107.icmon.gamelogic.actions.Action;
import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.engine.PauseMenu;
import ch.epfl.cs107.play.engine.Updatable;

import java.util.ArrayList;
import java.util.List;

public abstract class ICMonEvent implements Updatable, ICMonInteractionVisitor {

    private final List<Action> startActions = new ArrayList<>();
    private final List<Action> suspendActions = new ArrayList<>();
    private final List<Action> resumeActions = new ArrayList<>();
    private final List<Action> completeActions = new ArrayList<>();

    private boolean isStarted = false;
    private boolean isCompleted = false;
    private boolean isSuspended = false;

    /**
     * Default constructor for an ICMonEvent.
     */
    public ICMonEvent() {}

    /**
     * @return the pause menu for this event if any.
     */
    public PauseMenu getPauseMenu() {
        return null;
    }

    /**
     * @return whether this event has a pause menu or not.
     */
    public boolean hasPauseMenu() {
        return false;
    }

    /**
     * Executes all actions in the provided actions list.
     * @param actions - The actions to perform
     */
    private void performActions(List<Action> actions) {
        assert actions != null;
        assert !actions.isEmpty();
        // Iterate on actions and call the perform() method of all of them.
        for (Action action : actions) {
            action.perform();
        }
    }

    /**
     * Starts the event.
     */
    final public void start() {
        if (!isStarted) {
            performActions(startActions);
            isStarted = true;
        }
    }

    /**
     * Completes the event.
     */
    final public void complete() {
        if (isStarted && !isCompleted) {
            performActions(completeActions);
            isCompleted = true;
        }
    }

    /**
     * Suspends the event.
     */
    final public void suspend() {
        if (isStarted && !isCompleted && !isSuspended) {
            performActions(suspendActions);
            isSuspended = true;
        }
    }

    /**
     * Resumes the event.
     */
    final public void resume() {
        if (!isCompleted && isSuspended && isStarted) {
            performActions(resumeActions);
            isSuspended = false;
        }
    }

    /**
     * Registers an action to perform at the start of the event.
     * @param action - The action to perform
     */
    final public void onStart(Action action) {
        assert action != null;
        startActions.add(action);}

    /**
     * Registers an action to perform when the event has been completed.
     * @param action - The action to perform
     */
    final public void onComplete(Action action) {
        assert action != null;
        completeActions.add(action);
    }

    /**
     * Registers an action to perform when the event has been suspended.
     * @param action - The action to perform
     */
    final public void onSuspension(Action action) {
        assert action != null;
        suspendActions.add(action);
    }

    /**
     * Registers an action to perform when the event has been resumed.
     * @param action - The action to perform
     */
    final public void onResume(Action action) {
        assert action != null;
        resumeActions.add(action);
    }

    /**
     * @return whether the event has been started or not.
     */
    final public boolean isStarted() {
        return isStarted;
    }

    /**
     * @return whether the event has been completed or not.
     */
    final public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * @return whether the event is suspended or not.
     */
    final public boolean isSuspended() {
        return isSuspended;
    }
}
