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
    public void start(Stage primaryStage) {

//        Parent root;
//        try {
//            root = FXMLLoader.load(getClass().getResource("FXML FILES/MainScene.fxml"));
//            Scene scene = new Scene(root);
//
//            primaryStage.setTitle("Rats");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch (IOException e) {
//        }

        Tile[][] tilemap = new Tile[4][4];
        Canvas canvas = new Canvas(800, 800);
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                String type = "g";
                Tile test = new Tile(type, x, y, canvas);
                tilemap[x][y] = test;
            }
        }

        //Board testBoard = new Board();
        Board testBoard = new Board(tilemap, 4, 4);
        testBoard.start(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}