package Game;

import GUI.DragAndDrop;
import ItemClasses.BombItem;
import ItemClasses.Item;
import RatClasses.Rat;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * Represents the board.
 * @author Alex
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public class Board extends Application{
    private final String[][] tempTileMap;
    private final Tile[][] tileMap;
    private ArrayList<Tile> traversableTiles = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Rat> rats = new ArrayList<>();
    private final int mapX;
    private final int mapY;
    private final int GAME_WIDTH = 1200;
    private final int GAME_HEIGHT = 854;
    private Level instance;
    private final Canvas canvas= new Canvas(GAME_WIDTH, GAME_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    /**
     * Constructor function for board
     * @param tiles tile map in 2D array
     * @param rats rats in an Array List
     */
    public Board(String[][] tiles, ArrayList<String> rats, int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        tileMap = new Tile[this.mapX][this.mapY];
        tempTileMap = tiles;
        for (String rt : rats) {
            this.rats.add(new Rat(rt.charAt(0),Character.getNumericValue(rt.charAt(2)),
                    Character.getNumericValue(rt.charAt(4))));
        }
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
        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        DragAndDrop toolBar = new DragAndDrop(canvas, tileMap);
        root.setTop(toolBar.makeToolBar());

        root.setCenter(canvas);

        return root;
    }

    public void drawBoard() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Read in a 2d array of strings of tile types then turn it into a 2d array of Game.Tile objects
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                tileMap[x][y] = new Tile(tempTileMap[x][y], x, y, canvas);
                if(tileMap[x][y].getTraversable()){
                    traversableTiles.add(tileMap[x][y]);
                }
            }
        }

        System.out.println("Drawing a rat");
        drawRats();
        drawItems();
    }

    public void drawRats() {
        for (Rat rt: rats) {
            gc.drawImage(rt.getSprite(),rt.getX()*60,rt.getY()*60);
        }
    }

    public void drawItems() {
        for (Item it: items) {
            gc.drawImage(it.getImage(),it.getX() * 60,it.getY() * 60);
        }

    }

    /**
     * Function to add an item to the item list and board
     * @param item the item object
     */
    public void addItem(Item item) {

        if (item.getClass() == BombItem.class) {

            instance.addListener((Tick) item);
        }
        items.add(item);
        this.drawItems();
    }

    public void removeItem(Item item) {
        if(items.contains(item)){
            items.remove(item);
            drawBoard();
        }
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public ArrayList<Tile> getTraversableTiles(){
        return traversableTiles;
    }

    public void redrawTile(int x, int y, boolean redrawItems) {
        Tile tile = tileMap[x][y];
        tile.draw(canvas);

        if(redrawItems){
            drawItems();
        }

    }

    public void addRat(Rat rat) {
        rats.add(rat);
    }

    public void removeRat(Rat rat) {
        if(rats.contains(rat)){
            rats.remove(rat);
            drawBoard();
        }
    }

    public ArrayList<Rat> getRats() {
        return rats;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

}
