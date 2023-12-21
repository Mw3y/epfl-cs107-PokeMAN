package ch.epfl.cs107.icmon.actor.items;

import ch.epfl.cs107.icmon.handler.ICMonInteractionVisitor;
import ch.epfl.cs107.play.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.engine.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.window.Canvas;

public abstract class ICMonItem extends CollectableAreaEntity {
    private final RPGSprite sprite;

    /**
     * Represents an item of the ICMon game.
     *
     * @param area (Area) : owner area. Not null
     * @param orientation (Orientation) : Initial orientation of the item in the Area. Not null
     * @param position (DiscreteCoordinate): Initial position of the item in the Area. Not null
     * @param spriteName (String) : sprite of the item. Not null
     */
    public ICMonItem(Area area, Orientation orientation, DiscreteCoordinates position, String spriteName) {
        super(area, orientation, position);
        sprite = new RPGSprite(spriteName, 1f, 1f, this);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }


    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        assert canvas != null;
        sprite.draw(canvas);
    }
}
