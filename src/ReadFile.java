import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ReadFile {
    private int mapX;
    private int mapY;
    private static Level level;

    public ReadFile(String levelFile) throws FileNotFoundException {
        File inputFile = new File(levelFile);
        Scanner in = null;
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open " + levelFile);
            System.exit(0);
        }
        in = new Scanner(inputFile).useDelimiter(",| ");

        mapX = mapSize(in);
        mapY = mapSize(in);
        level = new Level(mapX, mapY, readMap(in));
        //Tile[][] tilemap = readMap(in);
        //new Level(tilemap, mapX, mapY);
    }

    private int mapSize(Scanner in) {
        return Integer.parseInt(in.next());
    }

    private String[][] readMap(Scanner in) {
        String[][] tilemap = new String[mapX][mapY];
        String curLine = in.nextLine();
        Scanner token = new Scanner(String.valueOf(curLine));
        for (int x = 0; x < mapX; x++) {
            for (int y = 0; y < mapX; y++) {
                tilemap[x][y] = in.next();
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

//    private String readSaveItemLocations() {
//
//    }

//    private String readSaveRatLocations() {
//
//    }
}
