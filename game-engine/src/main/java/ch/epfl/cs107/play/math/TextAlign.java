package ch.epfl.cs107.play.math;

public abstract class TextAlign {

    public enum Horizontal{
        /// The anchor point is aligned with the left bound of the text
        LEFT,
        /// The anchor point is in the center of the text
        CENTER,
        /// The anchor point is aligned with the right bound of the text
        RIGHT;
    }

    public enum Vertical{
        /// The anchor point is on top of the text
        TOP,
        /// The anchor point is in the middle of the text
        MIDDLE,
        /// The anchor point is at the bottom of the text
        BOTTOM;
    }
}
