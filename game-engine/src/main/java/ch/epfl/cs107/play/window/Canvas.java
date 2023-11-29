package ch.epfl.cs107.play.window;

import java.awt.Color;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.shape.Shape;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

/**
 * Represents a rendering context, with various drawing capabilities.
 */
public interface Canvas extends Positionable {

    // TODO maybe provide some size/aspect ratio information

    /**
     * Gets image from file system.
     * @param name (String): full name of image, not null
     * @param roi (RegionOfInterest): region of interest
     * @param removeBackground (boolean): which indicate if we need to remove an uniform background
     * @return an image object, null on error
     */
    Image getImage(String name, RegionOfInterest roi, boolean removeBackground);

    /**
     * Draws specified image.
     * @param image (Image): any image associated to this context, may be null
     * @param transform (Transform): any affine transform, not null
     * @param alpha (float): transparency, between 0.0 and 1.0
     * @param depth (float): any real, larger values are drawn afterward, i.e. above
     */
    void drawImage(Image image, Transform transform, float alpha, float depth);

    /**
     * Draws specified image
     * @param shape (Shape): any shape, may be null
     * @param transform (Transform): any affine transform, not null
     * @param fillColor (Color): color used to fill the shape, may be null
     * @param outlineColor (Color): color used to draw shape border, may be null
     * @param thickness (float): border thickness
     * @param alpha (float): transparency, between 0.0 and 1.0
     * @param depth (float): any real, larger values are drawn afterward, i.e. above
     */
    void drawShape(Shape shape, Transform transform, Color fillColor, Color outlineColor, float thickness, float alpha, float depth);
    
    /**
     * Register all the font in a directory
     * @param directoryName (String): the name of the directory
     */
    void registerFonts(String directoryName);
    
    /**
     * Creates a new text graphics.
     * @param text (String): content, not null
     * @param transform (Transform): affine transform, not null
     * @param fontSize (float): size
     * @param fillColor (Color): fill color, may be null
     * @param outlineColor (Color): outline color, may be null
     * @param thickness (float): outline thickness
     * @param fontName (String): the font name
     * @param bold (boolean): whether to use bold font
     * @param italics (boolean): whether to use italics font
     * @param anchor (Vector): text anchor
     * @param hAlign (TextAlign.Horizontal): horizontal alignment of the text around the anchor vector
     * @param vAlign (TextAlign.Vertical): vertical alignment of the text around the anchor vector
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param depth (float): render priority, lower-values drawn first
     */
    void drawText(String text, float fontSize, Transform transform, Color fillColor, Color outlineColor, float thickness, String fontName,
                  boolean bold, boolean italics, Vector anchor, TextAlign.Horizontal hAlign, TextAlign.Vertical vAlign, float alpha, float depth);
        
    /**
     * Convert a coordinate in the canvas to a coordinate in the screen
     * @param coord the coordinate in the canvas
     * @return the coordinate in the screen
     */
    Vector convertPositionOnScreen(Vector coord);
    /**
     * Return the canvas width
     * @return width (int): the canvas width
     */
    int getWidth();
    
    /**
     * Return the canvas height
     * @return height (int): the canvas height
     */
    int getHeight();
    
    /**
     * Return the canvas scaled width
     * @return width (float): the canvas scaled width
     */
    float getXScale();
    
    /**
     * Return the canvas scaled height
     * @return height (float): the canvas scaled height
     */
    float getYScale();
    
    
    /**
     * Return the canvas scaled width after adjusting to ratio
     * @return width (float): the canvas scaled width
     */
    float getScaledWidth();
    
    /**
     * Return the canvas scaled height after adjusting to ratio
     * @return height (float): the canvas scaled height
     */
    float getScaledHeight();
    
}
