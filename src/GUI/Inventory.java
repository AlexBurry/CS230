package GUI;

import Game.ITickHandler;
import Game.Level;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Inventory manager
 * @author Alex
 */
public class Inventory implements ITickHandler {
    private int bombSpawnRate;
    private int mSexChangeSpawnRate;
    private int fSexChangeSpawnRate;
    private int gasSpawnRate;
    private int poisonSpawnRate;
    private int sterilisationSpawnRate;
    private int noEntrySpawnRate;
    private int deathRatSpawnRate;
    private int bombSpawnRateTimer;
    private int mSexChangeSpawnRateTimer;
    private int fSexChangeSpawnRateTimer;
    private int gasSpawnRateTimer;
    private int poisonSpawnRateTimer;
    private int sterilisationSpawnRateTimer;
    private int noEntrySpawnRateTimer;
    private int deathRatSpawnRateTimer;
    private int numberOfBombs;
    private int numberOfMSexChange;
    private int numberOfFSexChange;
    private int numberOfGas;
    private int numberOfSterilisation;
    private int numberOfPoison;
    private int numberOfNoEntry;
    private int numberOfDeathRat;
    private final int MAX_ITEM_NUMBER = 4;

    /**
     * Constructor for inventory
     * @param itemsRespawnRate ArrayList of all item respawn rate values as ints
     */
    public Inventory(ArrayList<String> itemsRespawnRate) {
        setItemRespawnTimers(itemsRespawnRate);
        numberOfMSexChange = 0;
        numberOfFSexChange = 0;
        numberOfGas = 0;
        numberOfSterilisation = 0;
        numberOfPoison = 0;
        numberOfNoEntry = 0;
        bombSpawnRateTimer = bombSpawnRate;
        mSexChangeSpawnRateTimer = mSexChangeSpawnRate;
        fSexChangeSpawnRateTimer = fSexChangeSpawnRate;
        gasSpawnRateTimer = gasSpawnRate;
        poisonSpawnRateTimer = poisonSpawnRate;
        sterilisationSpawnRateTimer = sterilisationSpawnRate;
        noEntrySpawnRateTimer = noEntrySpawnRate;
        deathRatSpawnRateTimer = deathRatSpawnRate;
        Level INSTANCE = Level.getInstance();
        INSTANCE.addListener(this);
    }

    /**
     * Setter for all item respawn rats
     * @param itemsRespawnRate ArrayList of all item respawn rate values as ints
     */
    private void setItemRespawnTimers(ArrayList<String> itemsRespawnRate) {
        for (String iRR : itemsRespawnRate) {
            Scanner token = new Scanner(String.valueOf(iRR));
            token.useDelimiter(",");
            String[] values = new String[2];
            int i = 0;
            while (token.hasNext()) {
                values[i] = token.next();
                i++;
            }
            switch (values[0]) {
                case "b" -> bombSpawnRate = Integer.parseInt(values[1]);
                case "g" -> gasSpawnRate = Integer.parseInt(values[1]);
                case "s" -> sterilisationSpawnRate = Integer.parseInt(values[1]);
                case "p" -> poisonSpawnRate = Integer.parseInt(values[1]);
                case "m" -> mSexChangeSpawnRate = Integer.parseInt(values[1]);
                case "f" -> fSexChangeSpawnRate = Integer.parseInt(values[1]);
                case "n" -> noEntrySpawnRate = Integer.parseInt(values[1]);
                case "d" -> deathRatSpawnRate = Integer.parseInt(values[1]);
                default -> System.out.println("Invalid item char. Check level file.");
            }
        }
    }

    @Override
    public void tickEvent() {
        increaseNumberOfBombs();
        increaseNumberOfMSexChange();
        increaseNumberOfFSexChange();
        increaseNumberOfGas();
        increaseNumberOfSterilisation();
        increaseNumberOfPoison();
        increaseNumberOfNoEntry();
        increaseNumberOfDeathRat();
    }

