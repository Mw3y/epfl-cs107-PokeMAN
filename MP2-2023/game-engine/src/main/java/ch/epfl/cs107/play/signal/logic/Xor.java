package ch.epfl.cs107.play.signal.logic;

/**
 * Implementation of a Logical Xor gate
 */
public final class Xor extends LogicGate {
    /// Two signals
    private final Logic signal1, signal2;

    /**
     * Default constructor of logical Xor
     * @param signal1 (Logic): first signal. Not null
     * @param signal2 (Logic): second signal. Not null
     */
    public Xor(Logic signal1, Logic signal2) {
        this.signal1 = signal1;
        this.signal2 = signal2;
    }

    /// Xor  extends logicGate

    @Override
    public float getIntensity() {
        return Math.abs(signal1.getIntensity() - signal2.getIntensity());
    }
}