import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Level class
 * @author Alex
 * @version 0.1
 */
public class Level {
    private float timeRemaining;
    private int currentScore;
    private int allowedTime;
    private int timeLeft;
    private int lossCondition;
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
    private final int TICKRATE = 1000;

    /**
     * Level constructor for a new Level
     * @param mapX Width of map
     * @param mapY Height of map
     * @param tiles 2D array of the tile map as strings
     * @param primaryStage primary stage scene
     */
    public Level (int mapX, int mapY, String[][] tiles, ArrayList<String> rats, ArrayList<String> itemsRespawnRate,
                  int allowedTime, int lossCondition, Stage primaryStage) {
        this.allowedTime = allowedTime;
        timeLeft = this.allowedTime;
        this.lossCondition = lossCondition;
        instance = this;
        setItemRespawnTimers(itemsRespawnRate);
        levelBoard = new Board(tiles, rats, mapX, mapY);
        levelBoard.start(primaryStage);

        //game tick system
        Timeline tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play(); //can be used to pause the game
    }

    private void setItemRespawnTimers(ArrayList<String> itemsRespawnRate) {
        for (String iRR : itemsRespawnRate) {
            Scanner token = new Scanner(String.valueOf(iRR));
            token.useDelimiter(",");
            String[] values = new String[2];
            int i = 0;
            while (token.hasNext()) {
                values[i] = token.next();
                i++;
            }
            switch (values[0]) {
                case "b" -> bombSpawnRate = Integer.parseInt(values[1]);
                case "g" -> gasSpawnRate = Integer.parseInt(values[1]);
                case "s" -> sterilisationSpawnRate = Integer.parseInt(values[1]);
                case "p" -> poisonSpawnRate = Integer.parseInt(values[1]);
                case "m" -> mSexChangeSpawnRate = Integer.parseInt(values[1]);
                case "f" -> fSexChangeSpawnRate = Integer.parseInt(values[1]);
                case "n" -> noEntrySpawnRate = Integer.parseInt(values[1]);
                case "d" -> deathRatSpawnRate = Integer.parseInt(values[1]);
                default -> System.out.println("Invalid item char. Check level file.");
            }
        }
    }

    /**
     * Tick method for the game runtime, updates board every second/tick
     */
    public void tick() {
        for (Rat rt: levelBoard.getRats()) {
            levelBoard.redrawTile(rt.getX(),rt.getY());
            rt.move();
        }
        levelBoard.drawRats();
        checkLossCondition();
        timeLeft = timeLeft - 1;
        System.out.println(timeLeft);
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

    public void checkLossCondition() {
        if (levelBoard.getRats().size() >= lossCondition) {
            System.out.println("Game over");
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
