package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.engine.actor.Actor;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;


/**
 * Basic Entity are simply actor and represented by a current exact position and its corresponding transform
 */
public abstract class Entity implements Actor {

    /// Exact Position as a floating vector
    private Vector currentPosition;
    /// Corresponding transformation
    private Transform transform;


    /**
     * Default Entity constructor
     * @param position (Coordinate): Initial position of the entity. Not null
     */
    public Entity(Vector position) {

        if (position == null )
            throw new NullPointerException();
        this.currentPosition = position;
    }

    /**
     * Update the current position (i.e. after motion)
     * after position change, the transform need to be updated to. Hence set it to null
     * @param v (Vector): The new Position. Not null
     */
    protected void setCurrentPosition(Vector v){
        this.currentPosition = v;
        transform = null;
    }

    /// Entity implements Positionable

    @Override
    public Transform getTransform() {
        // Compute the transform only when needed
        if (transform == null) {
            float x = currentPosition.x;
            float y = currentPosition.y;
            transform = new Transform(
                    1, 0, x,
                    0, 1, y
            );
        }
        return transform;
    }

    @Override
    public Vector getPosition(){
        return currentPosition;
    }

    @Override
    public Vector getVelocity() {
        return Vector.ZERO;
    }
}
