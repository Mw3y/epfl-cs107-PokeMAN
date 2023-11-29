package ch.epfl.cs107.play.math;

/**
 * Simple attachable object.
 */
public class Node implements Attachable {

    private Positionable parent;
    private Transform transform;

    /** Creates a new node at origin. */
    public Node() {
        parent = null;
        transform = Transform.I;
    }

    /// Node implements Attachable

    @Override
    public void setParent(Positionable parent) {
        this.parent = parent;
    }

    @Override
    public Positionable getParent() {
        return parent;
    }
    
    @Override
    public Transform getRelativeTransform() {
        return transform;
    }

    @Override
    public void setRelativeTransform(Transform transform) {
        if (transform == null)
            throw new NullPointerException();
        this.transform = transform;
    }

    @Override
    public Transform getTransform() {
        Transform relative = getRelativeTransform();
        if (parent == null)
            return relative;
        Transform absolute = relative.transformed(parent.getTransform());
        return absolute;
    }

    @Override
    public Vector getPosition() {
        Transform relative = getRelativeTransform();
        if (parent == null)
            return relative.getOrigin();
        return relative.onPoint(parent.getPosition());
    }

    @Override
    public Vector getVelocity() {
        Transform relative = getRelativeTransform();
        return relative.onVector(parent.getVelocity());
    }
}
