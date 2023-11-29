package ch.epfl.cs107.play.math;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DiscreteCoordinates assume a standard coordinate system.
 * Assume every unit the coordinate axis are graduated.
 * Now imagine a grid passing by all graduation. We get the set of all discrete coordinates:
 * For example if x, y in [0, 2] we get:
 * (0, 0), (1, 0), (2, 0), (0, 1) (0, 2), (1, 1), (1, 2), (2, 1), (2, 2)
 *
 * DiscreteCoordinate are special Vector that can be assimilated to Cells in a grid
 * in a way DiscreteCoordinate (1, 1) include all vector (x, y) with  x, y in [1; 2)
 */
public final class DiscreteCoordinates implements Serializable {

	private static final long serialVersionUID = 1;
    public static DiscreteCoordinates ORIGIN = new DiscreteCoordinates(0, 0);
    /// A coordinate pair is defined by the x and y graduation
    public final int x, y;

    /**
     * Default coordinate constructor
     * @param y (int): The row index
     * @param x (int): The column index
     */
    public DiscreteCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** @return (DiscreteCoordinates): one coordinate left */
    public DiscreteCoordinates left(){
        return new DiscreteCoordinates(x-1, y);
    }

    /** @return (DiscreteCoordinates): one coordinate right */
    public DiscreteCoordinates right(){
        return new DiscreteCoordinates(x+1, y);
    }

    /** @return (DiscreteCoordinates): one coordinate above */
    public DiscreteCoordinates up(){
        return new DiscreteCoordinates(x, y+1);
    }

    /** @return (DiscreteCoordinates): one coordinate below */
    public DiscreteCoordinates down(){
        return new DiscreteCoordinates(x, y-1);
    }

    /**
     * Return the neighbours coordinates
     * @return (float): the neighbours
     */
    public List<DiscreteCoordinates> getNeighbours(){
    	List<DiscreteCoordinates> result = new ArrayList<DiscreteCoordinates>();
    	result.add(left());
    	result.add(up());
    	result.add(right());
    	result.add(down());
    	return result;
    }
    
    /**
     * Make a jump to another cell
     * @param dx (int): the delta x
     * @param dy (int): the delta y
     * @return (DiscreteCoordinates): the Coordinates after the jump
     */
    public DiscreteCoordinates jump(int dx, int dy){
        return new DiscreteCoordinates(x+dx, y+dy);
    }

    /**
     * Make a jump to another cell
     * @param delta (Vector): to define the length and the direction of the jump. Not null
     * @return (DiscreteCoordinates): the Coordinates after the jump
     */
    public DiscreteCoordinates jump(Vector delta){
        return new DiscreteCoordinates(x+(int)delta.x, y+(int)delta.y);
    }


    /**@return (Vector): convert coordinates into continuous vector*/
    public Vector toVector(){
        return new Vector(x, y);
    }

    /**
     * Check weather a vector is close enough from any coordinate crossing
     * @param v (Vector): the vector to check. Not null
     * @return (boolean): true if the given vector is close enough from a coordinate
     */
    public static boolean isCoordinates(Vector v){
        return v.sub(v.round()).getLength() < Vector.EPSILON;
    }

    /**
     * Check weather a vector is close enough from a specific coordinate crossing
     * @param v (Vector): the vector to check. Not null
     * @param c (DiscreteCoordinates): the discrete coordinate to compare with
     * @return (boolean) : true if the given vector is close enough from the given coordinate
     */
    public static boolean isCoordinates(Vector v, DiscreteCoordinates c){
        return v.sub(c.x, c.y).getLength() < Vector.EPSILON;
    }


    /**
     * Return the euclidean Distance between two discrete coordinate
     * @param a (DiscreteCoordinates). Not null
     * @param b (DiscreteCoordinates). Not null
     * @return (float): the euclidean distance between the two coordinates
     */
    public static float distanceBetween(DiscreteCoordinates a, DiscreteCoordinates b){
        return (float) Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }
    
    /// Implements Serializable

    @Override
    public int hashCode() {
        return Integer.hashCode(y) ^ Integer.hashCode(x);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof DiscreteCoordinates))
            return false;
        else {
            DiscreteCoordinates other = (DiscreteCoordinates)object;
            return x == other.x && y == other.y;
        }
    }

    @Override
    public String toString() {
        return "Coor(x:"+ x +", y:"+ y +")";
    }
}
