package ch.epfl.cs107.play.signal.logic;


/**
 * Implementation of a Logical Or gate
 */
public final class Or extends LogicGate {

    /// Two signals
    private final Logic signal1, signal2;

    /**
     * Default constructor of logical Or
     * @param signal1 (Logic): first signal. Not null
     * @param signal2 (Logic): second signal. Not null
     */
    public Or(Logic signal1, Logic signal2) {
        this.signal1 = signal1;
        this.signal2 = signal2;
    }

    /// Or  extends logicGate

    @Override
    public float getIntensity() {
        return Math.max(signal1.getIntensity(), signal2.getIntensity());
    }
}
