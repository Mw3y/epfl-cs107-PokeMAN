package ch.epfl.cs107.play.signal.wave;

/**
 * Implementation of a Square waveform
 */
public class Square extends Waveform{

    /**
     * Default Square Waveform constructor
     * @param lambda (float): wavelength
     * @param a (float): amplitude
     * @param phi (float): phase
     */
    public Square(float lambda, float a, float phi) {
        super( lambda, a, phi);
    }

    @Override
    public float getIntensity(float t) {
        if((t-phi) % lambda < 0.5){
            return a;
        }
        return -a;
    }
}