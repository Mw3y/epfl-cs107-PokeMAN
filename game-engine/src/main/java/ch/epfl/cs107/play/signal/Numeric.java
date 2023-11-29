package ch.epfl.cs107.play.signal;

/**
 * Implementation of numeric signal
 * The signal has a constant integer numerical value
 */
public final class Numeric implements Signal{

    /// Integer numerical value
    private final int value;

    /**
     * Default Numeric Signal constructor
     * @param value (int): constant value of this signal
     */
    public Numeric(int value){
        this.value = value;
    }


    /// Numeric implements Signal

    @Override
    public float getIntensity(float t) {
        return value;
    }
}
