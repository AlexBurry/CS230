package Game;

import GUI.Inventory;
import RatClasses.DeathRat;
import RatClasses.Rat;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Game.Level class
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
    private final int TICKRATE = 500;
    private List<ITickHandler> listeners = new ArrayList<>();
    private ArrayList<ITickHandler> nullListeners = new ArrayList<>();

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
        currentScore = 0;
        instance = this;
        levelInv = new Inventory(itemsRespawnRate);
        levelBoard = new Board(tiles, rats, mapX, mapY);
        levelBoard.start(primaryStage);

        //game tick system
        Timeline tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play(); //can be used to pause the game
    }


    public void addListener(ITickHandler toAdd) {
        listeners.add(toAdd);

    }

    public List<ITickHandler> getListeners(){
        return listeners;
    }

    public void markListenerForRemoval(ITickHandler toAdd) {
        nullListeners.add(toAdd);
    }

    /**
     * ITickHandler method for the game runtime, updates board every second/tick
     */
    public void tick() { //TODO: figure out order through trial and error
        for (ITickHandler t : nullListeners){
            listeners.remove(t);
        }
        for (ITickHandler t : listeners) {
            t.tickEvent();
        }

        levelBoard.drawRats();
        levelBoard.drawItems();
        checkLossCondition();
        timeLeft = timeLeft - 1;
    }

    public void increaseScore(int pointsToAdd) {
        currentScore = currentScore + pointsToAdd;
        System.out.println(currentScore);
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
            System.exit(0);
        }
        if (timeLeft == 0) {
            System.out.println("Game over");
            System.exit(0);
        }
        ArrayList<Rat> properRats = levelBoard.getRats();
        properRats.removeIf(rat -> rat.getClass() == DeathRat.class);
        if (properRats.size() == 0) {
            currentScore = currentScore + timeLeft;
            System.out.println("Game won: " + currentScore);
            System.exit(0);
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getALLOWED_TIME() {
        return ALLOWED_TIME;
    }
}
