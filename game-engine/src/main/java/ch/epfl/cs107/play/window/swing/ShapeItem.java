package ch.epfl.cs107.play.window.swing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;


/**
 * Draw a single shape.
 */
public final class ShapeItem implements Item {

	private final Shape shape;
	private final Color fillColor;
	private final Color outlineColor;
	private final float thickness;
	private final float alpha;
	private final float depth;

    /**
     * Creates a new shape item.
     * @param shape (Shape): Swing shape, not null
     * @param fillColor (Color): fill color, may be null
     * @param outlineColor (Color): outline color, may be null
     * @param thickness (float): outline thickness
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param depth (float): associated depth
     */
	public ShapeItem(Shape shape, Color fillColor, Color outlineColor, float thickness, float alpha, float depth) {
		super();
		this.shape = shape;
		this.fillColor = fillColor;
		this.outlineColor = outlineColor;
		this.thickness = thickness;
		this.alpha = alpha;
		this.depth = depth;
	}

	/// ShapeItem implements Item

	@Override
	public float getDepth() {
		return depth;
	}

	@Override
	public void render(Graphics2D g) {
        if (alpha <= 0.0f)
            return;
        Composite old = null;
        if (alpha < 1.0) {
            old = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }
		if (fillColor != null) {
			g.setColor(fillColor);
			g.fill(shape);
		}
		if (outlineColor != null) {
			g.setColor(outlineColor);
			g.setStroke(new BasicStroke(thickness));
			g.draw(shape);
		}
        if (old != null)
            g.setComposite(old);
	}
}
