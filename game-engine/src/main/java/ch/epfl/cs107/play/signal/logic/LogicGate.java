package ch.epfl.cs107.play.signal.logic;


/**
 * Implementation of LogicGate signal
 */
public abstract class LogicGate implements Logic {

    /**@return (boolean): true if the signal is considered as on*/
    public final boolean isOn(){
        return Math.abs(getIntensity() - 1.0f) < EPSILON;
    }

    /**@return (boolean): true if the signal is considered as off*/
    public final boolean isOff(){
        return Math.abs(getIntensity()) < EPSILON;
    }


    /// LogicGate implements Logic

    @Override
    public final float getIntensity(float t) {
        return getIntensity();
    }
}
