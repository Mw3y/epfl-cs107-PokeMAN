package ch.epfl.cs107.icmon.actor;

import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public class NPCActor extends ICMonActor {

    private final RPGSprite sprite;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public NPCActor(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
        super(area, orientation, position);
        sprite = new RPGSprite(spriteName, 1, 1.3125f, this, new RegionOfInterest(0, 0, 16, 21));
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}
