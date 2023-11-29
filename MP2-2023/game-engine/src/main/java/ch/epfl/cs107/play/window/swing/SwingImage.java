package ch.epfl.cs107.play.window.swing;

import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Image;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Swing implementation of an image.
 */
public final class SwingImage implements Image {

    // Package-protected, for efficient access
    final BufferedImage image;

    /**
     * Creates an image from specified image.
     * @param image (java.awt.Image): valid image to be copied, not null
     * @param roi (RegionOfInterest): rectangle of interest in the image, may be null
     * @param removeBackground (boolean): which indicate if need to remove an uniform background
     */
	public SwingImage(java.awt.Image image, RegionOfInterest roi, boolean removeBackground) {
		// See
		// http://stackoverflow.com/questions/196890/java2d-performance-issues
		// http://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
		// http://stackoverflow.com/questions/148478/java-2d-drawing-optimal-performance
		
		// Get system graphical configuration
		final GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		// Get image size
		int width = image.getWidth(null);
		int height = image.getHeight(null);

		if(roi != null){
		    width = Math.min(width, roi.w);
		    height = Math.min(height, roi.h);
        }

		// Create optimized buffered image
		this.image = config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		
		// Draw original image in buffer
		final Graphics2D graphics = this.image.createGraphics();
		if(roi == null) {
            graphics.drawImage(image, 0, 0, null);
        }else {
            graphics.drawImage(image, 0, 0, roi.w, roi.h, roi.x, roi.y, roi.x+roi.w, roi.y+roi.h,null);
        }
		graphics.dispose();

		if(removeBackground){
		    removeBackground();
        }
	}
    
    /**
     * Creates an image from specified image input stream.
     * @param stream (InputStream): valid image input stream, not null
     * @param roi (RegionOfInterest): rectangle of interest in the image, may be null
     * @param removeBackground (boolean): which indicate if need to remove an uniform background
     * @throws IOException if an error occurs during reading
     */
    public SwingImage(InputStream stream, RegionOfInterest roi, boolean removeBackground) throws IOException {
        this(ImageIO.read(stream), roi, removeBackground);
    }

    /**
     * Remove uniform background from an image, putting full alpha instead.
     * Useful to integrate Sprite which are given with uniform background color
     * Assume background color is contained into the top left corner
     */
    private void removeBackground(){

        final int backgroundRGB = image.getRGB(0,0);
        final int alpha = 0;

        final int w = image.getWidth();
        final int h = image.getHeight();

        final int[] rgb = image.getRGB(0, 0, w, h, null, 0, w);
        for (int i = 0; i < rgb.length; i++) {
            if (rgb[i] == backgroundRGB) {
                rgb[i] = alpha;
            }
        }
        image.setRGB(0, 0, w, h, rgb, 0, w);
    }


    /// SwingImage implements Image

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int getRGB(int r, int c){
        return image.getRGB(c, r);
    }
    
}
