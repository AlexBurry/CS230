import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    public ReadFile(String levelFile) {
        File inputFile = new File(levelFile);
        Scanner in = null;
        in = new Scanner(levelFile);
        Tile[][] tilemap = readMap(in);
    }

    private Tile[][] readMap(Scanner in) {
        String curLine = in.nextLine();
        Scanner token = new Scanner(String.valueOf(curLine));

        while (in.hasNext()) {

        }
        return null;
    }
//
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
