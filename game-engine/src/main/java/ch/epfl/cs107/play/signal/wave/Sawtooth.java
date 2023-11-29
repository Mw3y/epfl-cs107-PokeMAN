package ch.epfl.cs107.play.signal.wave;

/**
 * Implementation of a Sawtooth waveform
 */
public class Sawtooth extends Waveform{

    /**
     * Default Sawtooth Waveform constructor
     * @param lambda (float): wavelength
     * @param a (float): amplitude
     * @param phi (float): phase
     */
    public Sawtooth(float lambda, float a, float phi) {
        super(lambda, a, phi);
    }

    @Override
    public float getIntensity(float t) {
        return (float)(2*a/Math.PI * Math.atan( Math.tan((2*Math.PI*t - phi)/(2*lambda))));
    }
}