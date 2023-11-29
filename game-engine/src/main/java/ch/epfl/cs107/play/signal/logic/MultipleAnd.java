package ch.epfl.cs107.play.signal.logic;

/**
 * Implementation of a Logical And gate
 */
public final class MultipleAnd extends LogicGate {

    /// Multiple signals
    private final Logic[] signals;

    /**
     * Default constructor of logical And with more than two input
     * @param signals (Array of Logic): all signals. Not null
     */
    public MultipleAnd(Logic... signals) {
        this.signals = signals;
    }


    /// Multiple And extends logicGate

    @Override
    public float getIntensity() {
        for(Logic logic : signals){
            if(logic.isOff()) return Logic.FALSE.getIntensity();
        }
        return Logic.TRUE.getIntensity();
    }
}