package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

/**
 * Creates a Game.Tile object and draws it on the map
 * @author Alex
 * @version 0.1
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
        draw(canvas);
    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(type != null){
            switch (type) {
                case "w":
                    Image grassImage = new Image("sprites/metalTile.png");
                    gc.drawImage(grassImage, xValue * 60, yValue * 60);
                    break;
                case "p":
                    Image pathImage = new Image("sprites/metalPathTile.png");
                    gc.drawImage(pathImage, xValue * 60, yValue * 60);
                    break;
                case "t":
                    Image tunnelImage = new Image("sprites/drainTile.png");
                    gc.drawImage(tunnelImage, xValue * 60, yValue * 60);

                    break;
            }
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

    public String getTileType() {
        return type;
    }
}
