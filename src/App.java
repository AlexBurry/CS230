import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;

public class App extends Application {

    Profile p;
    boolean skip = true;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Pane loginUI = buildLoginUI(primaryStage);
        Scene scene = new Scene(loginUI, 300, 150);
        primaryStage.getIcons().add(new Image("raticon.png"));
        primaryStage.setTitle("Rats: Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Opens the Level, need to create new Menu or revamp old Menu.
    /**
     * This method creates a login "form" using a GridPane Layout
     * and allows the player to access the Menu through it.
     *
     * @param primaryStage
     * @returns Formatted GridPane.
     */
    public Pane buildLoginUI(Stage primaryStage) {
        GridPane gPane = new GridPane();


        Label userLbl = new Label("Enter Username!");
        TextField inputField = new TextField("");
        Button enterBtn = new Button("Enter");

        GridPane.setHalignment(userLbl, HPos.CENTER);
        GridPane.setHalignment(enterBtn, HPos.RIGHT);
        gPane.add(userLbl, 0, 0);
        gPane.add(inputField, 0, 1);
        gPane.add(enterBtn, 0, 2);

        gPane.setVgap(5);
        gPane.setAlignment(Pos.CENTER);

        enterBtn.setOnAction(a -> {
            try {
                buildLevel(primaryStage, inputField);
            } catch (FileNotFoundException e) {
                System.out.println("Failed To Build Map!");
            }
        });

        return gPane;
    }

    /**
     * This method creates the Level if the user
     * entered an acceptable username.
     *
     * @param primaryStage
     * @param inputField
     * @throws FileNotFoundException
     */
    public void buildLevel(Stage primaryStage, TextField inputField) throws FileNotFoundException {

        if (!inputField.getText().isEmpty() || skip) {
            if(skip){p = new Profile("test");}else{p = new Profile(inputField.getText());}
            primaryStage.setTitle("Rats: Steampunk Edition");
            primaryStage.getIcons().add(new Image("raticon.png"));

            Level newLevel = new ReadFile("level_1.txt", primaryStage).newLevel();


           // newLevel.getLevelBoard().addRat(new Rat('f', false,true, false, 2, 5, 3));
            //newLevel.getLevelBoard().addItem(new SexChangeItem(2,4));
            newLevel.getLevelBoard().addItem(new BombItem(2,4));


        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username!", "Try Again!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {



        launch(args);
    }
}