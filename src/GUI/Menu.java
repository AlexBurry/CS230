package GUI;

import Game.Level;
import Game.Profile;
import Game.ReadFile;
import ItemClasses.BombItem;
import ItemClasses.MFChange;
import ItemClasses.FMChange;
import ItemClasses.SteriliseItem;
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
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void buildMenu() {
        BorderPane menuPane = new BorderPane();

        Label playLbl = presetLabel("Play", font, 72);
        Label profileLbl = presetLabel("Profile", font, 36);
        Label highscoreLbl = presetLabel("Highscore", font, 36);
        Label exitLbl = presetLabel("Exit", font, 36);

        playLbl.setOnMouseClicked(a -> {
            try {
                buildLevel(primaryStage);
            } catch (FileNotFoundException e) {
                System.out.println("Failed To Build Map!");
            }
        });
        profileLbl.setOnMouseClicked(mouseEvent -> buildProfile(primaryStage));
        highscoreLbl.setOnMouseClicked(mouseEvent -> buildHighScore());
        exitLbl.setOnMouseClicked(mouseEvent -> System.exit(0));

        VBox options = new VBox();
        options.setPadding(new Insets(10, 10, 10, 10));
        options.setAlignment(Pos.CENTER);
        options.getChildren().addAll(playLbl, highscoreLbl, profileLbl, exitLbl);

        //menuPane.setTop(//MessageOfTheDayLbl); Message Of The Day Here!
        menuPane.setCenter(options);

        menuPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(menuPane, 400, 400);
        presetStage(primaryStage, "Sprites/raticon.png", "Rats: Menu", scene);
    }

    /**
     * This method creates a login "form" using a GridPane Layout
     * and allows the player to access the GUI.Menu through it.
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

        enterBtn.setOnAction(mouseEvent -> loginProcess(inputField));

        Scene scene = new Scene(gPane, 300, 150);
        presetStage(primaryStage, "Sprites/raticon.png", "Rats: Login", scene);
    }

    public void loginProcess(TextField inputField) {
        if (!inputField.getText().isEmpty() && inputField.getText().contains(",") || skip) {
            if(skip){p = new Profile("test");}else{p = new Profile(inputField.getText());}
            buildMenu();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username!", "Try Again!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This method creates the Level if the user
     * entered an acceptable username.
     *
     * @param primaryStage
     * @throws FileNotFoundException
     */
    public void buildLevel(Stage primaryStage) throws FileNotFoundException {
            primaryStage.setTitle("Rats: Steampunk Edition");
            primaryStage.getIcons().add(new Image("Sprites/raticon.png"));

            Level newLevel = new ReadFile("level_1.txt", primaryStage).newLevel();
            

    }

    public void buildProfile(Stage primaryStage) {
        GridPane gPane = new GridPane();

        Label username = presetLabel("Username:" + p.getName(), font, 24);
        Label currentLevel = presetLabel("Currentln On Level:" + p.getCurrentLevel(), font, 24);
        Label highestLevel = presetLabel("Highest Level Cleared:" + p.getHighestLevelUnlocked(), font, 24);
        Label score = presetLabel("Score: " + p.getScore(), font, 24);
        Button backBtn = new Button("Back");

        GridPane.setHalignment(username, HPos.CENTER);
        GridPane.setHalignment(currentLevel, HPos.CENTER);
        GridPane.setHalignment(highestLevel, HPos.CENTER);
        GridPane.setHalignment(score, HPos.CENTER);
        GridPane.setHalignment(backBtn, HPos.CENTER);
        gPane.add(username, 0, 0);
        gPane.add(currentLevel, 0, 1);
        gPane.add(highestLevel, 0, 2);
        gPane.add(score, 0, 3);
        gPane.add(backBtn, 0, 4);

        gPane.setVgap(5);
        gPane.setAlignment(Pos.CENTER);

        backBtn.setOnAction(mouseEvent -> buildMenu());

        Scene scene = new Scene(gPane, 400, 400);
        presetStage(primaryStage, "Sprites/raticon.png", "Rats: Menu", scene);
    }

    public void buildHighScore() {
        System.out.println("Work in Progress...");
    }

}
