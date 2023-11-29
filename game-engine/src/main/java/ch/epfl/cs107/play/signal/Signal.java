package ch.epfl.cs107.play.signal;

public interface Signal {

    /** Epsilon value to compare floating value*/
    float EPSILON = 1E-5f;

    /**
     * Intensity getter for given time
     * @param t (float): the time at which we want the intensity
     * @return (float): signal intensity, usually between 0.0 and 1.0
     */
    float getIntensity(float t);

    /**
     * Check if this signal and other given signal are equivalent for given time t
     * @param other (Signal): given other signal. Not null
     * @param t (float): given time
     * @return (boolean) : true if the signal are equivalent
     */
    default boolean is(Signal other, float t){
        return Math.abs(this.getIntensity(t) - other.getIntensity(t)) < EPSILON;
    }
}
