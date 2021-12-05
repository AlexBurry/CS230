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
    private ArrayList<String> ratList;
    private ArrayList<String> itemsRespawnRate;
    private ArrayList<String> itemList;
    private int timeLeft;
    private int lossCondition;
    private int currentScore;
    private int[] inv;

    public Save(int mapX, int mapY, String[][] tileMap, ArrayList<Rat> rats, ArrayList<String> itemsRespawnRate,
                int timeLeft, int lossCondition, ArrayList<Item> items, int currentScore, int[] inv) {
        Level INSTANCE = Level.getInstance();
        this.mapX = mapX;
        this.mapY = mapY;
        stringMap = tileMap;
        ratList = getRatInfo(rats);
        itemList = getItemInfo(items);
        this.inv = inv;
        this.itemsRespawnRate = itemsRespawnRate;
        this.timeLeft = timeLeft;
        this.lossCondition = lossCondition;
        this.currentScore = currentScore;
        makeFile();
        writeToFile();
    }

    public ArrayList<String> getRatInfo(ArrayList<Rat> rats) {
        ArrayList<String> ratList = new ArrayList<>();
        char sex;
        for (Rat r: rats) {
            if (!r.getIsBaby()) {
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

    public ArrayList<String> getItemInfo(ArrayList<Item> items) {
        ArrayList<String> itemList = new ArrayList<>();
        char type;
        for (Item i: items) {
            switch (i.getMyItemType()) {
                case Gas -> type = 'g';
                case NoEntry -> type = 'n';
                case FSex -> type = 'f';
                case MSex -> type = 'm';
                case Poison -> type = 'p';
                case DeathRat -> type = 'd';
                case Bomb -> type = 'b';
                case Sterilise -> type = 's';
                default -> throw new IllegalStateException("Unexpected value: " + i.getMyItemType());
            }

            int x = i.getX();
            int y = i.getY();
            itemList.add(type + "," + x + "," + y + ";");
        }
        return itemList;
    }

//    public ArrayList<String> getInvInfo() {
//
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
            for (String r: ratList) {
                myWriter.write(r);
            }
            myWriter.write("\n");
            for (String irr: itemsRespawnRate) {
                myWriter.write(irr + ";");
            }
            myWriter.write("\n");
            myWriter.write(timeLeft);
            myWriter.write("\n");
            myWriter.write(lossCondition);
            myWriter.write("\n");
            for (String i: itemList) {
                myWriter.write(i);
            }
            myWriter.write("\n");
            myWriter.write(currentScore);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
