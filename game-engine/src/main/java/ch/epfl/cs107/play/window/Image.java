package ch.epfl.cs107.play.window;

/**
 * Context-agnostic immutable image.
 */
public interface Image {

    /** @return (int): width, in pixels */
    int getWidth();
    
    /** @return (int): height, in pixels */
    int getHeight();

    /**
     * Color getter of the pixel at the given row and column
     * @param r (int): given row
     * @param c (int): given column
     * @return (int): RGB color of the pixel at row r and column c as a int value
     */
    int getRGB(int r, int c);

}
