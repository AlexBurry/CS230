import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Creates a Tile object and draws it on the map
 * @author Alex
 * @version 0.1
 * @since 0.1
 */


public class Tile {
    private char type;
    private int[][] location;
    private Boolean traversable;
    private Boolean isRatVisible;
    private Rectangle r;
    private static int width = 10;
    private static int height = 10;

    public Tile(char type) {
        this.type = type;
        traversable = this.type == 'P' || this.type == 'T';
        isRatVisible = this.type == 'P';
        this.r = r;
        this.r.setWidth(width);
        this.r.setHeight(height);
    }

    public void tempDraw() {
        System.out.print(type);
    }



    public void draw(int[][] location) {
        switch (type) {
            case 'G':
                r.setFill(Color.GREEN);
                r.setStroke(Color.DARKGRAY);
                break;
            case 'P':
                r.setFill(Color.GRAY);
                r.setStroke(Color.DARKGRAY);
                break;
            case 'T':
                r.setFill(Color.BROWN);
                r.setStroke(Color.DARKGRAY);
        }
    }

    public int[][] getLocation() {
        return location;
    }

    public Boolean getIsTraversable() {
        return traversable;
    }

//    public Boolean setIsTraversable() {
//
//    }

    public Boolean getIsRatVisible() {
        return isRatVisible;
    }
}
