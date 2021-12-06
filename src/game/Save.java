package game;

import itemClasses.GasChild;
import itemClasses.Item;
import ratClasses.Rat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Character.toUpperCase;

/**
 * Save classes that save the games current data and writes it to a File
 *
 * @author Alex
 * @version 1.0
 */
public class Save {
    private String playerName;
    private final int MAPX;
    private final int MAPY;
    private String[][] stringMap;
    private ArrayList<String> ratList;
    private ArrayList<String> itemsRespawnRate;
    private ArrayList<String> itemList;
    private int timeLeft;
    private int lossCondition;
    private int currentScore;
    private String[] inv;
    private final String PROFILE_NAME;
    private final String LEVEL_NAME;

    /**
     * Constructor for Save class
     *
     * @param profileName      players name
     * @param levelName        current levels name
     * @param MAPX             height of map
     * @param MAPY             width of map
     * @param tileMap          map of characters that correlate to tiles
     * @param rats             list of rats and their data
     * @param itemsRespawnRate list of item respawn rates for the current level
     * @param timeLeft         amount of time left in the level
     * @param lossCondition    the loss condition for the level
     * @param items            list of items and their data
     * @param currentScore     current player score in level
     * @param inv              inventory data
     */
    public Save(String profileName, String levelName, int MAPX, int MAPY, String[][] tileMap, ArrayList<Rat> rats,
                ArrayList<String> itemsRespawnRate, int timeLeft, int lossCondition, ArrayList<Item> items,
                int currentScore, int[] inv) {
        PROFILE_NAME = profileName;
        LEVEL_NAME = levelName;
        Level INSTANCE = Level.getInstance();
        this.MAPX = MAPX;
        this.MAPY = MAPY;
        stringMap = tileMap;
        ratList = getRatInfo(rats);
        itemList = getItemInfo(items);
        //this.inv = inv;
        this.itemsRespawnRate = itemsRespawnRate;
        this.timeLeft = timeLeft;
        this.lossCondition = lossCondition;
        this.currentScore = currentScore;
        this.inv = getInvInfo(inv);
        makeFile();
        writeToFile();
    }

    /**
     * Retrieves relevant info from each rat
     *
     * @param rats arraylist of rat objects
     * @return an arraylist of rats and their positions
     */
    public ArrayList<String> getRatInfo(ArrayList<Rat> rats) {
        ArrayList<String> ratList = new ArrayList<>();
        char sex;
        for (Rat r : rats) {
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

    /**
     * Retrieves relevant info from each item
     *
     * @param items arraylist of item objects
     * @return an arraylist of items and their positions
     */
    public ArrayList<String> getItemInfo(ArrayList<Item> items) {
        ArrayList<String> itemList = new ArrayList<>();
        char type;
        for (Item i : items) {
            switch (i.getMyItemType()) {
                case Gas -> {
                    if (i.getClass() == GasChild.class) {
                        type = 'c';
                    } else {
                        type = 'g';
                    }
                }
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

    /**
     * Creates an array for inventory
     *
     * @param inv array of current inventory sizes
     * @return array of current inventory sizes and the corresponding item chars
     */
    public String[] getInvInfo(int[] inv) {
        String[] inventory = new String[8];
        inventory[0] = "b," + inv[0];
        inventory[1] = "m," + inv[1];
        inventory[2] = "f," + inv[2];
        inventory[3] = "g," + inv[3];
        inventory[4] = "p," + inv[4];
        inventory[5] = "n," + inv[5];
        inventory[6] = "s," + inv[6];
        inventory[7] = "d," + inv[7];
        return inventory;
    }

    /**
     * Creates a new save file named after the player and current level
     */
    public void makeFile() {
        try {
            File myObj;
            boolean matches = LEVEL_NAME.contains(PROFILE_NAME);
            if (matches) {
                myObj = new File(LEVEL_NAME);
            } else {
                myObj = new File(PROFILE_NAME + LEVEL_NAME);
            }
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

    /**
     * Writes to the new save file in the correct format
     */
    public void writeToFile() {
        try {
            FileWriter myWriter;

            boolean matches = LEVEL_NAME.contains(PROFILE_NAME);
            if (matches) {
                myWriter = new FileWriter(LEVEL_NAME);
            } else {
                myWriter = new FileWriter(PROFILE_NAME + LEVEL_NAME);
            }

            myWriter.write(MAPX + "," + MAPY + ",");
            myWriter.write("\n");
            for (int y = 0; y < MAPY; y++) {
                for (int x = 0; x < MAPX; x++) {
                    if (x == MAPX - 1) {
                        myWriter.write(stringMap[x][y]);
                    } else {
                        myWriter.write(stringMap[x][y] + " ");
                    }
                }
                myWriter.write("\r\n");
            }
            for (String r : ratList) {
                myWriter.write(r);
            }
            myWriter.write("\n");
            for (String irr : itemsRespawnRate) {
                myWriter.write(irr + ";");
            }
            myWriter.write("\n");
            myWriter.write("" + timeLeft);
            myWriter.write("\n");
            myWriter.write(String.valueOf(lossCondition));
            myWriter.write("\n");
            for (String i : itemList) {
                myWriter.write(i);
            }
            myWriter.write("\n");
            myWriter.write("" + currentScore);
            myWriter.write("\n");

            for (String i : inv) {
                myWriter.write(i + ";");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
