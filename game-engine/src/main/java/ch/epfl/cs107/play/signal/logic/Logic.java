package ch.epfl.cs107.play.signal.logic;

import ch.epfl.cs107.play.signal.Signal;

/**
 * Extension of signals to Logic signals
 * The signal may be TRUE or FALSE
 */
public interface Logic extends Signal {

    Logic TRUE = new Logic() {
        @Override
        public boolean isOn() {
            return true;
        }

        @Override
        public boolean isOff() {
            return false;
        }

        @Override
        public float getIntensity() {
            return 1.0f;
        }
    };
    Logic FALSE = new Logic() {
        @Override
        public boolean isOn() {
            return false;
        }

        @Override
        public boolean isOff() {
            return true;
        }

        @Override
        public float getIntensity() {
            return 0.0f;
        }
    };


    /**@return (boolean): true if the signal is considered as on*/
    boolean isOn();

    /**@return (boolean): true if the signal is considered as off*/
    boolean isOff();

    /**@return (float) : the signal intensity, usually 0.0 or 1.0*/
    float getIntensity();

    @Override
    default float getIntensity(float t) {
        return getIntensity();
    }
}
