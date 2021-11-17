import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;

public class MainSceneController {

    @FXML
    private RadioMenuItem soundBtn;

    @FXML
    void startGame(ActionEvent event) {
        System.out.println("Start Game");
        // GOTO Level 1
    }

    @FXML
    void exitGame(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void displayAbout(ActionEvent event) {
        // Display About Window
    }

    @FXML
    void displayRules(ActionEvent event) {
        // Display Rules
    }

    @FXML
    void pauseGame(ActionEvent event) {
        // Pause the Game
    }

    @FXML
    void toggleMusic(ActionEvent event) {
        // Toggle Music
    }
}
