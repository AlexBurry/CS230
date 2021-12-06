package itemClasses;

/**
 * Small class for using Coordinates in GasItems.
 * @author Trafford
 * @version
 */
public class Coordinate {

    private int x;
    private int y;

    /**
     * A coordinate with two parameters
     * @param x X coordinate in tile space
     * @param y Y coordinate in tile space
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
}
