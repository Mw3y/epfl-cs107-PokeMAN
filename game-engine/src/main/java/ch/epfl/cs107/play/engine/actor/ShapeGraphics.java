package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.shape.Shape;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.Color;

/**
 * Contains information to render a single shape, which can be attached to any positionable.
 */
public class ShapeGraphics extends Node implements Graphics {
    
    private Shape shape;
	private Color fillColor;
	private Color outlineColor;
	private float thickness;
	private float alpha;
	private float depth;

    /**
     * Creates a new shape graphics.
     * @param shape (Shape): shape, may be null
     * @param fillColor (Color): fill color, may be null
     * @param outlineColor (Color): outline color, may be null
     * @param thickness (float): outline thickness
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param depth (float): render priority, lower-values drawn first
     */
    public ShapeGraphics(Shape shape, Color fillColor, Color outlineColor, float thickness, float alpha, float depth) {
        this.shape = shape;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.thickness = thickness;
        this.alpha = alpha;
        this.depth = depth;
    }

    /**
     * Creates a new shape graphics.
     * @param shape (Shape): shape, may be null
     * @param fillColor (Color): fill color, may be null
     * @param outlineColor (Color): outline color, may be null
     * @param thickness (float): outline thickness
     */
    public ShapeGraphics(Shape shape, Color fillColor, Color outlineColor, float thickness) {
        this(shape, fillColor, outlineColor, thickness, 1.0f, 0.0f);
    }

    /**
     * Sets shape.
     * @param shape (Shape): new shape, may be null
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }
    
    /** @return (Shape): current shape, may be null */
    public Shape getShape() {
        return shape;
    }

    /**
     * Sets fill color.
     * @param fillColor (Color): color, may be null
     */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
    
    /** @return (Color): fill color, may be null */
	public Color getFillColor() {
		return fillColor;
	}

    /**
     * Sets outline color.
     * @param outlineColor (Color): color, may be null
     */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

    /** @return (Color): outline color, may be null */
	public Color getOutlineColor() {
		return outlineColor;
	}

    /**
     * Sets outline thickness.
     * @param thickness (float): outline thickness
     */
    public void setThickness(float thickness) {
		this.thickness = thickness;
	}
    
    /** @return (float): outline thickness */
	public float getThickness() {
		return thickness;
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
		canvas.drawShape(shape, getTransform(), fillColor, outlineColor, thickness, alpha, depth);
	}
    
}
