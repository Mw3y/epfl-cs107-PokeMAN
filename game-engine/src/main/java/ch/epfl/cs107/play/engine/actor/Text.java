package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.engine.actor.TextGraphics;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

/**
 * Text entity, can be used as actor and put and aligned inside a cell .
 */
public class Text extends Entity {

    /// Useful to know the bottom left of the canvas
    private final float DX;
    private final float DY;
    /// The corresponding text graphics
    private final TextGraphics textGraphics;
    /// Variables to make the text appear and disappear
    private float currentAlpha;
    private boolean makeItAppear;
    private boolean makeItDisappear;
    private float appearStep;
    /// Define if the text is relative to the world (false) or to the window (true), i.e. is the text always fixed in the window
    private final boolean isScreenRelative;
    private final Vector relativePosition;


    /**
     * Default Text constructor
     * @param text (String): String of the text, not null
     * @param position (DiscreteCoordinates): Initial position in the grid, not null
     * @param area (Area): Area context of the text, not null
     * @param isScreenRelative (boolean): indicate if the text is relative to the world or the window
     * @param fontSize (float): the font size (i.e. 1 is one cell high)
     * @param color (Color): Color of the text, not null
     * @param bold (boolean): Is the text bold
     * @param italics (boolean): Is the text italics
     * @param hAlign (Horizontal): Which horizontal alignment has the text, not null
     * @param vAlign (Vertical): Which vertical alignment has the text, not null
     * @param alpha (float): transparency of the text (0 is full invisible and 1 is full opaque)
     * @param depth (float): the depth of the text, small depth draw first
     */
    public Text(String text, DiscreteCoordinates position, Area area, boolean isScreenRelative, float fontSize, Color color, boolean bold, boolean italics,
                TextAlign.Horizontal hAlign, TextAlign.Vertical vAlign, float alpha, float depth) {
        super(position.toVector());

        DX = area.getCameraScaleFactor()/2;
        DY = area.getCameraScaleFactor()/2;

        float x = 0;
        float y = 0;

        if(hAlign == TextAlign.Horizontal.CENTER){
            x += 0.5;
        }else if(hAlign == TextAlign.Horizontal.RIGHT){
            x += 1;
        }
        if(vAlign == TextAlign.Vertical.MIDDLE){
            y += 0.5;
        }else if(vAlign == TextAlign.Vertical.TOP){
            y += 1;
        }

        this.textGraphics = new TextGraphics(text, fontSize, color, null, 0.0f, bold, italics, new Vector(x, y), hAlign, vAlign, alpha, depth);
        this.textGraphics.setParent(this);
        this.currentAlpha = alpha;
        this.makeItAppear = false;
        this.makeItDisappear = false;
        this.appearStep = 0;
        this.isScreenRelative = isScreenRelative;
        this.relativePosition = position.toVector();
    }

    /**
     * Alternative Text constructor
     * @param text (String): String of the text, not null
     * @param position (DiscreteCoordinates): Initial position in the grid, not null
     * @param area (Area): Area context of the text, not null
     * @param isScreenRelative (boolean): indicate if the text is relative to the world or the window
     * @param fontSize (float): the font size (i.e. 1 is one cell high)
     * @param color (Color): Color of the text, not null
     * @param bold (boolean): Is the text bold
     * @param italics (boolean): Is the text italics
     */
    public Text(String text, DiscreteCoordinates position, Area area, boolean isScreenRelative, float fontSize, Color color, boolean bold, boolean italics) {
        this(text, position, area, isScreenRelative, fontSize, color, bold, italics, TextAlign.Horizontal.LEFT, TextAlign.Vertical.BOTTOM, 1, 10);
    }

    /**
     * Alternative Text constructor
     * @param text (String): String of the text, not null
     * @param position (DiscreteCoordinates): Initial position in the grid, not null
     * @param area (Area): Area context of the text, not null
     * @param isScreenRelative (boolean): indicate if the text is relative to the world or the window
     * @param fontSize (float): the font size (i.e. 1 is one cell high)
     * @param color (Color): Color of the text, not null
     * @param alpha (float): transparency of the text (0 is full invisible and 1 is full opaque)
     * @param depth (float): the depth of the text, small depth draw first
     */
    public Text(String text, DiscreteCoordinates position, Area area, boolean isScreenRelative, float fontSize, Color color, float alpha, float depth) {
        this(text, position, area, isScreenRelative, fontSize, color, false, false, TextAlign.Horizontal.LEFT, TextAlign.Vertical.BOTTOM, alpha, depth);
    }

    /**
     * Alternative Text constructor
     * @param text (String): String of the text, not null
     * @param position (DiscreteCoordinates): Initial position in the grid, not null
     * @param isScreenRelative (boolean): indicate if the text is relative to the world or the window
     * @param area (Area): Area context of the text, not null
     * @param fontSize (float): the font size (i.e. 1 is one cell high)
     * @param color (Color): Color of the text, not null
     */
    public Text(String text, DiscreteCoordinates position, Area area, boolean isScreenRelative, float fontSize, Color color) {
        this(text, position, area, isScreenRelative, fontSize, color, false, false, TextAlign.Horizontal.LEFT, TextAlign.Vertical.BOTTOM, 1, 10);
    }

    /**
     * Update the text displayed
     * @param text (String) the new text, not null
     */
    public void setText(String text){
        textGraphics.setText(text);
    }

    /**
     * Make the text appear by step of given size
     * @param stepSize (float): the step size
     */
    public void makeItAppear(float stepSize){
        makeItDisappear = false;
        makeItAppear = true;
        appearStep = stepSize;
    }

    /**
     * Make the text disappear by step of given size
     * @param stepSize (float): the step size
     */
    public void makeItDisappear(float stepSize){
        makeItAppear = false;
        makeItDisappear = true;
        appearStep = stepSize;
    }


    /// Text implements Updatable

    @Override
    public void update(float deltaTime) {

        if(makeItAppear){
            currentAlpha = Math.min(1, currentAlpha+appearStep);
            textGraphics.setAlpha(currentAlpha);
            if(currentAlpha >= 1)
                makeItAppear = false;
        }
        else if(makeItDisappear){
            currentAlpha = Math.max(0, currentAlpha-appearStep);
            textGraphics.setAlpha(currentAlpha);
            if(currentAlpha <= 0)
                makeItDisappear = false;
        }

        super.update(deltaTime);
    }

    /// Text implements graphics

    @Override
    public void draw(Canvas canvas) {

        if(isScreenRelative) {
            setCurrentPosition(canvas.getPosition().sub(DX, DY).add(relativePosition));
        }

        textGraphics.draw(canvas);
    }
}
