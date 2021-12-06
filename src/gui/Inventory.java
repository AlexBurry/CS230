package gui;

import game.ITickHandler;
import game.Level;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Inventory manager
 *
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
     *
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
     *
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

    /**
     * Checks if the inventory should increase the number of items
     *
     * @param count how many ticks have passed? Resets every second.
     */
    @Override
    public void tickEvent(int count) {

        if (count >= 4) {
            increaseNumberOfBombs();
            increaseNumberOfMSexChange();
            increaseNumberOfFSexChange();
            increaseNumberOfGas();
            increaseNumberOfSterilisation();
            increaseNumberOfPoison();
            increaseNumberOfNoEntry();
            increaseNumberOfDeathRat();
        }

    }

    /**
     * Increases number of Bombs
     */
    public void increaseNumberOfBombs() {
        if (numberOfBombs != MAX_ITEM_NUMBER) {
            if (bombSpawnRateTimer == 0) {
                numberOfBombs++;
                bombSpawnRateTimer = bombSpawnRate;

            } else {
                bombSpawnRateTimer--;
            }
        }
    }

    /**
     * Decreases number of Bombs
     */
    public void decreaseNumberOfBombs() {
        if (numberOfBombs != 0) {
            numberOfBombs--;
        }
    }

    /**
     * Increases number of Male sex changes
     */
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

    /**
     * Decreases number of Male sex changes
     */
    public void decreaseNumberOfMSexChange() {
        if (numberOfMSexChange != 0) {
            numberOfMSexChange--;
        }
    }

    /**
     * Increases number of Female sex changes
     */
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

    /**
     * Decreases number of Female sex changes
     */
    public void decreaseNumberOfFSexChange() {
        if (numberOfFSexChange != 0) {
            numberOfFSexChange--;
        }
    }

    /**
     * Increases number of Gas
     */
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

    /**
     * Decreases number of Gas
     */
    public void decreaseNumberOfGas() {
        if (numberOfGas != 0) {
            numberOfGas--;
        }
    }

    /**
     * Increases number of Sterilisation
     */
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

    /**
     * Decreases number of Sterilisation
     */
    public void decreaseNumberOfSterilisation() {
        if (numberOfSterilisation != 0) {
            numberOfSterilisation--;
        }
    }

    /**
     * Increases number of Poison
     */
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

    /**
     * Decreases number of Poison
     */
    public void decreaseNumberOfPoison() {
        if (numberOfPoison != 0) {
            numberOfPoison--;
        }
    }

    /**
     * Increases number of No Entry Signs
     */
    public void increaseNumberOfNoEntry() {
        if (numberOfNoEntry != MAX_ITEM_NUMBER) {
            if (noEntrySpawnRateTimer == 0) {
                numberOfNoEntry++;
                noEntrySpawnRateTimer = noEntrySpawnRate;

            } else {
                noEntrySpawnRateTimer--;
            }
        }
    }

    /**
     * Decreases number of No Entry signs
     */
    public void decreaseNumberOfNoEntry() {
        if (numberOfNoEntry != 0) {
            numberOfNoEntry--;
        }
    }

    /**
     * Increases number of Death rats
     */
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

    /**
     * Decreases number of Death rats
     */
    public void decreaseNumberOfDeathRat() {
        if (numberOfDeathRat != 0) {
            numberOfDeathRat--;
        }
    }

    /**
     * Gets the number of bombs
     *
     * @return int value of bombs
     */
    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    /**
     * Gets the number of male sex changes
     *
     * @return int value of male sex changes
     */
    public int getNumberOfMSexChange() {
        return numberOfMSexChange;
    }

    /**
     * Gets the number of female sex changes
     *
     * @return int value of female sex changes
     */
    public int getNumberOfFSexChange() {
        return numberOfFSexChange;
    }

    /**
     * Gets the number of gas items
     *
     * @return int value of gas
     */
    public int getNumberOfGas() {
        return numberOfGas;
    }

    /**
     * Gets the number of sterilisation
     *
     * @return int value of sterilsiation
     */
    public int getNumberOfSterilisation() {
        return numberOfSterilisation;
    }

    /**
     * Gets the number of poison
     *
     * @return int value of poison
     */
    public int getNumberOfPoison() {
        return numberOfPoison;
    }

    /**
     * Gets the number of No entry signs
     *
     * @return int value of no entry signs
     */
    public int getNumberOfNoEntry() {
        return numberOfNoEntry;
    }

    /**
     * Gets the number of Death rats
     *
     * @return int value of death rats
     */
    public int getNumberOfDeathRat() {
        return numberOfDeathRat;
    }

    /**
     * set number of bombs
     *
     * @param numberOfBombs int
     */
    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

    /**
     * sets number of male sex changes
     *
     * @param numberOfMSexChange int
     */
    public void setNumberOfMSexChange(int numberOfMSexChange) {
        this.numberOfMSexChange = numberOfMSexChange;
    }

    /**
     * Sests number of female sex changes
     *
     * @param numberOfFSexChange int
     */
    public void setNumberOfFSexChange(int numberOfFSexChange) {
        this.numberOfFSexChange = numberOfFSexChange;
    }

    /**
     * sets number of gas
     *
     * @param numberOfGas int
     */
    public void setNumberOfGas(int numberOfGas) {
        this.numberOfGas = numberOfGas;
    }

    /**
     * sets number of sterilisation
     *
     * @param numberOfSterilisation int
     */
    public void setNumberOfSterilisation(int numberOfSterilisation) {
        this.numberOfSterilisation = numberOfSterilisation;
    }

    /**
     * sets number of poison
     *
     * @param numberOfPoison int
     */
    public void setNumberOfPoison(int numberOfPoison) {
        this.numberOfPoison = numberOfPoison;
    }

    /**
     * sets number of no entry signs
     *
     * @param numberOfNoEntry int
     */
    public void setNumberOfNoEntry(int numberOfNoEntry) {
        this.numberOfNoEntry = numberOfNoEntry;
    }

    /**
     * Sets number of death rats
     *
     * @param numberOfDeathRat int
     */
    public void setNumberOfDeathRat(int numberOfDeathRat) {
        this.numberOfDeathRat = numberOfDeathRat;
    }
}
