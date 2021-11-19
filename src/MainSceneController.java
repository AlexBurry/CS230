import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainSceneController {

    @FXML
    private RadioMenuItem soundBtn;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Pane mainPane;

    // private ArrayList<TestItem> itemList;
    private double x = 100;
    private double y = 100;
    private double width = 50;
    private double height = 50;

    @FXML
    public void initialize() {
        Rectangle r = new Rectangle();
        TestItem item1 = new TestItem(x, y, width, height, r);
        mainPane.getChildren().add(r);
        item1.draw();

        r.setOnMouseClicked(event -> pressed(event, item1));
        r.setOnMouseDragged(event -> dragged(event, item1));
        r.setOnMouseReleased(event -> released(event, item1));
    }

    public void pressed(MouseEvent event, TestItem item) {
        item.setColor(Color.RED);
    }

    public void dragged(MouseEvent event, TestItem item) {
        item.setX(item.getX() + event.getX() - width / 2);
        item.setY(item.getY() + event.getY() - height / 2);
        outOfBounds(item);
        item.draw();
    }

    public void released(MouseEvent event, TestItem item) {
        item.setColor(Color.BLACK);
    }

    public void outOfBounds(TestItem item) {
        if (item.getX() > mainPane.getWidth() || item.getX() + item.getWidth() < 0) {
            item.setX(mainPane.getWidth() / 2 - item.getWidth() / 2);
        }
        if (item.getY() > mainPane.getHeight() || item.getY() - menuBar.getHeight() / 2 < 0) {
            item.setY(mainPane.getHeight() / 2 - item.getHeight() / 2);
        }
    }

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
