package Game;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private int mapX;
    private int mapY;
    private final Stage primaryStage;
    private Scanner in;

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
        int currentScore = readCurrentScore(new Scanner(in.nextLine()));
        ArrayList<String> items = readItemLocations(in);

        return new Level(mapX, mapY, map, rats, respawns, allowedTime, lossCondition, currentScore, items, primaryStage);
    }

    private int mapSize(Scanner in) {
        return Integer.parseInt(in.next());
    }

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

    private ArrayList<String> readSpawnRatLocations(Scanner ratScan) {
        ArrayList<String> rats = new ArrayList<>();
        ratScan.useDelimiter(";");
        while (ratScan.hasNext()) {
            rats.add(ratScan.next());
        }
        return rats;
    }

    private ArrayList<String> readItemSpawnRates(Scanner itemScan) {
        ArrayList<String> items = new ArrayList<>();
        itemScan.useDelimiter(";");
        while (itemScan.hasNext()) {
            items.add(itemScan.next());
        }
        return items;
    }

    private int readTimeLimit(Scanner in) {
        return Integer.parseInt(in.next());
    }

    private int readLossCondition(Scanner in) {
        return Integer.parseInt(in.next());
    }

    private int readCurrentScore(Scanner in) {
        return Integer.parseInt(in.next());
    }

    private ArrayList<String> readItemLocations(Scanner itemScan) {
        ArrayList<String> rats = new ArrayList<>();
        itemScan.useDelimiter(";");
        while (itemScan.hasNext()) {
            rats.add(itemScan.next());
        }
        return rats;
    }

}
