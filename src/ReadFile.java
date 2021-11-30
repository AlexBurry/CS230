import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
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
        in = new Scanner(inputFile).useDelimiter(",|\r\n| ");
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
        return new Level(mapX, mapY, readMap(in), primaryStage); //in can never be closed... oops. - alex
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

//    private String readTimeLimit() {
//
//    }
//
//    private String readLossCondition() {
//
//    }
//
//    private String readItemSpawnRates() {
//
//    }
//
//    private String readSpawnRatLocations() {
//
//    }
}