    public void increaseNumberOfBombs() {
        if (numberOfBombs != MAX_ITEM_NUMBER) {
            if (bombSpawnRateTimer == 0) {
                numberOfBombs++;
                bombSpawnRateTimer = bombSpawnRate;

            }
            else {
                bombSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfBombs() {
        if (numberOfBombs != 0) {
            numberOfBombs--;
        }
    }

    public void increaseNumberOfMSexChange() {
        if (numberOfMSexChange != MAX_ITEM_NUMBER) {
            if (mSexChangeSpawnRateTimer == 0) {
                numberOfMSexChange++;
                mSexChangeSpawnRateTimer = mSexChangeSpawnRate;

            } else {
                mSexChangeSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfMSexChange() {
        if (numberOfMSexChange != 0) {
            numberOfMSexChange--;
        }
    }

    public void increaseNumberOfFSexChange() {
        if (numberOfFSexChange != MAX_ITEM_NUMBER) {
            if (fSexChangeSpawnRateTimer == 0) {
                numberOfFSexChange++;
                fSexChangeSpawnRateTimer = fSexChangeSpawnRate;

            } else {
                fSexChangeSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfFSexChange() {
        if (numberOfFSexChange != 0) {
            numberOfFSexChange--;
        }
    }

    public void increaseNumberOfGas() {
        if (numberOfGas != MAX_ITEM_NUMBER) {
            if (gasSpawnRateTimer == 0) {
                numberOfGas++;
                gasSpawnRateTimer = gasSpawnRate;

            } else {
                gasSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfGas() {
        if (numberOfGas != 0) {
            numberOfGas--;
        }
    }

    public void increaseNumberOfSterilisation() {
        if (numberOfSterilisation != MAX_ITEM_NUMBER) {
            if (sterilisationSpawnRateTimer == 0) {
                numberOfSterilisation++;
                sterilisationSpawnRateTimer = sterilisationSpawnRate;

            } else {
                sterilisationSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfSterilisation() {
        if (numberOfSterilisation != 0) {
            numberOfSterilisation--;
        }
    }

    public void increaseNumberOfPoison() {
        if (numberOfPoison != MAX_ITEM_NUMBER) {
            if (poisonSpawnRateTimer == 0) {
                numberOfPoison++;
                poisonSpawnRateTimer = poisonSpawnRate;

            } else {
                poisonSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfPoison() {
        if (numberOfPoison != 0) {
            numberOfPoison--;
        }
    }

    public void increaseNumberOfNoEntry() {
        if (numberOfNoEntry != MAX_ITEM_NUMBER) {
            if (noEntrySpawnRateTimer== 0) {
                numberOfNoEntry++;
                noEntrySpawnRateTimer = noEntrySpawnRate;

            } else {
                noEntrySpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfNoEntry() {
        if (numberOfNoEntry != 0) {
            numberOfNoEntry--;
        }
    }

    public void increaseNumberOfDeathRat() {
        if (numberOfDeathRat != MAX_ITEM_NUMBER) {
            if (deathRatSpawnRateTimer == 0) {
                numberOfDeathRat++;
                deathRatSpawnRateTimer = deathRatSpawnRate;

            } else {
                deathRatSpawnRateTimer--;
            }
        }
    }

    public void decreaseNumberOfDeathRat() {
        if (numberOfDeathRat != 0) {
            numberOfDeathRat--;
        }
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public int getNumberOfMSexChange() {
        return numberOfMSexChange;
    }

    public int getNumberOfFSexChange() {
        return numberOfFSexChange;
    }

    public int getNumberOfGas() {
        return numberOfGas;
    }

    public int getNumberOfSterilisation() {
        return numberOfSterilisation;
    }

    public int getNumberOfPoison() {
        return numberOfPoison;
    }

    public int getNumberOfNoEntry() {
        return numberOfNoEntry;
    }

    public int getNumberOfDeathRat() {
        return numberOfDeathRat;
    }
}
