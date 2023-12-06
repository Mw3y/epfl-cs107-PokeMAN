package ch.epfl.cs107.icmon.area.maps;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Background;
import ch.epfl.cs107.play.engine.actor.Foreground;

public class House extends Area {

    public static final String TITLE = "house";

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public float getCameraScaleFactor() {
        return 0;
    }

    public void createArea() {
        registerActor(new Background(this));
        registerActor(new Foreground(this));
    }
}
