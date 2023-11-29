package ch.epfl.cs107.play.math;

import java.io.Serializable;


/**
 * Represents an immutable RegionOfInterest (RoI) in Image.
 * A RegionOfInterest is a rectangle defined by its top left corner and either dimension (width and height) or bottom right corner
 */
public final class RegionOfInterest implements Serializable{
	private static final long serialVersionUID = 1;

    /// Top left corner coordinates, width and height of the RoI
    public int x, y, w, h;

    /**
     * Create A new RegionOfInterest
     * @param x (int) top left pixel x coordinate in the image coordinate system
     * @param y (int) top left pixel y coordinate in the image coordinate system
     * @param w (int) width in number of pixel
     * @param h (int) height in number of pixel
     */
    public RegionOfInterest(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /** @return (int): top left pixel x coordinate*/
    public int x1(){
        return x;
    }
    /** @return (int): bottom right pixel x coordinate*/
    public int x2(){
        return x + w - 1;
    }
    /** @return (int): top left pixel y coordinate */
    public int y1(){
        return y;
    }
    /** @return (int): bottom right y coordinate*/
    public int y2(){
        return y + h - 1;
    }
    /** @return (int): the number of pixel contained inside the RoI*/
    public int pixelsNumber(){
        return w*h;
    }

    /**
     * Region of interest builder using top left and bottom right corner position
     * @param x1 (int): top left x-coordinate
     * @param y1 (int): top left y-coordinate
     * @param x2 (int): bottom-right x-coordinate
     * @param y2 (int): bottom-right y-coordinate
     * @return (RegionOfInterest): build a RegionOfInterest using the bottom right corner instead of the dimensions
     */
    public RegionOfInterest buildAltRegion(int x1, int y1, int x2, int y2){
        return new RegionOfInterest(x1, y1, x2-x1+1, y2-y1+1);
    }

    // Implement serializable

    @Override
    public int hashCode() {
        return (Float.hashCode(x) + Float.hashCode(y)) ^ (Float.hashCode(w) + Float.hashCode(h));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof RegionOfInterest))
            return false;
        RegionOfInterest other = (RegionOfInterest) object;
        return x == other.x && y == other.y && h==other.h && w == other.w;
    }

    @Override
    public String toString() {
        return "RoI(x:" + x + ", y:" + y + ", w:"+w+", h:"+h+")";
    }
}
