package itemClasses;

/**
 * Small class for using Coordinates in GasItems.
 *
 * @author Trafford
 */
public class Coordinate {

    private int x;
    private int y;

    /**
     * A coordinate with two parameters
     *
     * @param x X coordinate in tile space
     * @param y Y coordinate in tile space
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * get the x position of this coordinate
     *
     * @return the y pos
     */
    public int getX() {
        return x;
    }

    /**
     * get the y position of this coordinate
     *
     * @return the x pos
     */
    public int getY() {
        return y;
    }

    /**
     * set the y position of this coordinate
     *
     * @param x the new x pos
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set the y position of this coordinate
     *
     * @param y the new y pos
     */
    public void setY(int y) {
        this.y = y;
    }
}
