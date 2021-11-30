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
    private static ArrayList<Rat> rats = new ArrayList<>();
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
    public Board(String[][] tiles, /*ArrayList<String> items, ArrayList<String> rats,*/ int mapX, int mapY) {
        tempTileMap = tiles;
//        this.items = items;
//        this.rats =  rats;
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
        primaryStage.centerOnScreen();
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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Read in a 2d array of strings of tile types then turn it into a 2d array of Tile objects
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                tileMap[x][y] = new Tile(tempTileMap[x][y], x, y, canvas);
            }
        }


        for (Rat rt: rats) {
            gc.drawImage(rt.getSprite(),2 * 60,5 * 60);

        }

        for (Item it: items) {
            gc.drawImage(it.getImage(),it.getX() * 60,it.getY() * 60);
        }

    }
    

    /**
     * Function to add an item to the itemMap
     * @param item the item object
     */
    public void addItemToMap(Item item) {
        items.add(item);
        this.drawBoard();
    }

    /**
     * Function to add a male rat to the mRatMap
     * @param rat the rat object
     */
    public void addMRatToMap(Rat rat) {
        rats.add(rat);
        this.drawBoard();
    }

    public void removeItemFromMap(Item item) {
        if(items.contains(item)){
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
