package ch.epfl.cs107.play.window.swing;

import ch.epfl.cs107.play.math.Transform;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Draw a single image.
 */
public final class ImageItem implements Item {

    private final float depth;
    private final float alpha;
    private final Transform transform;
    private final SwingImage image;

    /**
     * Creates a new image item.
     * @param depth (float): associated depth
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param transform (Transform): transform used, not null
     * @param image (SwingImage): swing image, not null
     */
    public ImageItem(float depth, float alpha, Transform transform, SwingImage image) {
        this.depth = depth;
        this.alpha = alpha;
        this.transform = transform;
        this.image = image;
    }

    /// ImageItem implements Item

    @Override
    public float getDepth() {
        return depth;
    }
    
    @Override
    public void render(Graphics2D g) {
        if (alpha <= 0.0f)
            return;
        // Note: image space has inverted Y-axis, need to flip vertically
        BufferedImage i = image.image;
        float sx = 1.0f / i.getWidth();
        float sy = 1.0f / i.getHeight();
        AffineTransform a = new AffineTransform(
            transform.m00 * sx, transform.m10 * sx,
            transform.m01 * sy, -transform.m11 * sy,
            transform.m02 + transform.m01, transform.m12 + transform.m11
        );
        if (alpha >= 1.0f)
            g.drawImage(i, a, null);
        else {
            Composite composite = g.getComposite();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g.drawImage(i, a, null);
            g.setComposite(composite);
        }
    }
    
}
