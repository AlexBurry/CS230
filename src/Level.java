import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * Level class
 * @author Alex
 * @version 0.1
 */
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
    private Board levelBoard;
    private static Level instance;
    private Canvas canvas;
    private Timeline tickTimeline;
    private final int TICKRATE = 1000;

    /**
     * Level constructor for a new Level
     * @param mapX Width of map
     * @param mapY Height of map
     * @param tiles 2D array of the tile map as strings
     * @param primaryStage primary stage scene
     */
    public Level (int mapX, int mapY, String[][] tiles, /*ArrayList<String> items, ArrayList<String> rats,
                  int allowedTime, int lossCondition*/ Stage primaryStage) {
        levelBoard = new Board(tiles, /*items, rats, */ mapX, mapY);
        levelBoard.start(primaryStage);
        levelBoard.drawBoard();

        //game tick system
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play(); //can be used to pause the game
    }

    /**
     * Tick method for the game runtime, updates board every second/tick
     */
    public void tick() {
        System.out.println("tick");
        for (Rat rt: levelBoard.getRatMap()) {
            rt.move();
            levelBoard.drawBoard();
        }

    }

    /**
     * Getter for the Board object
     * @return Board object
     */
    public Board getLevelBoard(){
        return levelBoard;
    }

    /**
     * Idk
     * @return Instance of this Level object
     */
    public static Level getInstance(){
        return instance;
    }
}
