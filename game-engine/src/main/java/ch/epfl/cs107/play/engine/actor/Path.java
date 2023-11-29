package ch.epfl.cs107.play.engine.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import ch.epfl.cs107.play.engine.actor.Entity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.shape.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;


/**
 * Path Overlay entity
 * Draw a path on the DiscreteCoordinate Lines:
 */
public class Path extends Entity {

    private final Polyline pathLine;

    /**
     * Default Path Constructor
     * @param start (Vector): the origin of the path
     * @param path (Queue): the successive orientation of the path
     */
    public Path(Vector start, Queue<Orientation> path){
        super(DiscreteCoordinates.ORIGIN.toVector());

        final List<Vector> points = new ArrayList<>();

        Vector prevPoint = start.add(new Vector(0.5f, 0.5f));
        points.add(prevPoint);
        
        while(!path.isEmpty()) {
        	Vector newPoint = prevPoint.add(path.poll().toVector());
        	points.add(newPoint);
        	prevPoint = newPoint;
        }
        
        // Convert the point into a opened poly line
        if(points.size() >= 2)
        	pathLine = new Polyline(points);
        else
        	pathLine = null;
    }

    /// Path implements Graphics

    @Override
    public void draw(Canvas canvas) {
    	if(pathLine != null)
    		canvas.drawShape(pathLine, getTransform(), null, java.awt.Color.RED, 0.2f, 1f, 10000);
    }
}
