package Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
    private String playerName;
    private int mapX;
    private int mapY;
    private String[][] stringMap;

    public Save(int mapX, int mapY, String[][] tileMap) {
        Level INSTANCE = Level.getInstance();
        this.mapX = mapX;
        this.mapY = mapY;
        stringMap = tileMap;
        makeFile();
        writeToFile();
    }

//    public String[][] mapToString(Tile[][] tileMap) {
//        String[][] stringMap = new String[mapX][mapY];
//        for (int y = 0; y < mapY; y++) {
//            for (int x = 0; x < mapX; x++) {
//                 stringMap[x][y] = tileMap[x][y].getTileType();
//            }
//        }
//        return stringMap;
//    }

    public void makeFile() {
        try {
            File myObj = new File("filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(mapX + ", " + mapY + ",");
            myWriter.write("\n");
            for (int y = 0; y < mapY; y++) {
                for (int x = 0; x < mapX; x++) {
                     myWriter.write(stringMap[x][y] + " ");
                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
