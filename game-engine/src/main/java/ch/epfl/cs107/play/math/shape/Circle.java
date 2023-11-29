package ch.epfl.cs107.play.math.shape;

import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.random.RandomGenerator;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;


/**
 * Represents an immutable circle.
 */
public final class Circle extends Shape {
    
    private final float radius;
    private final Vector center;

    /**
     * Creates a new circle.
     * @param radius (float): size, not negative
     * @param center (Vector): origin, not null
     */
    public Circle(float radius, Vector center) {
        if (center == null)
            throw new NullPointerException();
        this.radius = radius;
        this.center = center;
    }

    /**
     * Creates a new circle.
     * @param radius (float): size, not negative
     */
    public Circle(float radius) {
        this(radius, Vector.ZERO);
    }
    
    /** @return (float): size of circle */
    public float getRadius() {
        return radius;
    }

    /** @return (Vector): origin of circle, not null */
    public Vector getCenter() {
        return center;
    }


    /// Circle extends Shape

    @Override
    public float getArea() {
        return (float)Math.PI * radius * radius;
    }

    @Override
    public float getPerimeter() {
        return 2.0f * (float)Math.PI * radius;
    }

    @Override
    public Vector sample() {
        
        // Sample random angle and distance (density increase quadratically)
        double distance = Math.sqrt(RandomGenerator.getInstance().nextDouble()) * radius;
        double angle = RandomGenerator.getInstance().nextDouble() * 2.0 * Math.PI;
        
        // Compute actual location
        return new Vector(
            center.x + (float)(distance * Math.cos(angle)),
            center.y + (float)(distance * Math.sin(angle))
        );
    }

    @Override
    public Path2D toPath() {
        // TODO is it possible to cache this? need to check if SwingWindow modifies it...
        Ellipse2D ellipse = new Ellipse2D.Float(
            center.x - radius,
		    center.y - radius,
            radius * 2,
            radius * 2
        );
		return new Path2D.Float(ellipse);
    }
}
