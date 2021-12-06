package Game;

import GUI.Inventory;
import GUI.Menu;
import RatClasses.BabyRat;
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
 * @version 1.0
 */
public class Level {
    private float timeRemaining;
    private int currentScore;
    private final int ALLOWED_TIME;
    private int timeLeft;
    private int levelNumber;
    private final int LOSS_CONDITION;
    private final Board LEVEL_BOARD;
    private final Inventory LEVEL_INVENTORY;
    private String profileName;
    private String levelName;
    private static Level instance;
    private ArrayList<String> itemsRespawnRate = new ArrayList<>();
    private int tickCount = 1; //used to keep ticks in line.
    private List<ITickHandler> listeners = new ArrayList<>();
    private ArrayList<ITickHandler> nullListeners = new ArrayList<>();
    private ArrayList<Rat> ratsToAddAfterTick = new ArrayList<>();


    /**
     * Level constructor for a new level
     * @param mapX Width of map
     * @param mapY Height of map
     * @param tiles 2D array of the tile map as strings
     * @param rats list of rats as a string
     * @param itemsRespawnRate list of item spawn rates
     * @param allowedTime amount of time allowed in level to start off
     * @param lossCondition amount of rats on the board for game to be lost
     * @param primaryStage primary stage scene
     */
    public Level(int mapX, int mapY, String[][] tiles, ArrayList<String> rats, ArrayList<String> itemsRespawnRate,
                 int allowedTime, int lossCondition, Stage primaryStage) {
        instance = this;

        this.ALLOWED_TIME = allowedTime;
        timeLeft = this.ALLOWED_TIME;
        this.LOSS_CONDITION = lossCondition;
        currentScore = 0;

        this.itemsRespawnRate = itemsRespawnRate;
        LEVEL_INVENTORY = new Inventory(itemsRespawnRate);

        LEVEL_BOARD = new Board(tiles, rats, mapX, mapY);
        LEVEL_BOARD.start(primaryStage);

        createTick(); //begins tick
    }

    /**
     * Level constructor for loading a level
     * @param mapX Width of map
     * @param mapY Height of map
     * @param tiles 2D array of the tile map as strings
     * @param rats list of rats as a string
     * @param itemsRespawnRate list of item spawn rates
     * @param timeLeft amount of time left in level
     * @param lossCondition amount of rats on the board for game to be lost
     * @param items list of all items and their locations
     * @param primaryStage primary stage scene
     * @param currentScore current score of the level
     * @param inv amount of all inventory items
     */
    public Level(int mapX, int mapY, String[][] tiles, ArrayList<String> rats, ArrayList<String> itemsRespawnRate,
                 int timeLeft, int lossCondition, int currentScore, ArrayList<String> items, ArrayList<String> inv,
                 Stage primaryStage) {
        instance = this;

        this.ALLOWED_TIME = timeLeft;
        this.timeLeft = this.ALLOWED_TIME;
        this.LOSS_CONDITION = lossCondition;
        this.currentScore = currentScore;

        this.itemsRespawnRate = itemsRespawnRate;
        LEVEL_INVENTORY = new Inventory(itemsRespawnRate);
        reloadInv(inv);
        LEVEL_BOARD = new Board(tiles, rats, mapX, mapY);
        LEVEL_BOARD.start(primaryStage);
        for (String i: items) {
            String[] values = i.split(",");
            LEVEL_BOARD.reloadItems(values[0].charAt(0), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
        }


        createTick(); //begins tick
    }

    /**
     * Creates and sets the tick rate for the entire game. Called in Level construction
     */
    public void createTick() {
        //game tick system
        int TICKRATE = 250;
        Timeline tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play(); //can be used to pause the game
    }

    /**
     * adds the current logged in profile name
     * @param profileName current profile username
     */
    public void addProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * adds the current level name
     * @param levelName current level file name
     */
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

        //add rats after, so we don't modify the collection.
        for (Rat rt : ratsToAddAfterTick){
            getLevelBoard().addRat(rt);
            listeners.add(rt);
        }
        ratsToAddAfterTick.clear();

        LEVEL_BOARD.drawRats();
        LEVEL_BOARD.drawItems();
        LEVEL_BOARD.drawTunnels();

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
        Profile profile = Menu.getProfile();
        profile.setScore(pointsToAdd);
        System.out.println("\n" + profile.getScore());
    }

