package ch.epfl.cs107.play.window.swing;

import javax.sound.sampled.*;

/**
 * Play a single sound
 * https://bugs.openjdk.java.net/browse/JDK-8077019
 */
public final class SoundItem implements LineListener {

    private final static float FADE_STEPS = 0.005f;

    /// Sound to play
    private final SwingSound sound;
    /// Clip ("Stream") corresponding to the sound
    private Clip audioClip;
    /// Offset on start
    private int offset;
    /// Indication if the clip must loop on self-ending
    private final boolean loop;
    /// currentVolume float value in percent : 0.0f no currentVolume, 1.0f full currentVolume
    private final float maxVolume;
    private float currentVolume, destinationVolume;
    /// Indicate if the clip is finish
    private boolean finish;

    /**
     * Default Sound Item constructor
     * @param loop (boolean): indicate if the item restart on self-ending
     * @param volume (float): 0.0f no sound, 1.0f full audio
     * @param fadeIn (boolean): indicate if the song fade in until reaching its max volume
     * @param randomFirstStart (boolean): indicate if the first start is random in the sound
     * @param sound (SwingSound): the given sound to play. Not null
     */
    public SoundItem(boolean loop, float volume, boolean fadeIn, boolean randomFirstStart, SwingSound sound) {
        this.loop = loop;
        this.maxVolume = volume;
        this.currentVolume = fadeIn ? 0.0f : volume;
        this.destinationVolume = maxVolume;
        this.sound = sound;
        this.finish = false;
        this.offset = randomFirstStart ? sound.randomOffSet() : 0;
    }

    /** Start the sound by asking a clip and starting it */
    public void start(){
        audioClip = sound.openedClip(offset);
        offset = 0; // The random offset is only for the first start
        if(audioClip != null) {
            audioClip.addLineListener(this);
            applyVolume();
            audioClip.start();
            finish = false;
        }
    }

    /** Apply gain currentVolume modification before starting a clip
     * - Notice the MASTER_GAIN FloatControl value is in decibels,
     * meaning it's a logarithmic scale, not a linear one. */
    private void applyVolume(){
        // TODO the FloatControl may be problematic following the JDK version: figure it out exactly
        // seems to work with OracleJDK version and not always with OpenJDK
        if(audioClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(currentVolume));
        }
    }

    /** Update the volume using the fade step concept
     * Notice: we finish the song if the faded out terminated*/
    private void updateVolume(){
        if(currentVolume < destinationVolume){
            currentVolume = Math.min(destinationVolume, currentVolume+FADE_STEPS);
            applyVolume();
        }else if(currentVolume > destinationVolume){
            currentVolume = Math.max(destinationVolume, currentVolume-FADE_STEPS);
            applyVolume();
        }
        // End the song if finish faded out
        if(currentVolume == 0.0f){
           finish();
        }
    }

    /** Can be called to fade in this item*/
    public void fadeIn(){
        destinationVolume = maxVolume;
    }

    /** Can be called to fade out this item*/
    public void fadeOut(){
        destinationVolume = 0.0f;
    }

    /** Finish the clip. Break the loop if any : force the end of the clip */
    public void finish(){
        finish = true;
        audioClip.stop();
    }

    /**@return (boolean): true if this item is finished */
    public boolean isFinish() {
        updateVolume();
        return finish;
    }

    /// SoundItem implements LineListener

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            // On self ending : if loop : stop and restart the clip
            if (loop && !finish) {
                audioClip.close();
                start();

            // On self ending : if no loop : finish the clip
            }else if(!finish){
                finish = true;
                audioClip.close();
            }
            // On forced ending
            else{
                audioClip.flush();
                audioClip.close();
            }
        }
    }
}
