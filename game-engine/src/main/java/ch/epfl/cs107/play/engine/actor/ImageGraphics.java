package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Image;


/**
 * Contains information to render a single image, which can be attached to any positionable.
 */
public class ImageGraphics extends Node implements Graphics {

    /// Region of interest as a rectangle in the image
    private final RegionOfInterest roi;
    /// Image name
    private String name;
    /// Image dimension
    private float width, height;
    /// Anchor of the image (i.e. if the origin of the image is not the origin of the parent)
    private Vector anchor;
    /// Transparency of the image. Between 0 (invisible) amd 1 (opaque)
    private float alpha;
    /// Depth used as render priority. It is the third axis. See it as altitude : lower values are drawn first
    private float depth;
    ///
    private final boolean removeBackground;

    /**
     * Creates a new image graphics.
     * @param name (String): image name, may be null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     * @param roi (RegionOfInterest): region of interest as a rectangle in the image
     * @param anchor (Vector): image anchor, not null
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param depth (float): render priority, lower-values drawn first
     * @param removeBackground (boolean): indicate if we need to remove the uniform color background before using this image
     */
    public ImageGraphics(String name, float width, float height, RegionOfInterest roi, Vector anchor, float alpha, float depth, boolean removeBackground) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.roi = roi;
        this.anchor = anchor;
        this.alpha = alpha;
        this.depth = depth;
        this.removeBackground = removeBackground;
    }

    /**
     * Creates a new image graphics.
     * @param name (String): image name, may be null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     * @param roi (RegionOfInterest): region of interest as a rectangle in the image
     * @param anchor (Vector): image anchor, not null
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param depth (float): render priority, lower-values drawn first
     */
    public ImageGraphics(String name, float width, float height, RegionOfInterest roi, Vector anchor, float alpha, float depth) {
        this(name, width, height, roi, anchor, alpha, depth, false);
    }

    /**
     * Creates a new image graphics.
     * @param name (String): image name, may be null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     * @param roi (RegionOfInterest): region of interest as a rectangle in the image
     * @param anchor (Vector): image anchor, not null
     */
    public ImageGraphics(String name, float width, float height, RegionOfInterest roi, Vector anchor) {
        this(name, width, height, roi, anchor, 1.0f, 0.0f, false);
    }

    /**
     * Creates a new image graphics.
     * Creates a new image graphics.
     * @param name (String): image name, may be null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     * @param roi (RegionOfInterest): region of interest as a rectangle in the image
     */
    public ImageGraphics(String name, float width, float height, RegionOfInterest roi) {
        this(name, width, height, roi, Vector.ZERO);
    }

    /**
     * Creates a new image graphics.
     * @param name (String): image name, may be null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     * @param roi (RegionOfInterest): region of interest as a rectangle in the image
     * @param removeBackground (boolean): indicate if we need to remove the uniform color background before using this image
     */
    public ImageGraphics(String name, float width, float height, RegionOfInterest roi, boolean removeBackground) {
        this(name, width, height, roi, Vector.ZERO, 1.0f, 0.0f, removeBackground);
    }

    /**
     * Creates a new image graphics.
     * @param name (String): image name, may be null
     * @param width (float): actual image width, before transformation
     * @param height (float): actual image height, before transformation
     */
    public ImageGraphics(String name, float width, float height) {
        this(name, width, height, null, Vector.ZERO);
    }
   
    /**
     * Sets image name.
     * @param name (String): new image name, may be null
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return (String): image name, may be null */
    public String getName() {
        return name;
    }

    /**
     * Sets actual image width, before transformation.
     * @param width (float): image width
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /** @return (float): actual image width, before transformation */
    public float getWidth() {
        return width;
    }

    /**
     * Sets actual image height, before transformation.
     * @param height (float): image height
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /** @return (float): actual image height, before transformation */
    public float getHeight() {
        return height;
    }

    /**
     * Sets image anchor location, i.e. where is the center of the image.
     * @param anchor (Vector): image anchor, not null
     */
    public void setAnchor(Vector anchor) {
        this.anchor = anchor;
    }

    /** @return (Vector): image anchor, not null */
    public Vector getAnchor() {
        return anchor;
    }
    
    /**
     * Sets transparency.
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /** @return (float): transparency, between 0 (invisible) and 1 (opaque) */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Sets rendering depth.
     * @param depth (float): render priority, lower-values drawn first
     */
    public void setDepth(float depth) {
        this.depth = depth;
    }

    /** @return (float): render priority, lower-values drawn first */
    public float getDepth() {
        return depth;
    }
    
    @Override
    public void draw(Canvas canvas) {
        if (name == null)
            return;
        Image image = canvas.getImage(name, roi, removeBackground);
        Transform transform = Transform.I.scaled(width, height).translated(anchor.x, anchor.y).transformed(getTransform());
        canvas.drawImage(image, transform, alpha, depth);
    }
}
