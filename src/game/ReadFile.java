package game;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for reading in a level file
 *
 * @author Alex
 * @version 1.0
 */
public class ReadFile {
    private int mapX;
    private int mapY;
    private final Stage primaryStage;
    private Scanner in;

    /**
     * Constructor for read file class
     *
     * @param levelFile    level file to read from
     * @param primaryStage the primary stage
     * @throws FileNotFoundException throws if cannot find requested file
     */
    public ReadFile(String levelFile, Stage primaryStage) throws FileNotFoundException {
        this.primaryStage = primaryStage;
        File inputFile = new File(levelFile);
        this.in = null;
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + levelFile);
            System.exit(0);
        }
        in = new Scanner(inputFile).useDelimiter(";|\r\n| ");
    }

    /**
     * Returns a new level object for generation
     *
     * @return new, fresh level
     */
    public Level newLevel() {
        Scanner scan = new Scanner(in.nextLine()).useDelimiter(",|\r\n| ");
        mapX = mapSize(scan);
        mapY = mapSize(scan);
        scan.close();

        String[][] map = readMap(in);
        in.nextLine();
        ArrayList<String> rats = readSpawnRatLocations(new Scanner(in.nextLine()));
        ArrayList<String> respawns = readItemSpawnRates(new Scanner(in.nextLine()));
        int allowedTime = readTimeLimit(new Scanner(in.nextLine()));
        int lossCondition = readLossCondition(in);

        return new Level(mapX, mapY, map, rats, respawns, allowedTime, lossCondition, primaryStage); //in can never be closed... oops. - alex
    }

    /**
     * Returns a level with data already in it
     *
     * @return a level which ahs been played
     */
    public Level loadLevel() {
        Scanner scan = new Scanner(in.nextLine()).useDelimiter(",|\r\n| ");
        mapX = mapSize(scan);
        mapY = mapSize(scan);
        scan.close();

        String[][] map = readMap(in);
        in.nextLine();
        ArrayList<String> rats = readSpawnRatLocations(new Scanner(in.nextLine()));
        ArrayList<String> respawns = readItemSpawnRates(new Scanner(in.nextLine()));
        int allowedTime = readTimeLimit(new Scanner(in.nextLine()));
        int lossCondition = readLossCondition(new Scanner(in.nextLine()));
        ArrayList<String> items = readItemLocations(new Scanner(in.nextLine()));
        int currentScore = readCurrentScore(new Scanner(in.nextLine()));
        ArrayList<String> inv = readInv(in);

        return new Level(mapX, mapY, map, rats, respawns, allowedTime, lossCondition, currentScore, items, inv, primaryStage);
    }

    /**
     * Gets eitheer x or y of map
     *
     * @param in Scanner object
     * @return x or y of map
     */
    private int mapSize(Scanner in) {
        return Integer.parseInt(in.next());
    }

    /**
     * Reads in a 2D array of tilemap
     *
     * @param in Scanner object
     * @return String 2D array tilemap
     */
    private String[][] readMap(Scanner in) {
        String[][] tilemap = new String[mapX][mapY];
        for (int y = 0; y < mapY; y++) {
            for (int x = 0; x < mapX; x++) {
                if (in.hasNext()) {
                    tilemap[x][y] = in.next();
                }
            }
        }
        return tilemap;
    }

    /**
     * gets a list of rat spawn locations
     *
     * @param ratScan Scanner object
     * @return rat spawn locations and sex
     */
    private ArrayList<String> readSpawnRatLocations(Scanner ratScan) {
        ArrayList<String> rats = new ArrayList<>();
        ratScan.useDelimiter(";");
        while (ratScan.hasNext()) {
            rats.add(ratScan.next());
        }
        return rats;
    }

    /**
     * Gets a list of items spawn rates
     *
     * @param itemScan Scanner object
     * @return item spawn rate in list
     */
    private ArrayList<String> readItemSpawnRates(Scanner itemScan) {
        ArrayList<String> items = new ArrayList<>();
        itemScan.useDelimiter(";");
        while (itemScan.hasNext()) {
            items.add(itemScan.next());
        }
        return items;
    }

    /**
     * Reads in the time limit
     *
     * @param in Scanner object
     * @return level time limit
     */
    private int readTimeLimit(Scanner in) {
        return Integer.parseInt(in.next());
    }

    /**
     * Reads in the loss condition
     *
     * @param in Scanner object
     * @return level loss condition
     */
    private int readLossCondition(Scanner in) {
        return Integer.parseInt(in.next());
    }

    /**
     * Reads in the current score
     *
     * @param in Scanner object
     * @return current level score
     */
    private int readCurrentScore(Scanner in) {
        return Integer.parseInt(in.next());
    }

    /**
     * Reads in the current item locations
     *
     * @param itemScan Scanner object
     * @return current item types and locations
     */
    private ArrayList<String> readItemLocations(Scanner itemScan) {
        ArrayList<String> items = new ArrayList<>();
        itemScan.useDelimiter(";");
        while (itemScan.hasNext()) {
            items.add(itemScan.next());
        }
        return items;
    }

    /**
     * Reads in the current inventory
     *
     * @param in Scanner object
     * @return current level inventory
     */
    private ArrayList<String> readInv(Scanner in) {
        ArrayList<String> inv = new ArrayList<>();
        in.useDelimiter(";");
        while (in.hasNext()) {
            inv.add(in.next());
        }
        return inv;
    }

}
