package ch.epfl.cs107.play.math.shape;

import ch.epfl.cs107.play.math.Vector;

import java.awt.geom.Path2D;

/**
 * Base class of all physical shapes.
 */
public abstract class Shape {
        
    /** @return shape area */
    public abstract float getArea();
    
    /** @return shape perimeter */
    public abstract float getPerimeter();
    
    // TODO bounding box/circle?
    
    // TODO apply transform (or at least some translation, scale, rotation) to shape?
    
    /**
     * Sample uniform point inside shape, including border.
     * @return (Vector): a uniform sample, not null
     */
    public abstract Vector sample();
    
    /** @return (Path2D): AWT path used for drawing */
    public abstract Path2D toPath();
}
