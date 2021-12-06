package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class' purpose is to keep details about the Player.
 * It also serves to create files to store the high scores per level
 * and update those files based on the Player's achievements.
 *
 * @author Dominik
 * @version 1.0
 */
public class Profile {

    private String name;
    private int score;
    private int currentLevel = 1;
    private int highestLevelUnlocked;
    private ArrayList<Integer> levelsSavedTo = new ArrayList<>();
    private File playerDatabase = new File("src/playerDatabase.txt");
    private File Lvl;

    public Profile(String name) {
        this.name = name;
        if (!profileExists(name)) {
            createProfile();
        } else {
            getProfile(name);
        }
    }

    /**
     * Searches the playerDatabase for the value stored in name.
     *
     * @param name the variable that stores the player's name.
     * @return true if the profile exists, false otherwise.
     */
    public boolean profileExists(String name) {
        boolean exists = false;
        String lineRead;
        String tempArray[];
        try {
            FileReader reader = new FileReader(playerDatabase);
            BufferedReader buffReader = new BufferedReader(reader);
            while ((lineRead = buffReader.readLine()) != null) {
                tempArray = lineRead.split(",");
                if ((tempArray[0]).equalsIgnoreCase(name)) {
                    exists = true;
                }
            }
            buffReader.close();
        } catch (Exception e) {
            System.out.println("Oops. Couldn't Check If The Game.Profile Exists.");
        }
        return exists;
    }

    /**
     * Finds the Player in playerDatabase and adds their
     * saves to their record.
     *
     * @param name stores the Player that saved.
     */
    public void saveToProfile(String name) {
        ArrayList<String> tempList = new ArrayList<>();
        String lineRead;
        String levelsSaved = "";
        for (int i = 0; i < levelsSavedTo.size(); i++) {
            levelsSaved += levelsSavedTo.get(i) + ",";
        }
        try {
            FileReader reader = new FileReader(playerDatabase);
            BufferedReader buffReader = new BufferedReader(reader);
            while ((lineRead = buffReader.readLine()) != null) {
                String tempArray[] = lineRead.split(",");
                if (!tempArray[0].equalsIgnoreCase(name)) {
                    tempList.add(lineRead);
                } else {
                    tempList.add(tempArray[0] + ","
                            + tempArray[1] + ","
                            + tempArray[2] + ","
                            + tempArray[3] + ","
                            + levelsSaved);
                }
            }
            FileWriter emptyFile = new FileWriter(playerDatabase, false);
            FileWriter writer = new FileWriter(playerDatabase, true);
            BufferedWriter buffWriter = new BufferedWriter(writer);
            emptyFile.write("");
            for (int i = 0; i < tempList.size(); i++) {
                writer.write(tempList.get(i) + System.lineSeparator());
            }
            emptyFile.close();
            buffReader.close();
            buffWriter.close();
        } catch (Exception e) {
            System.out.println("Failed To Delete From Highscore!");
        }
    }

    /**
     * Finds the Profile using the name as their unique identifier
     * and loads in all the information the player has.
     *
     * @param name used as primary key.
     */
    public void getProfile(String name) {
        String lineRead;
        String tempArray[];
        try {
            FileReader reader = new FileReader(playerDatabase);
            BufferedReader buffReader = new BufferedReader(reader);
            while ((lineRead = buffReader.readLine()) != null) {
                tempArray = lineRead.split(",");
                if (tempArray[0].equalsIgnoreCase(name)) {
                    currentLevel = Integer.parseInt(tempArray[1]);
                    highestLevelUnlocked = Integer.parseInt(tempArray[2]);
                    score = Integer.parseInt(tempArray[3]);
                    //for saving which levels have been saved to.
                    levelsSavedTo = new ArrayList<>();
                    for (int i = 4; i < tempArray.length; i++) {
                        //start at 4, because we have already iterated to 3.
                        levelsSavedTo.add(Integer.parseInt(tempArray[i]));
                    }

                }
            }
            buffReader.close();
        } catch (Exception e) {
            System.out.println("Oops. Couldn't Get The Game.Profile!");
        }
    }

    /**
     * This method creates a brand-new Profile.
     * currentLevel and highestLevelUnlocked is
     * set to 1 and score is set to 0 by default.
     */
    public void createProfile() {
        try {
            FileWriter writer = new FileWriter(playerDatabase, true);
            BufferedWriter buffWriter = new BufferedWriter(writer);
            buffWriter.write("\n" + name + "," + "1,1,0");
            highestLevelUnlocked = 1;
            buffWriter.close();
        } catch (Exception e) {
            System.out.println("Oops. Couldn't Open Player Database!");
        }
    }

