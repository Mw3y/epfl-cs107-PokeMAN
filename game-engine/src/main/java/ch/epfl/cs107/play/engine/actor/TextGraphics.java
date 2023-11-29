package ch.epfl.cs107.play.engine.actor;

import java.awt.Color;

import ch.epfl.cs107.play.math.Attachable;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


/**
 * Contains information to render a single string, which can be attached to any positionable.
 */
public class TextGraphics extends Node implements Attachable, Graphics {
    
	private String text;
    private float fontSize;
	private Color fillColor;
	private Color outlineColor;
    private float thickness;
	private String fontName;
	private boolean bold;
	private boolean italics;
    private Vector anchor;
	private float alpha;
	private float depth;
    private TextAlign.Horizontal hAlign;
    private TextAlign.Vertical vAlign;
    
    private final static String DEFAULT_FONT = "Kenney Pixel";

    /**
     * Creates a new text graphics.
     * @param text (String): content, not null
     * @param fontSize (float): size
     * @param fillColor (Color): fill color, may be null
     * @param outlineColor (Color): outline color, may be null
     * @param thickness (float): outline thickness
     * @param bold (boolean): whether to use bold font
     * @param italics (boolean): whether to use italics font
     * @param anchor (Vector): text anchor
     * @param hAlign (TextAlign.Horizontal): the horizontal alignment
     * @param vAlign (TextAlign.Vertical): the vertical alignment
     * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
     * @param depth (float): render priority, lower-values drawn first
     */
    public TextGraphics(String text, float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics,
                        Vector anchor, TextAlign.Horizontal hAlign, TextAlign.Vertical vAlign, float alpha, float depth) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
        this.fontSize = fontSize;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.thickness = thickness;
        this.fontName = DEFAULT_FONT;
        this.bold = bold;
        this.italics = italics;
        this.anchor = anchor;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        this.alpha = alpha;
        this.depth = depth;
    }

    /**
     * Creates a new text graphics.
     * @param text (String): content, not null
     * @param fontSize (float): size
     * @param fillColor (Color): fill color, may be null
     * @param outlineColor (Color): outline color, may be null
     * @param thickness (float): outline thickness
     * @param bold (boolean): whether to use bold font
     * @param italics (boolean): whether to use italics font
     * @param anchor (Vector): text anchor
     */
    public TextGraphics(String text, float fontSize, Color fillColor, Color outlineColor, float thickness, boolean bold, boolean italics, Vector anchor) {
        this(text, fontSize, fillColor, outlineColor, thickness, bold, italics, anchor, TextAlign.Horizontal.LEFT, TextAlign.Vertical.BOTTOM, 1.0f, 0.0f);
    }
    
    /**
     * Creates a new text graphics.
     * @param text (String): content, not null
     * @param fontSize (float): size
     * @param fillColor (Color): fill color, may be null
     */
    public TextGraphics(String text, float fontSize, Color fillColor) {
        this(text, fontSize, fillColor, null, 0.0f, false, false, Vector.ZERO);
    }

    /**
     * Sets text content.
     * @param text (String): content, not null
     */
    public void setText(String text) {
        if (text == null)
            throw new NullPointerException();
        this.text = text;
    }

    /** @return (String): text content, not null */
    public String getText() {
        return text;
    }

    /**
     * Sets font size.
     * @param fontSize (float): size
     */
    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    /** @return (float): font size */
    public float getFontSize() {
        return fontSize;
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
	 * Set the font name
	 * @param fontName (String): the font name
	 */
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	
	/**
	 * @return (String): the font name
	 */
	public String getFontName() {
		return fontName;
	}
	
    /**
     * Sets bold font.
     * @param bold (boolean): whether to use bold font
     */
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    /** @return (boolean):  whether to use bold font */
    public boolean isBold() {
        return bold;
    }

    /**
     * Sets italics font.
     * @param italics (boolean): whether to use italics font
     */
    public void setItalics(boolean italics) {
        this.italics = italics;
    }

    /** @return (boolean): whether to use italics font */
    public boolean isItalics() {
        return italics;
    }

    /**
     * Sets text anchor, i.e. how to orient it.
     * @param anchor (Vector): text anchor
     */
    public void setAnchor(Vector anchor) {
        this.anchor = anchor;
    }

    /** @return (Vector): text anchor */
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
        canvas.drawText(text, fontSize, getTransform(), fillColor, outlineColor, thickness, fontName, bold, italics, anchor, hAlign, vAlign, alpha, depth);
	}

}
