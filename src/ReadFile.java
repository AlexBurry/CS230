import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    private int mapX;
    private int mapY;

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

        //Tile[][] tilemap = readMap(in);
        //new Level(tilemap, mapX, mapY);
    }

    private int mapSize(Scanner in) {
        return Integer.parseInt(in.next());
    }

//    private Tile[][] readMap(Scanner in) {
//        Tile[][] tilemap = new Tile[mapX][mapY];
//        String curLine = in.nextLine();
//        Scanner token = new Scanner(String.valueOf(curLine));
//        for (int y = 0; y < mapY; y++) {
//            for (int x = 0; y < mapX; x++) {
//                while (in.hasNext()) {
//                    tilemap[x][y] = new Tile(in.next(), 40, 40);
//                }
//            }
//        }
//        return tilemap;
//    }

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
