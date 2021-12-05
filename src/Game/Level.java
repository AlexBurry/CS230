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
 *
 * @author Alex
 * @version 0.1
 */
public class Level {
    private float timeRemaining;
    private int currentScore;
    private final int ALLOWED_TIME;
    private int timeLeft;
    private int levelNumber;
    private int lossCondition;
    private Board levelBoard;
    private Inventory levelInv;
    private String profileName;
    private String levelName;
    private static Level instance;
    private ArrayList<String> itemsRespawnRate = new ArrayList<>();
    private final int TICKRATE = 250;
    private int tickCount = 1; //used to keep ticks in line.
    private List<ITickHandler> listeners = new ArrayList<>();
    private ArrayList<ITickHandler> nullListeners = new ArrayList<>();
    private ArrayList<Rat> ratsToAddAfterTick = new ArrayList<>();


    /**
     * Level constructor for a new Level
     *
     * @param mapX         Width of map
     * @param mapY         Height of map
     * @param tiles        2D array of the tile map as strings
     * @param primaryStage primary stage scene
     * @param itemsRespawnRate list of item respawn rates
     * @param allowedTime amount of time allowed in level
     * @param lossCondition loss condition for level
     */
    public Level(int mapX, int mapY, String[][] tiles, ArrayList<String> rats, ArrayList<String> itemsRespawnRate,
                 int allowedTime, int lossCondition, Stage primaryStage) {

        this.ALLOWED_TIME = allowedTime;
        timeLeft = this.ALLOWED_TIME;
        this.lossCondition = lossCondition;
        currentScore = 0;
        instance = this;


        levelInv = new Inventory(itemsRespawnRate);
        levelBoard = new Board(tiles, rats, mapX, mapY);
        levelBoard.start(primaryStage);

        this.itemsRespawnRate = itemsRespawnRate;

        createTick();
    }

    /**
     * Level constructor for loading a level
     * @param mapX
     * @param mapY
     * @param tiles
     * @param rats
     * @param itemsRespawnRate
     * @param timeLeft
     * @param lossCondition
     * @param items
     * @param primaryStage
     */
    public Level(int mapX, int mapY, String[][] tiles, ArrayList<String> rats, ArrayList<String> itemsRespawnRate,
                 int timeLeft, int lossCondition, int currentScore, ArrayList<String> items, Stage primaryStage) {

        this.ALLOWED_TIME = timeLeft;
        this.timeLeft = this.ALLOWED_TIME;
        this.lossCondition = lossCondition;
        this.currentScore = currentScore;
        instance = this;


        levelInv = new Inventory(itemsRespawnRate);
        levelBoard = new Board(tiles, rats, mapX, mapY);
        levelBoard.start(primaryStage);

        this.itemsRespawnRate = itemsRespawnRate;

        createTick();
    }

    /**
     * Creates and sets the tick rate for the entire game. Called in Level construction
     */
    public void createTick() {
        //game tick system
        Timeline tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play(); //can be used to pause the game
    }

    public void addProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void addLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * adds a listener to the tick event
     * @param toAdd object to be added to listeners list
     */
    public void addListener(ITickHandler toAdd) {
        listeners.add(toAdd);
    }

    /**
     * gets the listeners for the tick event
     * @return list of current tick event listeners
     */
    public List<ITickHandler> getListeners() {
        return listeners;
    }

    /**
     * Marks an object for removal from the tick event
     * @param toAdd object to be removed from listeners list
     */
    public void markListenerForRemoval(ITickHandler toAdd) {
        nullListeners.add(toAdd);
    }

    /**
     * ITickHandler method for the game runtime, updates board every second/tick
     */
    public void tick() { //TODO: figure out order through trial and error
        //System.out.println(lossCondition);
        for (ITickHandler t : nullListeners) {
            listeners.remove(t);
        }
        for (ITickHandler t : listeners) {
            t.tickEvent(tickCount);
        }

        //add rats after, so we dont modify the collection.
        for (Rat rt : ratsToAddAfterTick){
            getLevelBoard().addRat(rt);
            listeners.add(rt);
        }
        ratsToAddAfterTick.clear();

        levelBoard.drawRats();
        levelBoard.drawItems();
        levelBoard.drawTunnels();

        tickCount++;

        if(tickCount > 4){
            timeLeft = timeLeft - 1;
            checkLossCondition();
            tickCount = 1;
        }
    }

    /**
     * Adds rats to rat list
     * @param rat new rat object
     */
    public void addRatToQueue(Rat rat){
        ratsToAddAfterTick.add(rat);
    }

    /**
     * Increases the current score every tick
     * @param pointsToAdd number of points to add since last tick if any rats have died
     */
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

    /**
     * get the level inventory
     * @return inventory object
     */
    public Inventory getLevelInv() {
        return levelInv;
    }

    /**
     * Idk
     *
     * @return Instance of this Level object
     */
    public static Level getInstance() {
        return instance;
    }

    /**
     * Checks if the loss condition has been met every tick, ends the level if true
     */
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
        properRats.removeIf(rat -> rat.getClass() == DeathRat.class); //removes deathrats from consideration in the rats
        if (properRats.size() == 0) {
            currentScore = currentScore + timeLeft;
            System.out.println("Game won: " + currentScore);
            System.exit(0);
        }
    }

    /**
     * Function that calls the save class in order to create a new savefile
     */
    public void save() {
        int[] inv = new int[8];
        inv[0] = levelInv.getNumberOfBombs();
        inv[1] = levelInv.getNumberOfMSexChange();
        inv[2] = levelInv.getNumberOfFSexChange();
        inv[3] = levelInv.getNumberOfGas();
        inv[4] = levelInv.getNumberOfPoison();
        inv[5] = levelInv.getNumberOfNoEntry();
        inv[6] = levelInv.getNumberOfSterilisation();
        inv[7] = levelInv.getNumberOfDeathRat();


        new Save(profileName, levelName, levelBoard.getMapX(), levelBoard.getMapY(), levelBoard.getTempTileMap(),
                levelBoard.getRats(), itemsRespawnRate,  timeLeft, lossCondition, levelBoard.getItems(),
                currentScore, inv);
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getALLOWED_TIME() {
        return ALLOWED_TIME;
    }

    /**
     * Gets the loss condition value
     * @return lossCondition
     */
    public int getLossCondition() { return lossCondition; }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
