package ch.epfl.cs107.play.signal.wave;

/**
 * Implementation of a Sine waveform
 */
public class Sine extends Waveform{

    /**
     * Default Sine Waveform constructor
     * @param lambda (float): wavelength
     * @param a (float): amplitude
     * @param phi (float): phase
     */
    public Sine(float lambda, float a, float phi) {
        super( lambda, a, phi);
    }

    @Override
    public float getIntensity(float t) {
        return (float)(a * Math.sin((2*Math.PI*t - phi) / lambda));
    }
}