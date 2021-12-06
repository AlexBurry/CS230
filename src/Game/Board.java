package Game;

import GUI.DragAndDrop;
import ItemClasses.*;
import RatClasses.BabyRat;
import RatClasses.Rat;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
public class Board extends Application implements ITickHandler {
    private final String[][] tempTileMap;
    private final Tile[][] tileMap;
    private ArrayList<Tile> traversableTiles = new ArrayList<>();
    private ArrayList<Tile> tunnelTiles = new ArrayList<>(); //used to make sure tunnels remain ontop
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Rat> rats = new ArrayList<>();

    private final int mapX;
    private final int mapY;
    private final int GAME_WIDTH = 1200;
    private final int GAME_HEIGHT = 884;
    private int gameWidthInTiles;
    private int gameHeightInTiles;
    private Level instance;
    private final Canvas canvas= new Canvas(GAME_WIDTH, GAME_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private DragAndDrop toolBar;
    private BorderPane root;

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
        instance = Level.getInstance();
        instance.addListener(this);
        for (String rt : rats) {
            String[] values = rt.split(",");
            Rat newRat;
            if (Character.isLowerCase(values[0].charAt(0))) {
                newRat = new BabyRat(values[0].charAt(0),Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]));
            } else {
                newRat = new Rat(values[0].charAt(0),Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]));
            }
            this.rats.add(newRat);
            instance.addListener(newRat);
        }



        gameWidthInTiles = GAME_WIDTH / 60;
        gameWidthInTiles = (GAME_HEIGHT - 104) / 60;

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
        root = new BorderPane();
        root.setCenter(canvas);

        toolBar = new DragAndDrop(canvas, tileMap);
        root.setTop(toolBar.makeToolBar());


        return root;
    }

    public void drawBoard() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Read in a 2d array of strings of tile types then turn it into a 2d array of Tile objects
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                tileMap[x][y] = new Tile(tempTileMap[x][y], x, y, canvas);
                Tile thisTile = tileMap[x][y];
                if(thisTile.getTraversable()){
                    traversableTiles.add(thisTile);
                    if(thisTile.getTileType().equals("t")){
                        tunnelTiles.add(thisTile);
                    }
                }
            }
        }


        drawRats();
        drawItems();

    }

    public void drawTunnels(){
        for (Tile t:tunnelTiles) {
            t.draw(canvas);
        }
    }

    public void drawRats() {
        for (Rat rt: rats) {
            gc.drawImage(rt.getSprite(),rt.getX()*60,rt.getY()*60);
        }
    }

    /**
     * The same as DrawRats, but only draws them at a specified tile.
     * @param x
     * @param y
     */
    public void drawRats(int x, int y) {
        for (Rat rt: rats) {
            if(rt.getX() == x && rt.getY() == y){
                gc.drawImage(rt.getSprite(),rt.getX()*60,rt.getY()*60);
            }

        }
    }

    public void drawItems() {
        for (Item it: items) {
            if(it.getMyItemType() == Item.itemType.Gas){
                redrawTile(it.getX(),it.getY(),false);
                drawRats(it.getX(),it.getY());
            }

            gc.drawImage(it.getImage(),it.getX() * 60,it.getY() * 60);
        }

    }

    /**
     * Reloads items onto the board
     * @param t
     * @param xPos
     * @param yPos
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
     * @param item the item object
     */
    public void addItem(Item item) {

        if (item.getClass() == BombItem.class || item.getClass() == SteriliseItem.class) {

            instance.addListener((ITickHandler) item);
        }
        items.add(item);
        this.drawItems();
    }

    public void removeItem(Item item) {



        if(items.contains(item)){
            if(item.getMyItemType() == Item.itemType.Gas || item.getMyItemType() == Item.itemType.Bomb
                    || item.getMyItemType() == Item.itemType.Sterilise){
                //if we are listeners..
                instance.markListenerForRemoval((ITickHandler) item);
            }

            items.remove(item);
            redrawTile(item.getX(),item.getY(),true);
        }
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public ArrayList<Tile> getTraversableTiles(){
        return traversableTiles;
    }

    public void redrawTile(int x, int y, boolean redrawItems) {
        if(x >= 0 && y >= 0){ //ensures we never try to draw out of bounds.
            Tile tile = tileMap[x][y];
            tile.draw(canvas);

            if(redrawItems){
                drawItems();
            }
        }
    }

    public void addRat(Rat rat) {
        rats.add(rat);
        toolBar.countRats();
    }

    public void removeRat(Rat rat) {
        //remove the listener. All rats are listeners.
        instance.markListenerForRemoval(rat);

        if(rats.contains(rat)){
            int x = rat.getX();
            int y = rat.getY();
            rats.remove(rat);
            redrawTile(x,y,true);
        }
    }

    public ArrayList<Rat> getRats() {
        return rats;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public void tickEvent(int count) {
        root.setTop(toolBar.makeToolBar());
    }

    public void drawRat(Rat rat) {
        redrawTile(rat.getX(),rat.getY(),false);
        gc.drawImage(rat.getSprite(),rat.getX(),rat.getY());
    }

    /**
     * Check if an item exists at a given location already.
     * @param x
     * @param y
     * @return true if an item exists, false otherwise.
     */
    public boolean existsItemAt(int x, int y) {
        for(Item item: items){
            if(item.getX() == x && item.getY() == y){
                return true;
            }

        }
        return false;
    }

    public String[][] getTempTileMap() {
        return tempTileMap;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    /**
     * @return int gameWidthInTiles, the width in terms of tiles.
     */
    public int getGameWidthInTiles(){
        return gameWidthInTiles;
    }

    /**
     * @return int gameHeightInTiles, the height in terms of tiles, accounting for the inventory box.
     */
    public int getGameHeightInTiles() {
        return gameHeightInTiles;

    }
}
