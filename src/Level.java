import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import static javafx.application.Application.launch;

public class Level {
    private float timeRemaining;
    private int currentScore;
    private int allowedTime;
    private int bombSpawnRate;
    private int mSexChangeSpawnRate;
    private int fSexChangeSpawnRate;
    private int gasSpawnRate;
    private int poisonSpawnRate;
    private int sterilisationSpawnRate;
    private int noEntrySpawnRate;
    private int deathRatSpawnRate;
    private int numberOfBombs;
    private int numberOfMSexChange;
    private int numberOfFSexChange;
    private int numberOfGas;
    private int numberOfSterilisation;
    private int numberOfPoison;
    private int numberOfNoEntry;
    private final int MAX_ITEM_NUMBER = 4;
    private static Board levelBoard;
    private static Level instance;
    private Canvas canvas;

    public Level (String[][] tiles, int mapX, int mapY) {
        launch();
        levelBoard = new Board(tiles, mapX, mapY);
        buildGUI();
        instance = this;
        levelBoard.drawBoard();
    }

    private static Pane buildGUI() {
        // Create top-level panel that will hold all GUI nodes.
        BorderPane root = new BorderPane();

        // Create the canvas that we will draw on.
        // We store this as a global variable so other methods can access it.
        Canvas canvas = new Canvas(800, 800);
        root.setCenter(canvas);

        return root;
    }


//    public void tick() {
//        // Here we move the player right one cell and teleport
//        // them back to the left side when they reach the right side.
//        playerX = playerX + 1;
//        if (playerX > 11) {
//            playerX = 0;
//        }
//        // We then redraw the whole canvas.
//        drawGame();
//    }

//    public Board constructBoard(Tile[][] tiles, Item[][] items, Rat[][] mRats, Rat[][] fRats) {
//        return new Board(tiles, items, mRats, fRats);
//    }


//    public void checkCollisions() {
//        Rat[][] mRatMap = levelBoard.getRatMap();
//        Item[][] itemMap = levelBoard.getItemMap();
//
//    }


}
