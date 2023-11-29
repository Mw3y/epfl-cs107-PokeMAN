package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.shape.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;


/**
 * Grid Overlay entity
 * Draw a grid on the DiscreteCoordinate Lines:
 *  Assume a coordinate system which is graduated every unit (0, 1, 2, ...)
 *  Assume a grid overlay with unit square
 */
public class Grid extends Entity {

    /// Whole grid lines
    private final Polyline gridLine;
    /// Border of the grid
    private final Polyline border;

    /**
     * Default Grid Constructor
     * @param width (int): of the desired grid
     * @param height (int): of the desired grid
     */
    public Grid(int width, int height){
        super(DiscreteCoordinates.ORIGIN.toVector());

        final List<Vector> points = new ArrayList<>();

        // Add all vertical lines as a snake from top left to top/bottom right
        for(int c = 1; c < width; c++) {
            points.add(new Vector(c, (c%2)*height));
            points.add(new Vector(c, ((c+1)%2)*height));
        }
        // Reach the right in the margin
        points.add(new Vector(width, (width%2)*height));

        // Add all horizontal lines as a snake
        for(int r = 1; r < height; r++) {
            points.add(new Vector((r%2)*width, r));
            points.add(new Vector(((r+1)%2)*width, r));
        }
        // Convert the point into a opened poly line
        gridLine = new Polyline(false, points);

        border = new Polyline(true, 0,0,0, height, width, height, width, 0);
    }

    public Grid(Area area) {
        this(area.getWidth(), area.getHeight());
    }

    /// Grid implements Graphics

    @Override
    public void draw(Canvas canvas) {
        canvas.drawShape(gridLine, getTransform(), null, java.awt.Color.GRAY, 0.05f, 0.5f, 10000);
        canvas.drawShape(border, getTransform(), null, java.awt.Color.GRAY, 0.05f, 1, 10000);
    }
}
