package ch.epfl.cs107.play.window;

import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;

/**
 * Represents the mouse pointer.
 */
public interface Mouse extends Positionable {

    /**
     * Getter for the button corresponding to the given index
     * @param index (int): given index
     * @return (Button): button corresponding of the state of the given index button
     */
    Button getButton(int index);

    /**@return (Button): the left button (by default index = 0)*/
    default Button getLeftButton() {
        return getButton(0);
    }

    /**@return (Button): the Middle button (by default index = 1)*/
    default Button getMiddleButton() {
        return getButton(1);
    }

    /**@return (Button): the right button (by default index = 2)*/
    default Button getRightButton() {
        return getButton(2);
    }
    
    // TODO wheel/scroll
    // TODO other position getters and screen space things?


    /// Mouse extends Positionable

    @Override
    default Transform getTransform() {
        Vector position = getPosition();
        return new Transform(1.0f, 0.0f, position.x, 0.0f, 1.0f, position.y);
    }
}
