package ch.epfl.cs107.play.signal.wave;

import ch.epfl.cs107.play.signal.Signal;


/**
 * Implementation of Waveform signal defined by wavelength (lambda), amplitude (a) and phase (phi)
 */
abstract class Waveform implements Signal{

    /// Wavelength, amplitude and phase
    final float lambda, a, phi;

    /**
     * Default Waveform constructor
     * @param lambda (float): wavelength
     * @param a (float): amplitude
     * @param phi (float): phase
     */
    Waveform(float lambda, float a, float phi) {
        this.lambda = lambda;
        this.a = a;
        this.phi = phi;
    }
}