    /**
     * Getter for the Board object
     * @return Board object
     */
    public Board getLevelBoard() {
        return LEVEL_BOARD;
    }

    /**
     * get the level inventory
     * @return inventory object
     */
    public Inventory getLevelInventory() {
        return LEVEL_INVENTORY;
    }

    /**
     * Idk
     * @return Instance of this Level object
     */
    public static Level getInstance() {
        return instance;
    }

    /**
     * Checks if the loss condition has been met every tick, ends the level if true
     */
    public void checkLossCondition() {
        if (LEVEL_BOARD.getRats().size() >= LOSS_CONDITION) {
            System.out.println("Game over");
            System.exit(0);
        }
        if (timeLeft == 0) {
            System.out.println("Game over");
            System.exit(0);
        }
        ArrayList<Rat> properRats = LEVEL_BOARD.getRats();
        properRats.removeIf(rat -> rat.getClass() == DeathRat.class); //removes deathrats from consideration in the rats
        if (properRats.size() == 0) {
            currentScore = currentScore + timeLeft;
            System.out.println("Game won: " + currentScore);
            System.exit(0);
        }
    }

    /**
     * Reloads the current inventory values
     * @param inv arraylist of inventory values stored as strings
     */
    public void reloadInv(ArrayList<String> inv) {
        for (String item: inv) {
            switch (item.charAt(0)) {
                case 'b' -> LEVEL_INVENTORY.setNumberOfBombs(Character.getNumericValue(item.charAt(2)));
                case 'f' -> LEVEL_INVENTORY.setNumberOfFSexChange(Character.getNumericValue(item.charAt(2)));
                case 'm' -> LEVEL_INVENTORY.setNumberOfMSexChange(Character.getNumericValue(item.charAt(2)));
                case 'g' -> LEVEL_INVENTORY.setNumberOfGas(Character.getNumericValue(item.charAt(2)));
                case 'p' -> LEVEL_INVENTORY.setNumberOfPoison(Character.getNumericValue(item.charAt(2)));
                case 's' -> LEVEL_INVENTORY.setNumberOfSterilisation(Character.getNumericValue(item.charAt(2)));
                case 'd' -> LEVEL_INVENTORY.setNumberOfDeathRat(Character.getNumericValue(item.charAt(2)));
                case 'n' -> LEVEL_INVENTORY.setNumberOfNoEntry(Character.getNumericValue(item.charAt(2)));
            }
        }
    }

    /**
     * Function that calls the save class in order to create a new savefile
     */
    public void save() {
        int[] inv = new int[8];
        inv[0] = LEVEL_INVENTORY.getNumberOfBombs();
        inv[1] = LEVEL_INVENTORY.getNumberOfMSexChange();
        inv[2] = LEVEL_INVENTORY.getNumberOfFSexChange();
        inv[3] = LEVEL_INVENTORY.getNumberOfGas();
        inv[4] = LEVEL_INVENTORY.getNumberOfPoison();
        inv[5] = LEVEL_INVENTORY.getNumberOfNoEntry();
        inv[6] = LEVEL_INVENTORY.getNumberOfSterilisation();
        inv[7] = LEVEL_INVENTORY.getNumberOfDeathRat();


        new Save(profileName, levelName, LEVEL_BOARD.getMapX(), LEVEL_BOARD.getMapY(), LEVEL_BOARD.getTempTileMap(),
                LEVEL_BOARD.getRats(), itemsRespawnRate,  timeLeft, LOSS_CONDITION, LEVEL_BOARD.getItems(),
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
    public int getLOSS_CONDITION() { return LOSS_CONDITION; }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
