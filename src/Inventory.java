import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {
    private int bombSpawnRate;
    private int mSexChangeSpawnRate;
    private int fSexChangeSpawnRate;
    private int gasSpawnRate;
    private int poisonSpawnRate;
    private int sterilisationSpawnRate;
    private int noEntrySpawnRate;
    private int deathRatSpawnRate;
    private int numberOfBombs;
    private int numberOfMSexChange;
    private int numberOfFSexChange;
    private int numberOfGas;
    private int numberOfSterilisation;
    private int numberOfPoison;
    private int numberOfNoEntry;
    private final int MAX_ITEM_NUMBER = 4;

    public Inventory(ArrayList<String> itemsRespawnRate) {
        setItemRespawnTimers(itemsRespawnRate);
        numberOfMSexChange = 0;
        numberOfFSexChange = 0;
        numberOfGas = 0;
        numberOfSterilisation = 0;
        numberOfPoison = 0;
        numberOfNoEntry = 0;
    }

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

    public void respawnItems() {
        //TODO: Implement

    }

    public void increaseNumberOfBombs() {
        if (numberOfBombs != MAX_ITEM_NUMBER) {
            numberOfBombs++;
        }
    }

    public void decreaseNumberOfBombs() {
        if (numberOfBombs != 0) {
            numberOfBombs--;
        }
    }

    public void increaseNumberOfMSexChange() {
        if (numberOfMSexChange != MAX_ITEM_NUMBER) {
            numberOfMSexChange++;
        }
    }

    public void decreaseNumberOfMSexChange() {
        if (numberOfMSexChange != 0) {
            numberOfMSexChange--;
        }
    }

    public void increaseNumberOfFSexChange() {
        if (numberOfFSexChange != MAX_ITEM_NUMBER) {
            numberOfFSexChange++;
        }
    }

    public void decreaseNumberOfFSexChange() {
        if (numberOfFSexChange != 0) {
            numberOfFSexChange--;
        }
    }

    public void increaseNumberOfGas() {
        if (numberOfGas != MAX_ITEM_NUMBER) {
            numberOfGas++;
        }
    }

    public void decreaseNumberOfGas() {
        if (numberOfGas != 0) {
            numberOfGas--;
        }
    }

    public void increaseNumberOfSterilisation() {
        if (numberOfSterilisation != MAX_ITEM_NUMBER) {
            numberOfSterilisation++;
        }
    }

    public void decreaseNumberOfSterilisation() {
        if (numberOfSterilisation != 0) {
            numberOfSterilisation--;
        }
    }

    public void increaseNumberOfPoison() {
        if (numberOfPoison != MAX_ITEM_NUMBER) {
            numberOfPoison++;
        }
    }

    public void decreaseNumberOfPoison() {
        if (numberOfPoison != 0) {
            numberOfPoison--;
        }
    }

    public void increaseNumberOfNoEntry() {
        if (numberOfNoEntry != MAX_ITEM_NUMBER) {
            numberOfNoEntry++;
        }
    }

    public void decreaseNumberOfNoEntry() {
        if (numberOfNoEntry != 0) {
            numberOfNoEntry--;
        }
    }
}
