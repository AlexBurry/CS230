package game;

import gui.DragAndDrop;
import itemClasses.*;
import ratClasses.BabyRat;
import ratClasses.Rat;
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
 *
 * @author Alex
 * @author Trafford
 * @version 0.1
 */
public class Board extends Application implements ITickHandler {

    private final int mapX;
    private final int mapY;
    private final int GAME_WIDTH = 1200;
    private final int GAME_HEIGHT = 884;
    private final int TILE_SIZE_PX = 60;
    private final String[][] tempTileMap;
    private final Tile[][] tileMap;
    private ArrayList<Tile> traversableTiles = new ArrayList<>();
    private ArrayList<Tile> tunnelTiles = new ArrayList<>(); //used to make sure tunnels remain ontop
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Rat> rats = new ArrayList<>();


    private Level instance;
    private final Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private DragAndDrop toolBar;
    private BorderPane root;

    /**
     * Constructor function for board
     *
     * @param tiles tile map in 2D array
     * @param rats  rats in an Array List
     */
    public Board(String[][] tiles, ArrayList<String> rats, int mapX, int mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
        tileMap = new Tile[this.mapX][this.mapY];
        tempTileMap = tiles;
        instance = Level.getInstance();
        instance.addListener(this);
        for (String rt : rats) {
            String[] values = rt.split(",");
            Rat newRat;
            if (Character.isLowerCase(values[0].charAt(0))) {
                newRat = new BabyRat(values[0].charAt(0), Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]));
            } else {
                newRat = new Rat(values[0].charAt(0), Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]));
            }
            this.rats.add(newRat);
            instance.addListener(newRat);
        }


    }

    /**
     * Starts the javaFX and begins drawing the board and scene
     *
     * @param primaryStage primary stage
     */
    public void start(Stage primaryStage) {
        Pane root = buildGUI();
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        drawBoard();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Builds the basic gui pane
     *
     * @return BorderPane object
     */
    private Pane buildGUI() {
        root = new BorderPane();
        root.setCenter(canvas);

        toolBar = new DragAndDrop(canvas, tileMap);
        root.setTop(toolBar.makeToolBar());


        return root;
    }

    /**
     * Draws the entire game board
     */
    public void drawBoard() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Read in a 2d array of strings of tile types then turn it into a 2d array of Tile objects
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                tileMap[x][y] = new Tile(tempTileMap[x][y], x, y, canvas);
                Tile thisTile = tileMap[x][y];
                if (thisTile.getTraversable()) {
                    traversableTiles.add(thisTile);
                    if (thisTile.getTileType().equals("t")) {
                        tunnelTiles.add(thisTile);
                    }
                }
            }
        }


        drawRats();
        drawItems();

    }


    /**
     * Draw all tunnels
     */
    public void drawTunnels() {
        for (Tile t : tunnelTiles) {
            t.draw(canvas);
        }
    }

    /**
     * Draw all rats on the board
     */
    public void drawRats() {
        for (Rat rt : rats) {
            gc.drawImage(rt.getSprite(), rt.getX() * TILE_SIZE_PX, rt.getY() * TILE_SIZE_PX);
        }
    }

    /**
     * The same as DrawRats, but only draws them at a specified tile.
     *
     * @param x x position
     * @param y y position
     */
    public void drawRats(int x, int y) {
        for (Rat rt : rats) {
            if (rt.getX() == x && rt.getY() == y) {
                gc.drawImage(rt.getSprite(), rt.getX() * TILE_SIZE_PX, rt.getY() * TILE_SIZE_PX);
            }

        }
    }

    /**
     * Draws all items on the board
     */
    public void drawItems() {
        for (Item it : items) {
            if (it.getMyItemType() == Item.itemType.Gas) {
                redrawTile(it.getX(), it.getY(), false);
                drawRats(it.getX(), it.getY());
            }

            gc.drawImage(it.getImage(), it.getX() * TILE_SIZE_PX, it.getY() * TILE_SIZE_PX);
        }

    }

    /**
     * Reloads items onto the board
     *
     * @param t    char type of item
     * @param xPos int x position of item
     * @param yPos int y position of item
     */
    public void reloadItems(char t, int xPos, int yPos) {
//        GasItem parent;
//        ArrayList<GasChild> children = new ArrayList<>();
        switch (t) {
            case 'g' -> new GasItem(xPos, yPos);
            case 'b' -> new BombItem(xPos, yPos);
            case 'n' -> new NoEntryItem(xPos, yPos);
            case 'f' -> new FMChange(xPos, yPos);
            case 'm' -> new MFChange(xPos, yPos);
            case 'd' -> new DeathRatItem(xPos, yPos);
            case 'p' -> new PoisonItem(xPos, yPos);
            case 's' -> new SteriliseItem(xPos, yPos);
//            case 'c' -> {
//                new GasChild(xPos, yPos, false);
//            }
            default -> System.out.println("Not working...");
        }
    }

    /**
     * Function to add an item to the item list and board
     *
     * @param item the item object
     */
    public void addItem(Item item) {

        if (item.getClass() == BombItem.class || item.getClass() == SteriliseItem.class) {

            instance.addListener((ITickHandler) item);
        }
        items.add(item);
        this.drawItems();
    }

    /**
     * removes item from board
     *
     * @param item object of an item
     */
    public void removeItem(Item item) {
        if (items.contains(item)) {
            if (item.getMyItemType() == Item.itemType.Gas || item.getMyItemType() == Item.itemType.Bomb
                    || item.getMyItemType() == Item.itemType.Sterilise) {
                //if we are listeners..
                instance.markListenerForRemoval((ITickHandler) item);
            }
            items.remove(item);
            redrawTile(item.getX(), item.getY(), true);
        }
    }

    /**
     * gets the tile map
     *
     * @return tile map
     */
    public Tile[][] getTileMap() {
        return tileMap;
    }


    /**
     * gets a list of traversable tiles
     *
     * @return traversable tiles
     */
    public ArrayList<Tile> getTraversableTiles() {
        return traversableTiles;
    }

    /**
     * Redraws tile at a specific location
     *
     * @param x           tile x position
     * @param y           tile y position
     * @param redrawItems whether to redraw items on tile
     */
    public void redrawTile(int x, int y, boolean redrawItems) {
        if (x >= 0 && y >= 0) { //ensures we never try to draw out of bounds.
            Tile tile = tileMap[x][y];
            tile.draw(canvas);

            if (redrawItems) {
                drawItems();
            }
        }
    }

    /**
     * adds a rat to the board
     *
     * @param rat object of a rat
     */
    public void addRat(Rat rat) {
        rats.add(rat);
        toolBar.countRats();
    }

    /**
     * removes rat from the board
     *
     * @param rat object of a rat
     */
    public void removeRat(Rat rat) {
        //remove the listener. All rats are listeners.
        instance.markListenerForRemoval(rat);

        if (rats.contains(rat)) {
            int x = rat.getX();
            int y = rat.getY();
            rats.remove(rat);
            redrawTile(x, y, true);
        }
    }

    /**
     * gets a list of all rats
     *
     * @return ArrayList of rat objects
     */
    public ArrayList<Rat> getRats() {
        return rats;
    }

    /**
     * gets a list of all items
     *
     * @return Arraylist of item objects
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * remakes toolbar every second
     *
     * @param count how many ticks have passed? Resets every second.
     */
    @Override
    public void tickEvent(int count) {
        root.setTop(toolBar.makeToolBar());
    }

    public void drawRat(Rat rat) {
        redrawTile(rat.getX(), rat.getY(), false);
        gc.drawImage(rat.getSprite(), rat.getX(), rat.getY());
    }

    /**
     * Check if an item exists at a given location already.
     *
     * @param x
     * @param y
     * @return true if an item exists, false otherwise.
     */
    public boolean existsItemAt(int x, int y) {
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return true;
            }

        }
        return false;
    }

    /**
     * gets tile map in the form of strings
     *
     * @return 2D array of strings
     */
    public String[][] getTempTileMap() {
        return tempTileMap;
    }

    /**
     * Map x size
     *
     * @return int mapX
     */
    public int getMapX() {
        return mapX;
    }

    /**
     * Map Y size
     *
     * @return int mapY
     */
    public int getMapY() {
        return mapY;
    }

    public DragAndDrop getToolBar() {
        return toolBar;
    }
}

