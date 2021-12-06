package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

/**
 * Creates a Game.Tile object and draws it on the map
 * @author Alex
 * @version 1.0
 */
public class Tile {
    private final String type;
    private final int xValue;
    private final int yValue;
    private final Boolean traversable;

    /**
     * Constructor for tile class
     * @param type String - type of tile
     * @param x int - x position
     * @param y int - y position
     * @param canvas canvas that will be drawn on
     */
    public Tile(String type, int x, int y, Canvas canvas) {
        this.type = type;
        this.xValue = x;
        this.yValue = y;
        traversable = Objects.equals(this.type, "p") || Objects.equals(this.type, "t");
        draw(canvas);
    }

    /**
     * draws the tile map on a canvas
     * @param canvas canvas object to be drawn on
     */
    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if(type != null){
            switch (type) {
                case "w" -> {
                    Image grassImage = new Image("sprites/metalTile.png");
                    gc.drawImage(grassImage, xValue * 60, yValue * 60);
                }
                case "p" -> {
                    Image pathImage = new Image("sprites/metalPathTile.png");
                    gc.drawImage(pathImage, xValue * 60, yValue * 60);
                }
                case "t" -> {
                    Image tunnelImage = new Image("sprites/drainTile.png");
                    gc.drawImage(tunnelImage, xValue * 60, yValue * 60);
                }
            }
        }
    }

    /**
     * Gets the location of a tile
     * @return array of x and y int values
     */
    public int[] getLocation() {
        return new int[]{xValue, yValue};
    }

    /**
     * Gets whether a tile is traversable
     * @return boolean of whether a tile is traversable
     */
    public Boolean getTraversable() {
        return traversable;
    }

    /**
     * Gets the tile type
     * @return string of tile stype
     */
    public String getTileType() {
        return type;
    }
}
