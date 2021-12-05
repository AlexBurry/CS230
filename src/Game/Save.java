package Game;

import ItemClasses.Item;
import RatClasses.Rat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Character.toUpperCase;

public class Save {
    private String playerName;
    private int mapX;
    private int mapY;
    private String[][] stringMap;
    private ArrayList<String> ratList = new ArrayList<>();

    public Save(int mapX, int mapY, String[][] tileMap, ArrayList<Rat> rats, ArrayList<Item> items,
                int timeLeft, int lossCondition) {
        Level INSTANCE = Level.getInstance();
        this.mapX = mapX;
        this.mapY = mapY;
        stringMap = tileMap;
        ratList = getRatInfo(rats);
        makeFile();
        writeToFile();
    }

    public ArrayList<String> getRatInfo(ArrayList<Rat> rats) {
        ArrayList<String> ratList = new ArrayList<>();
        char sex;
        for (Rat r: rats) {
            if (!r.isBaby()) {
                sex = toUpperCase(r.getSex());
            } else {
                sex = r.getSex();
            }
            int x = r.getxPos();
            int y = r.getyPos();
            ratList.add(sex + "," + x + "," + y + ";");
        }
        return ratList;
    }

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
            for (String r: ratList) {
                myWriter.write(r);
            }
            myWriter.write("\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
