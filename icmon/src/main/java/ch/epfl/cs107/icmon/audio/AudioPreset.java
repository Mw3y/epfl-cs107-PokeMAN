package ch.epfl.cs107.icmon.audio;

public enum AudioPreset {

    SFX(.4f, false, false, false, false),
    BG_MUSIC(.1f, true, false, true, false),
    FIGHT_MUSIC(.2f, false, false, true, false);

    private final float volume;
    private final boolean fadeIn;
    private final boolean stopOthersOnStart;
    private final boolean loop;
    private final boolean randomFirstStart;

    AudioPreset(float volume, boolean fadeIn, boolean stopOthersOnStart, boolean loop, boolean randomFirstStart) {
        this.volume = volume;
        this.fadeIn = fadeIn;
        this.stopOthersOnStart = stopOthersOnStart;
        this.loop = loop;
        this.randomFirstStart = randomFirstStart;
    }

    public float getVolume() {
        return volume;
    }

    public boolean fadesIn() {
        return fadeIn;
    }

    public boolean stopsOthersOnStart() {
        return stopOthersOnStart;
    }

    public boolean loops() {
        return loop;
    }

    public boolean hasRandomFirstStart() {
        return randomFirstStart;
    }
}
