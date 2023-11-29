package ch.epfl.cs107.play.signal.logic;


/**
 * Implementation of a Logical Nand gate
 */
public final class Nand extends LogicGate {
    /// Two signals
    private final Logic signal1, signal2;

    /**
     * Default constructor of logical Nand
     * @param signal1 (Logic): first signal. Not null
     * @param signal2 (Logic): second signal. Not null
     */
    public Nand(Logic signal1, Logic signal2) {
        this.signal1 = signal1;
        this.signal2 = signal2;
    }

    /// Nand  extends logicGate

    @Override
    public float getIntensity() {
        return signal1.getIntensity() + signal2.getIntensity() == 2.0f ? 0.0f : 1.0f;
    }
}