package ch.epfl.cs107.play.engine;


/**
 * Represents a updatable element (which can be updated)
 */
public interface Updatable  {
    
    /**
     * Simulates a single time step.
     * Note: Need to be Override
     * @param deltaTime elapsed time since last update, in seconds, non-negative
     */
    void update(float deltaTime);
}
