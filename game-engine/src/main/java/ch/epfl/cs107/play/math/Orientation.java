package ch.epfl.cs107.play.math;


public enum Orientation {

    /// Enumeration elements
    UP (new Vector(0.0f, 1.0f)),
    RIGHT ( new Vector(1.0f, 0.0f)),
    DOWN (new Vector(0.0f, -1.0f)),
    LEFT (new Vector(-1.0f, 0.0f));


    /// Direction of the Orientation
    private final Vector direction;

    /**
     * Default Orientation constructor
     * @param direction (Vector). Not null
     */
    Orientation(Vector direction){
        this.direction = direction;
    }

    /**
     * Return the opposite Orientation
     * @return (Orientation): the opposite orientation Down:Up, Right:Left
     */
    public Orientation opposite(){
        return Orientation.values()[(ordinal()+2)%4];
    }

    /** @return (Orientation): the orientation on the left of this*/
    public Orientation hisLeft(){
        // Be careful, % return the reminder and not the modulus i.e. could be negative
        // It is why we do this trick +4)%4
        return Orientation.values()[(((ordinal()-1)%4)+4)%4];
    }

    /** @return (Orientation): the orientation on the right of this*/
    public Orientation hisRight(){
        return Orientation.values()[(ordinal()+1)%4];
    }

    /**
     * Convert an orientation into vector
     * @return (Vector)
     */
    public Vector toVector(){
        return direction;
    }

    /**
     * Convert an int into an orientation
     * @param index the integer representation
     * @return the orientation
     */
    public static Orientation fromInt(int index) {
		switch(index) {
			case 0: return Orientation.UP;
			case 1: return Orientation.RIGHT;
			case 2: return Orientation.DOWN;
			case 3: return Orientation.LEFT;
		}
		return null;
	}
    
    /**
     * Convert a vector into an orientation
     * @param v the vector representation
     * @return the orientation
     */
    public static Orientation fromVector(Vector v) {
    	if(v.x > 0 && v.y == 0)
    		return Orientation.RIGHT;
    	if(v.x < 0 && v.y == 0)
    		return Orientation.LEFT;
    	if(v.y > 0 && v.x == 0)
    		return Orientation.UP;
    	if(v.y < 0 && v.x == 0)
    		return Orientation.DOWN;
		return null;
	}

    @Override
    public String toString(){
        return super.toString()+direction.toString();
    }
}
