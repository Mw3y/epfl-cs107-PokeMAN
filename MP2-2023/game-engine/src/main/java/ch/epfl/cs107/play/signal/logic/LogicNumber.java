package ch.epfl.cs107.play.signal.logic;

/**
 * Implementation of a Logical Number. The signal is true if the given signals interpreted as binary digits
 * equals the number
 */
public final class LogicNumber extends LogicGate {

    /// Power of two from 2 pwer 0 to 2 power 12
    private final static int[] POWERS = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096};
    /// Number the signals must equal to return true
    private final int number;
    private final Logic[] signals;

    /**
     * Default constructor of logical Number
     * @param value (int): value we want to represent with the signals
     * @param signals (Array of Logic): all digits signals, smallest power of two first. Not null
     */
    public LogicNumber(int value, Logic... signals) {

        if(value >= 8196 || signals.length > 13)
            System.out.println("This Logic gate is not adapted to use 2^p for p>12");
        if(value < 0 || value >= Math.pow(2, signals.length))
            System.out.println("It would be impossible to represent given value with this number of signals");

        this.number = value;
        this.signals = signals;
    }

    /// LogicNumber extends logicGate

    @Override
    public float getIntensity() {
        int currentValue = 0;
        for(int p = 0; p < signals.length; p++){
            if(signals[p].isOn()) currentValue += POWERS[p];
        }
        return (currentValue == number) ? Logic.TRUE.getIntensity() : Logic.FALSE.getIntensity();
    }
}