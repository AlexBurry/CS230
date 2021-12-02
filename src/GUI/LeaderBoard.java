package GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderBoard {

    private int currentLevel;

    public void endGame(int finalScore) throws IOException{
    List<String> scores = retrieveScores();
    showLeaderBoard(scores);
}

    private List<String> retrieveScores() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Lvl".concat(String.valueOf(currentLevel).concat(".txt"))));
        String scoreLine = reader.readLine(); // read line that contains scores
        List<String> scores = new ArrayList<>();
        if (scoreLine != null) { // in case of first game
            String[] tempScore = scoreLine.split(", ");
            scores = new ArrayList<>(Arrays.asList(tempScore));
        }
        reader.close();
        return scores;
    }

    private void showLeaderBoard(List<String> scores) throws IOException{
        System.out.println("*** LEADERBOARD ***");
            for(int i=0; i< scores.size(); i++){
                System.out.println(scores.get(i));
        }
        System.out.println("*** LEADERBOARD ***");
    }
}
