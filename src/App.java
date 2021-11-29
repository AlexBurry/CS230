import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;

public class App extends Application {



    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Canvas canvas = new Canvas(1200, 800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Rats: DLC-less Edition");
        primaryStage.getIcons().add(new Image("raticon.png"));
//        testBoard.addRat(new Rat(false, false, false,true, false));

        Level newLevel = new ReadFile("level_1.txt", primaryStage).newLevel();
        Item newItem = new SexChangeItem(2,1);
        Item newItem2 = new SexChangeItem(2,2);
        newLevel.getLevelBoard().addItemToMap(2,1,newItem);
        newLevel.getLevelBoard().addItemToMap(2,2,newItem2);
        newLevel.getLevelBoard().removeItemFromMap(2,1,newItem);
    }

    public static void main(String[] args) {
        launch(args);
    }
}