package ch.epfl.cs107.icmon.actor.npc;

import ch.epfl.cs107.icmon.actor.ICMonActor;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.engine.actor.Dialog;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.window.Canvas;

public abstract class NPCActor extends ICMonActor {

    private final RPGSprite sprite;

    /**
     * Represents an NPC actor of the game.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     * @param spriteName  (String): The name of the sprite to display
     */
    public NPCActor(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
       this(area, orientation, position, spriteName, new RegionOfInterest(0, 0, 16, 21));
    }

    /**
     * Represents an NPC actor of the game with a custom region of interest.
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     * @param spriteName  (String): The name of the sprite to display
     * @param regionOfInterest (RegionOfInterest): The region of interest of the image
     */
    // TODO: Conception
    public NPCActor(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName, RegionOfInterest regionOfInterest) {
        super(area, orientation, position);
        sprite = new RPGSprite(spriteName, 1, 1.3125f, this, regionOfInterest);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        assert canvas != null;
        sprite.draw(canvas);
    }
}