    /**
     * This method is called to check if the player has achieved
     * a new high score when they clear the level.
     * The method checks if the player has an existing high score
     * or not and updates the table accordingly.
     */
    public void checkHighscore() {
        Scanner scan;
        boolean greaterThanLeaderboard = false;
        boolean notDuplicate = true;
        try {
            if (!makeFile().isFile()) {
                Lvl = makeFile();
                intializeFile(Lvl);
            } else {
                Lvl = new File("Lvl".concat(String.valueOf(currentLevel).concat(".txt")));
            }
            scan = new Scanner(Lvl);
            while (scan.hasNextLine()) {
                String lineRead = scan.nextLine();
                String tempArray[] = lineRead.split(",");
                if (tempArray[1].equalsIgnoreCase(name)) {
                    notDuplicate = false;
                    deleteFromHighscore(name);
                    addHighscore();
                } else if (Integer.parseInt(tempArray[3]) < score) {
                    greaterThanLeaderboard = true;
                }
            }
            if (greaterThanLeaderboard == true && notDuplicate == true) {
                addHighscore();
            }
            scan.close();
        } catch (Exception e) {
            System.out.println("Failed To Open Highscore Database!");
        }
    }

    /**
     * This method removes the score if the player has achieved a higher score.
     *
     * @param name contains the name that is being removed.
     */
    public void deleteFromHighscore(String name) {
        ArrayList<String> tempList = new ArrayList<>();
        String lineRead;
        try {
            FileReader reader = new FileReader(Lvl);
            BufferedReader buffReader = new BufferedReader(reader);
            while ((lineRead = buffReader.readLine()) != null) {
                String tempArray[] = lineRead.split(",");
                if (!tempArray[1].equalsIgnoreCase(name)) {
                    tempList.add(tempArray[0] + ","
                            + tempArray[1] + ","
                            + tempArray[2] + ","
                            + tempArray[3]);
                }
            }
            FileWriter emptyFile = new FileWriter(Lvl, false);
            FileWriter writer = new FileWriter(Lvl, true);
            BufferedWriter buffWriter = new BufferedWriter(writer);
            emptyFile.write("");
            for (int i = 0; i < tempList.size(); i++) {
                writer.write(tempList.get(i) + System.lineSeparator());
            }
            emptyFile.close();
            buffReader.close();
            buffWriter.close();
        } catch (Exception e) {
            System.out.println("Failed To Delete From Highscore!");
        }
    }

    /**
     * Adds a new high score into the table
     * and rearranges the existing ones.
     */
    public void addHighscore() {
        ArrayList<String> tempList = new ArrayList<>();
        int leaderBoardRank = 1;
        boolean notAdded = true;
        String lineRead;
        try {
            FileReader reader = new FileReader(Lvl);
            BufferedReader buffReader = new BufferedReader(reader);
            while ((lineRead = buffReader.readLine()) != null) {
                String tempArray[] = lineRead.split(",");
                if (Integer.parseInt(tempArray[3]) < score && notAdded == true) {
                    notAdded = false;
                    tempList.add(String.valueOf(leaderBoardRank++) + ","
                            + name + "," + String.valueOf(currentLevel) + ","
                            + String.valueOf(score));
                    tempList.add(String.valueOf(leaderBoardRank++) + ","
                            + tempArray[1] + "," + tempArray[2]
                            + "," + tempArray[3]);
                } else {
                    tempList.add(String.valueOf(leaderBoardRank++) + ","
                            + tempArray[1] + "," + tempArray[2]
                            + "," + tempArray[3]);
                }
            }
            FileWriter emptyFile = new FileWriter(Lvl, false);
            FileWriter writer = new FileWriter(Lvl, true);
            BufferedWriter buffWriter = new BufferedWriter(writer);
            emptyFile.write("");
            for (int i = 0; i < 10; i++) {
                writer.write(tempList.get(i) + System.lineSeparator());
            }
            emptyFile.close();
            buffReader.close();
            buffWriter.close();
        } catch (Exception e) {
            System.out.println("Failed To Add To Highscore!");
        }
    }

    /**
     * Creates a file for the level.
     *
     * @return the created File object.
     */
    public File makeFile() {
        return new File("Lvl".concat(String.valueOf(currentLevel).concat(".txt")));
    }

    /**
     * Initialises the file so the leaderboard is not empty.
     *
     * @param file contains the File object that is initialised.
     */
    public void intializeFile(File file) {
        int leaderBoardRank = 1;
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(writer);
            for (int i = 0; i < 10; i++) {
                buffWriter.write(leaderBoardRank++ + ",None," + currentLevel + ",0" + System.lineSeparator());
            }
            buffWriter.close();
        } catch (Exception e) {
            System.out.println("Failed To Initialize File.");
        }
    }

    /**
     * This method returns the player's name.
     *
     * @return player name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the player's score
     *
     * @returns player score.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method returns the level the player is currently on.
     *
     * @returns player current level.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * This method returns the highest level the player has unlocked.
     *
     * @return player the highest level unlocked.
     */
    public int getHighestLevelUnlocked() {
        return highestLevelUnlocked;
    }

    public ArrayList<Integer> getLevelsSavedTo() {
        return levelsSavedTo;
    }

    public void addToSavedLevels(int i){
        if(!levelsSavedTo.contains(i)){
            levelsSavedTo.add(i);
        }
    }

    public void setScore(int addScore) {
        score += addScore;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setHighestLevelUnlocked(int highestLevelUnlocked) {
        this.highestLevelUnlocked = highestLevelUnlocked;
    }
}
