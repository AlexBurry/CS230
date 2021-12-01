import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Menu {

    private Stage primaryStage;
    private Profile p;
    private boolean skip = true;
    private String font = "Comic Sans";

    public Menu (Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Label presetLabel(String labelName, String font, int size) {
        Label lbl = new Label(labelName);
        lbl.setFont(new Font(font, size));
        return lbl;
    }

    public void presetStage(Stage primaryStage, String iconPath, String title, Scene scene) {
        primaryStage.getIcons().add(new Image(iconPath));
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void buildMenu() {
        BorderPane menuPane = new BorderPane();

        Label playLbl = presetLabel("Play", font, 72);
        Label highScoreLbl = presetLabel("Profile", font, 36);
        Label exitLbl = presetLabel("Exit", font, 36);

        playLbl.setOnMouseClicked(mouseEvent -> buildLoginUI());
        highScoreLbl.setOnMouseClicked(mouseEvent -> buildHighScore());
        exitLbl.setOnMouseClicked(mouseEvent -> System.exit(0));

        VBox options = new VBox();
        options.setPadding(new Insets(10, 10, 10, 10));
        options.setAlignment(Pos.CENTER);
        options.getChildren().addAll(playLbl, highScoreLbl, exitLbl);

        //menuPane.setTop(//MessageOfTheDayLbl); Message Of The Day Here!
        menuPane.setCenter(options);

        menuPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(menuPane, 400, 400);
        presetStage(primaryStage, "raticon.png", "Rats: Menu", scene);
    }

    /**
     * This method creates a login "form" using a GridPane Layout
     * and allows the player to access the Menu through it.
     *
     * @returns Formatted GridPane.
     */
    public void buildLoginUI() {
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

        Scene scene = new Scene(gPane, 300, 150);
        presetStage(primaryStage, "raticon.png", "Rats: Login", scene);
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
            new BombItem(2,4);

        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username!", "Try Again!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void buildHighScore() {
        System.out.println("Work in Progress...");
    }

}
