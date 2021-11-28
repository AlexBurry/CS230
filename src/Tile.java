import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

/**
 * Creates a Tile object and draws it on the map
 * @author Alex
 * @version 0.1
 * @since 0.1
 */
public class Tile {
    private final String type;
    private final int xValue;
    private final int yValue;
    private Boolean traversable;
    private final Boolean isRatVisible;

    public Tile(String type, int x, int y, Canvas canvas) {
        this.type = type;
        this.xValue = x;
        this.yValue = y;
        traversable = Objects.equals(this.type, "p") || Objects.equals(this.type, "t");
        isRatVisible = Objects.equals(this.type, "p");
        draw(x, y, canvas);
    }

    public void draw(int x, int y, Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        switch (type) {
            case "g":
                Image grassImage = new Image("grasstile.png");
                gc.drawImage(grassImage, x * 60, y * 60);
                break;
            case "p":
                Image pathImage = new Image("pathtile.png");
                gc.drawImage(pathImage, x * 60, y * 60);
                //TODO: Add path image
                break;
            case "t":
                Image tunnelImage = new Image("rat.png");
                gc.drawImage(tunnelImage, x * 60, y * 60);
                //TODO: Add tunnel image
                break;
        }
    }

    public int[] getLocation() {
        return new int[]{xValue, yValue};
    }

    public Boolean getTraversable() {
        return traversable;
    }

    public void setTraversable() {
        traversable = !traversable;
    }

    public Boolean getIsRatVisible() {
        return isRatVisible;
    }
}
