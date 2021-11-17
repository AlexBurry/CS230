
/**
 * Creates a Tile object and draws it on the map
 * @author Alex
 * @version 0.1
 * @since 0.1
 */
public class Tile {
    private char type;
    private int[][] location;
    private Boolean isTraversable;
    private Boolean isRatVisible;

    public Tile(char type) {
        this.type = type;
        if (this.type == 'P' || this.type == 'T') {
            isTraversable = true;
        } else {
            isTraversable = false;
        }
        if (this.type == 'P') {
            isRatVisible = true;
        } else {
            isRatVisible = false;
        }
    }

    public void draw(int[][] location) {
        //TODO: implement draw method
    }

    public int[][] getLocation() {
        return location;
    }

    public Boolean getIsTraversable() {
        return isTraversable;
    }

    public Boolean getIsRatVisible() {
        return isRatVisible;
    }
}