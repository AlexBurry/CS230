import gui.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Menu m = new Menu(primaryStage);
        m.buildMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
