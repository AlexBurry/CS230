import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Objects;

/**
 * Creates a Tile object and draws it on the map
 * @author Alex
 * @version 0.1
 * @since 0.1
 */


public class Tile {
    private String type;
    private int[][] location;
    private Boolean traversable;
    private Boolean isRatVisible;
    //private Rectangle r;
    private static int width = 40;
    private static int height = 40;

    public Tile(String type, int x, int y, Canvas canvas) {
        this.type = type;
        traversable = Objects.equals(this.type, "p") || Objects.equals(this.type, "t");
        isRatVisible = Objects.equals(this.type, "p");
        draw(x, y, canvas);
    }

    public void draw(int x, int y, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Rectangle r = new Rectangle(40, 40, 40, 40);
        switch (type) {
            case "g":
//                r.setFill(Color.GREEN);
//                r.setStroke(Color.DARKGRAY);
                Image tileImage = new Image("grasstile.png");
                gc.drawImage(tileImage, x, y);
                break;
            case "p":
                r.setFill(Color.GRAY);
                r.setStroke(Color.DARKGRAY);
                break;
            case "t":
                r.setFill(Color.BROWN);
                r.setStroke(Color.DARKGRAY);
        }
    }

    public int[][] getLocation() {
        return location;
    }

    public Boolean getTraversable() {
        return traversable;
    }

//    public Boolean setIsTraversable() {
//
//    }

    public Boolean getIsRatVisible() {
        return isRatVisible;
    }
}
