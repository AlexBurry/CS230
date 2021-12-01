import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Level class
 * @author Alex
 * @version 0.1
 */
public class Level {
    private float timeRemaining;
    private int currentScore;
    private final int ALLOWED_TIME;
    private int timeLeft;
    private int lossCondition;
    private Board levelBoard;
    private Inventory levelInv;
    private static Level instance;
    private final int TICKRATE = 1000;
    private List<Tick> listeners = new ArrayList<>();
    private ArrayList<Tick> nullListeners = new ArrayList<>();

    /**
     * Level constructor for a new Level
     * @param mapX Width of map
     * @param mapY Height of map
     * @param tiles 2D array of the tile map as strings
     * @param primaryStage primary stage scene
     */
    public Level (int mapX, int mapY, String[][] tiles, ArrayList<String> rats, ArrayList<String> itemsRespawnRate,
                  int allowedTime, int lossCondition, Stage primaryStage) {

        this.ALLOWED_TIME = allowedTime;
        timeLeft = this.ALLOWED_TIME;
        this.lossCondition = lossCondition;
        instance = this;
        levelInv = new Inventory(itemsRespawnRate);
        levelBoard = new Board(tiles, rats, mapX, mapY);
        levelBoard.start(primaryStage);

        //game tick system
        Timeline tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play(); //can be used to pause the game
    }


    public void addListener(Tick toAdd) {
        listeners.add(toAdd);
    }

    public void markListenerForRemoval(Tick toAdd) {
        nullListeners.add(toAdd);
    }

    /**
     * Tick method for the game runtime, updates board every second/tick
     */
    public void tick() { //TODO: figure out order through trial and error
        for (Tick t : nullListeners){
            listeners.remove(t);
        }
        for (Tick t : listeners) {
            t.tickEvent();
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
    public Board getLevelBoard() {
        return levelBoard;
    }

    public Inventory getLevelInv() {
        return levelInv;
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

    public int getALLOWED_TIME() {
        return ALLOWED_TIME;
    }
}
