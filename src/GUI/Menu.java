package GUI;

import Game.Level;
import Game.Profile;
import Game.ReadFile;
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
    private boolean loggedIn = false;

    public Menu(Stage primaryStage) {
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

        Label playLbl = presetLabel("Play", font, 70);
        Label profileLbl = presetLabel("Profile", font, 60);
        Label highscoreLbl = presetLabel("Highscore", font, 45);
        Label exitLbl = presetLabel("Exit", font, 40);
        playLbl.setTextFill(Color.BLACK);
        profileLbl.setTextFill(Color.BLACK);
        highscoreLbl.setTextFill(Color.BLACK);
        exitLbl.setTextFill(Color.BLACK);

        playLbl.setOnMouseClicked(a -> {
            try {
                buildLoginUI(primaryStage, "l");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        profileLbl.setOnMouseClicked(mouseEvent -> {
            try {
                buildLoginUI(primaryStage, "p");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        highscoreLbl.setOnMouseClicked(mouseEvent -> buildHighScore());
        exitLbl.setOnMouseClicked(mouseEvent -> System.exit(0));

        VBox options = new VBox();

        options.setAlignment(Pos.CENTER);
        options.setPadding(new Insets(100, 40, 40, 40));
        options.getChildren().addAll(playLbl,profileLbl, highscoreLbl, exitLbl);

        //menuPane.setTop(//MessageOfTheDayLbl); Message Of The Day Here!

        //menuPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        BackgroundImage image = new BackgroundImage(new Image("Sprites/MenuBasic.png"),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,
                new BackgroundSize(1200,104,true,true,true,false));
        menuPane.setBackground(new Background(image));

        menuPane.setCenter(options);

        Scene scene = new Scene(menuPane, 1200, 884);
        presetStage(primaryStage, "Sprites/raticon.png", "Rats: Menu", scene);
    }

    /**
     * This method creates a login "form" using a GridPane Layout
     * and allows the player to access the GUI.Menu through it.
     *
     * @returns Formatted GridPane.
     */
    public void buildLoginUI(Stage primaryStage, String name) throws FileNotFoundException {
        GridPane gPane = new GridPane();

        Label userLbl = new Label("Enter Username!");
        userLbl.setTextFill(Color.BLACK);
        TextField inputField = new TextField("");
        Button enterBtn = new Button("Enter");

        userLbl.setFont(new Font("Comic Sans", 36));
        inputField.setPrefSize(100,10);
        enterBtn.setPrefSize(50,20);

        GridPane.setHalignment(userLbl, HPos.CENTER);
        GridPane.setHalignment(enterBtn, HPos.RIGHT);
        gPane.add(userLbl, 0, 0);
        gPane.add(inputField, 0, 1);
        gPane.add(enterBtn, 0, 2);

        gPane.setVgap(5);
        gPane.setAlignment(Pos.CENTER);

        System.out.println(loggedIn);

        Scene scene = new Scene(gPane, 1200, 884);

        BackgroundImage image = new BackgroundImage(new Image("Sprites/MenuBasic.png"),BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,
                new BackgroundSize(1200,104,true,true,true,false));
        gPane.setBackground(new Background(image));

        presetStage(primaryStage, "Sprites/raticon.png", "Rats: Login", scene);

        if (!loggedIn) {
            enterBtn.setOnAction(mouseEvent ->
            {
                try {
                    loginProcess(inputField, primaryStage, name);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            switch (name) {
                case "l" -> buildLevel(primaryStage);
                case "p" -> buildProfile(primaryStage);
            }
        }
    }

    public void loginProcess(TextField inputField, Stage primaryStage, String name) throws FileNotFoundException {
        if (!inputField.getText().isEmpty() && !inputField.getText().contains(",") || skip) {
            System.out.println(inputField.getText());
            if (skip) {
                p = new Profile("test");
            } else {
                p = new Profile(inputField.getText());
            }
            loggedIn = true;
            switch (name) {
                case "l" -> buildLevel(primaryStage);
                case "p" -> buildProfile(primaryStage);
            }

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

        Level newLevel = new ReadFile("test_level.txt", primaryStage).newLevel();
    }

    public void buildProfile(Stage primaryStage) {
        GridPane gPane = new GridPane();

        Label username = presetLabel("Username:" + p.getName(), font, 24);
        Label currentLevel = presetLabel("Currently On Level:" + p.getCurrentLevel(), font, 24);
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

        Scene scene = new Scene(gPane, 1200, 884);
        presetStage(primaryStage, "Sprites/raticon.png", "Rats: Menu", scene);
    }

    public void buildHighScore() {
        System.out.println("Work in Progress...");
    }

}
