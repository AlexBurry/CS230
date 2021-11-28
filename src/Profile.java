import java.io.File;

public class Profile {

    private String name;
    private int score;
    private int currentLevel;
    private File highscoreDatabase = new File("highscoreDatabase.txt");
    private File playerDatabase = new File("playerDatabase.txt");

    public Profile(String name, int score, int currentLevel) {
        this.name = name;
        this.score = score;
        this.currentLevel = currentLevel;
    }

    public void createProfile() {
        // Adds the player to the database if the name is unique. (Not case sensitive)
    }

    public void checkHighscore() {
        // Checks if player has a score higher than his personal best on a level.
    }

    public void addHighscore() {
        // Adds new personal best to the highscore table if its in the top 10.
    }





}
