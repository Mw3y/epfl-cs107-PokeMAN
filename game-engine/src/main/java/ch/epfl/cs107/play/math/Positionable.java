package ch.epfl.cs107.play.math;


/**
 * Represents an object that can be defined by an affine transform.
 */
public interface Positionable {

    /** @return (Transform): affine transform, not null */
    Transform getTransform();
    
    /** @return (Vector): origin, not null */
    default Vector getPosition() {
        return getTransform().getOrigin();
    }

    /** @return (Vector): linear velocity, not null */
    Vector getVelocity();
}
