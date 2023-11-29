package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


/**
 * GraphicEntity useful to link a single Specific Graphic type to a space point
 */
public class GraphicsEntity extends Entity{

    private final Graphics graphics;

    /**
     * Default GraphicsEntity constructor
     * Notice: it is private
     * @param position (Vector): Initial position of the entity. Not null
     * @param graphics (Graphics): graphics to display at position. Not null
     */
    private GraphicsEntity(Vector position, Graphics graphics) {
        super(position);
        this.graphics = graphics;
    }

    /**
     * Alternative GraphicsEntity Constructor
     * @param position (Vector): initial Position. Not null
     * @param graphics (ImageGraphics): graphics to display at position. Not null
     */
    public GraphicsEntity(Vector position, ImageGraphics graphics) {
        this(position, (Graphics) graphics);
        graphics.setParent(this);
    }

    /**
     * Alternative GraphicsEntity Constructor
     * @param position (Vector): initial Position. Not null
     * @param graphics (TextGraphics): graphics to display at position. Not null
     */
    public GraphicsEntity(Vector position, TextGraphics graphics) {
        this(position, (Graphics) graphics);
        graphics.setParent(this);
    }

    /**
     * Alternative GraphicsEntity Constructor
     * @param position (Vector): initial Position. Not null
     * @param graphics (ShapeGraphics): graphics to display at position. Not null
     */
    public GraphicsEntity(Vector position, ShapeGraphics graphics) {
        this(position, (Graphics) graphics);
        graphics.setParent(this);
    }


    /** @return (Graphics): The graphics of the entity*/
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * Set the current position. Make public the protected method of super class
     * @param position (Vector): new Position. Not null
     */
    public void setCurrentPosition(Vector position){
        super.setCurrentPosition(position);
    }

    /// GraphicsEntity implements drawable

    @Override
    public void draw(Canvas canvas) {
        if(graphics!= null) {
            graphics.draw(canvas);
        }
    }
}
