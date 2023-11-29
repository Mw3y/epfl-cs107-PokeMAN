package ch.epfl.cs107.play.signal.logic;


/**
 * Implementation of a Logical Not gate
 */
public final class  Not extends LogicGate {

    /// signal to revert
    private final Logic signal;

    /**
     * Default constructor of logical not
     * @param signal (Logic): signal to revert. Not null
     */
    public Not(Logic signal) {
        this.signal = signal;
    }

    /// Not  extends logicGate

    @Override
    public float getIntensity() {
        return 1.0f - signal.getIntensity();
    }
}