import com.sun.javafx.stage.EmbeddedWindow;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the board.
 * @author Alex
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public class Board extends Application{
    private final Tile[][] tileMap;
    private final String[][] tempTileMap;
    private static ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Rat> rats = new ArrayList<>();
    private static Canvas canvas;
    private final int mapX;
    private final int mapY;
    private final int GAME_WIDTH = 1200;
    private final int GAME_HEIGHT = 780;
    private Level instance;

    /**
     * Constructor function for board
     * @param tiles tile map in 2D array
     //* @param items item map in 2D array
     //* @param rats male rat map in 2D array
     */
    public Board(String[][] tiles, /*String[][] items, String[][] rats,*/ int mapX, int mapY) {
        tempTileMap = tiles;
//        itemMap = items;
//        ratMap =  rats;
        this.mapX = mapX;
        this.mapY = mapY;
        tileMap = new Tile[this.mapX][this.mapY];
        instance = Level.getInstance();
    }

    public void start(Stage primaryStage) {
        Pane root = buildGUI();
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        drawBoard();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane buildGUI() {
        // Create top-level panel that will hold all GUI nodes.
        BorderPane root = new BorderPane();

        // Create the canvas that we will draw on.
        // We store this as a global variable so other methods can access it.
        canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.setCenter(canvas);

        return root;
    }

    public void drawBoard() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        // Set the background to black.
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Read in a 2d array of strings of tile types then turn it into a 2d array of Tile objects
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                tileMap[x][y] = new Tile(tempTileMap[x][y], x, y, canvas);
            }
        }

        for (Item it: items) {
            gc.drawImage(it.getImage(),it.getX() * 60,it.getY() * 60);
        }

        for (Rat rt: rats) {
            gc.drawImage(rt.getSprite(),1 * 60,4 * 60);
            gc.drawImage(rt.getSprite(),2 * 60,4 * 60);
        }




    }

    /**
     * Function to add an item to the itemMap
     * @param x x coordinate of the item
     * @param y y coordinate of the item
     * @param item the item object
     */
    public void addItemToMap(int x, int y, Item item) {
        items.add(item);
        this.drawBoard();

    }

//    /**
//     * Function to add a male rat to the mRatMap
//     * @param x x coordinate of the rat
//     * @param y y coordinate of the rat
//     * @param rat the rat object
//     */
//    public void addMRatToMap(int x, int y, Rat rat) {
//        ratMap[x][y] = rat;
//    }

    public void removeItemFromMap(int x, int y, Item item) {
        if(items.contains(item)){
            System.out.println("here");
            items.remove(item);
            drawBoard();
        }
    }

    public void removeRatFromMap() {

    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public void addRat(Rat rat) {
        rats.add(rat);
        //instance.reDrawBoard();
    }


//    public Item getItemMap() {
//        return itemMap;
//    }

//    public Rat[][] getRatMap() {
//        return ratMap;
//    }
}
