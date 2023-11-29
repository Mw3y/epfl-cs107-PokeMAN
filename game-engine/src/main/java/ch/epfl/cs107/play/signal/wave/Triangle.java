package ch.epfl.cs107.play.signal.wave;

/**
 * Implementation of a triangle waveform
 */
public class Triangle extends Waveform{

    /**
     * Default Triangle Waveform constructor
     * @param lambda (float): wavelength
     * @param a (float): amplitude
     * @param phi (float): phase
     */
    public Triangle(float lambda, float a, float phi) {
        super(lambda, a, phi);
    }

    @Override
    public float getIntensity(float t) {
        return (float)(2*a/Math.PI * Math.asin( Math.sin((2*Math.PI*t - phi)/lambda)));
    }
}