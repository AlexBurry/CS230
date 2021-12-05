package GUI;

import Game.Level;
import Game.Profile;
import Game.ReadFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * This class creates and handles the Level Selector.
 * It loads only levels available to the Player based
 * on what the player has unlocked.
 *
 * @author Dominik
 * @version 1.0
 * @since 1.0
 */

public class LevelSelector {

    private Stage primaryStage;
    private Profile p;
    private Menu mObject;
    private int highestUnlocked = 1;

    /**
     * This method is used to initialise objects.
     *
     * @param primaryStage initialises primaryStage.
     * @param p initialises Profile.
     */
    public LevelSelector(Stage primaryStage, Profile p) {
        this.primaryStage = primaryStage;
        mObject = new Menu(primaryStage);
        this.p = p;
    }

    /**
     * This method creates a Pane, sets it to "Locked" both visually
     * and physically. A Pane is "Unlocked" when the player has beaten
     * the previous level.
     * "Unlocked" Panes start the level, "Locked" ones do not.
     *
     * @param primaryStage the Stage that is displayed for the user.
     * @param selectionLvl the level that the player is interacting with.
     * @return formatted Pane with an embedded MouseEvent
     */
    public Pane makeLevelPane(Stage primaryStage, int selectionLvl) {
        Pane lvl = new Pane();

        BackgroundImage image = new BackgroundImage(new Image("Sprites/metalTile.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(125, 125, true, true, true, false));

        lvl.setBackground(new Background(image));

        if (highestUnlocked <= p.getHighestLevelUnlocked()) {
            lvl = revealLevels(lvl, highestUnlocked);
            highestUnlocked++;
        }

        lvl.setPrefSize(125, 125);

        switch (selectionLvl) {
            case 0 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (1 <= p.getHighestLevelUnlocked()) {
                        buildLevel(primaryStage, "level_0.txt",0);
                    } else {
                        System.out.println("This Player Has Yet To " +
                                "Unlock This Level! Highest Level Unlocked: "
                                + p.getHighestLevelUnlocked());
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 1 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (2 <= p.getHighestLevelUnlocked()) {
                        buildLevel(primaryStage, "level_1.txt",1);
                    } else {
                        System.out.println("This Player Has Yet To " +
                                "Unlock This Level! Highest Level Unlocked: "
                                + p.getHighestLevelUnlocked());
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 2 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (3 <= p.getHighestLevelUnlocked()) {
                        buildLevel(primaryStage, "level_2.txt",2);
                    } else {
                        System.out.println("This Player Has Yet To " +
                                "Unlock This Level! Highest Level Unlocked: "
                                + p.getHighestLevelUnlocked());
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 3 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (4 <= p.getHighestLevelUnlocked()) {
                        buildLevel(primaryStage, "level_3.txt",3);
                    } else {
                        System.out.println("This Player Has Yet To " +
                                "Unlock This Level! Highest Level Unlocked: "
                                + p.getHighestLevelUnlocked());
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 4 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (5 <= p.getHighestLevelUnlocked()) {
                        buildLevel(primaryStage, "level_4.txt",4);
                    } else {
                        System.out.println("This Player Has Yet To " +
                                "Unlock This Level! Highest Level Unlocked: "
                                + p.getHighestLevelUnlocked());
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 5 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (6 <= p.getHighestLevelUnlocked()) {
                        buildLevel(primaryStage, "test_level.txt",5); //TODO: Change on submission
                    } else {
                        System.out.println("This Player Has Yet To " +
                                "Unlock This Level! Highest Level Unlocked: "
                                + p.getHighestLevelUnlocked());
                    }
                } catch (FileNotFoundException e) {
                }
            });
        }

        return lvl;
    }

    /**
     * This method reveals the levels that the player unlocked in
     * a previous session or in the current one.
     *
     * @param pane          the unrevealed Pane.
     * @param levelUnlocked the level currently being revealed.
     * @return revealed Pane.
     */
    public Pane revealLevels(Pane pane, int levelUnlocked) {
        Pane unlockedPane = new Pane();
        BackgroundSize standardSize = new BackgroundSize(125, 125, true, true, true, false);

        BackgroundImage lvl1 = new BackgroundImage(new Image("Sprites/testlvlIcon.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl2 = new BackgroundImage(new Image("Sprites/level_1Icon.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl3 = new BackgroundImage(new Image("Sprites/level_2Icon.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl4 = new BackgroundImage(new Image("Sprites/level_3Icon.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl5 = new BackgroundImage(new Image("Sprites/level_4Icon.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl6 = new BackgroundImage(new Image("Sprites/level_5Icon.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, standardSize);


        switch (levelUnlocked) {
            case 1 -> unlockedPane.setBackground(new Background(lvl1));
            case 2 -> unlockedPane.setBackground(new Background(lvl2));
            case 3 -> unlockedPane.setBackground(new Background(lvl3));
            case 4 -> unlockedPane.setBackground(new Background(lvl4));
            case 5 -> unlockedPane.setBackground(new Background(lvl5));
            case 6 -> unlockedPane.setBackground(new Background(lvl6));
        }

        return unlockedPane;
    }

    /**
     * Creates the Window that allows players to select a Level.
     * Players are only allowed to select levels up to their highest unlocked levels.
     *
     * @param primaryStage the Stage that is displayed for the user.
     * @throws FileNotFoundException
     */
    public void levelSelector(Stage primaryStage) throws FileNotFoundException {
        GridPane gPane = new GridPane();

        BackgroundImage image = new BackgroundImage(new Image("Sprites/MenuBasic.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1200, 104, true, true, true, false));

        gPane.setBackground(new Background(image));
        Button backBtn = new Button("Back");
        backBtn.setPrefSize(100,35);
        backBtn.setOnMouseClicked(mouseEvent -> mObject.buildMenu());

        gPane.add(makeLevelPane(primaryStage, 0), 0, 0);
        gPane.add(makeLevelPane(primaryStage, 1), 1, 0);
        gPane.add(makeLevelPane(primaryStage, 2), 2, 0);
        gPane.add(makeLevelPane(primaryStage, 3), 0, 1);
        gPane.add(makeLevelPane(primaryStage, 4), 1, 1);
        gPane.add(makeLevelPane(primaryStage, 5), 2, 1);
        gPane.add(backBtn, 1, 2);

        gPane.setHgap(25);
        gPane.setVgap(25);
        gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(100, 0, 0, 0));

        Scene scene = new Scene(gPane, 1200, 884);
        mObject.presetStage(primaryStage, "Sprites/raticon.png", "Rats: Level Selection", scene);
    }

    /**
     * This method creates a Pane, sets it to "Locked" both visually
     * and physically. A Pane is "Unlocked" when the player has beaten
     * the previous level.
     * "Unlocked" Panes start the level, "Locked" ones do not.
     *
     * @param primaryStage the Stage that is displayed for the user.
     * @param selectionLvl the level that the player is interacting with.
     * @return formatted Pane with an embedded MouseEvent
     */
    public Pane makeSavePane(Stage primaryStage, int selectionLvl) {
        Pane lvl = new Pane();

        BackgroundImage image = new BackgroundImage(new Image("Sprites/metalTile.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(125, 125, true, true, true, false));

        lvl.setBackground(new Background(image));

        if (p.getLevelsSavedTo().contains(selectionLvl)) {
            lvl = revealSavedLevels(lvl, selectionLvl);
        }

        lvl.setPrefSize(125, 125);

        switch (selectionLvl) {
            case 0 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (p.getLevelsSavedTo().contains(0)) {
                        loadLevel(primaryStage, p.getName() + "level_0.txt");
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 1 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (p.getLevelsSavedTo().contains(1)) {
                        loadLevel(primaryStage, p.getName() + "level_1.txt");
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 2 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (p.getLevelsSavedTo().contains(2)) {
                        loadLevel(primaryStage, p.getName() + "level_2.txt");
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 3 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (p.getLevelsSavedTo().contains(3)) {
                        loadLevel(primaryStage, p.getName() + "level_3.txt");
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 4 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (p.getLevelsSavedTo().contains(4)) {
                        loadLevel(primaryStage, p.getName() + "level_4.txt");
                    }
                } catch (FileNotFoundException e) {
                }
            });
            case 5 -> lvl.setOnMouseClicked(mouseEvent -> {
                try {
                    if (p.getLevelsSavedTo().contains(5)) {
                        loadLevel(primaryStage, p.getName() + "test_level.txt"); //TODO: Change on submission
                    }
                } catch (FileNotFoundException e) {
                }
            });
        }

        return lvl;
    }

    /**
     * This method reveals the levels that have save files.
     *
     * @param pane          the unrevealed Pane.
     * @param levelSaved the level currently being revealed.
     * @return revealed Pane.
     */
    public Pane revealSavedLevels(Pane pane, int levelSaved) {
        Pane savedPane = new Pane();
        BackgroundSize standardSize = new BackgroundSize(125, 125, true, true, true, false);

        BackgroundImage lvl1 = new BackgroundImage(new Image("Sprites/level_0IconSaved.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl2 = new BackgroundImage(new Image("Sprites/level_1IconSaved.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl3 = new BackgroundImage(new Image("Sprites/level_2IconSaved.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl4 = new BackgroundImage(new Image("Sprites/level_3IconSaved.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl5 = new BackgroundImage(new Image("Sprites/level_4IconSaved.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                standardSize);
        BackgroundImage lvl6 = new BackgroundImage(new Image("Sprites/level_5IconSaved.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, standardSize);


        switch (levelSaved) {
            case 0 -> savedPane.setBackground(new Background(lvl1));
            case 1 -> savedPane.setBackground(new Background(lvl2));
            case 2 -> savedPane.setBackground(new Background(lvl3));
            case 3 -> savedPane.setBackground(new Background(lvl4));
            case 4 -> savedPane.setBackground(new Background(lvl5));
            case 5 -> savedPane.setBackground(new Background(lvl6));
        }

        return savedPane;
    }

    /**
     * Creates the Window that allows players to select a saved level
     * Players are only allowed to select saves from levels they have unlocked.
     *
     * @param primaryStage the Stage that is displayed for the user.
     * @throws FileNotFoundException
     */
    public void saveSelector(Stage primaryStage) throws FileNotFoundException {
        GridPane gPane = new GridPane();

        BackgroundImage image = new BackgroundImage(new Image("Sprites/MenuBasic.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1200, 104, true, true, true, false));

        gPane.setBackground(new Background(image));
        Button backBtn = new Button("Back");
        backBtn.setPrefSize(100,35);
        backBtn.setOnMouseClicked(mouseEvent -> mObject.buildMenu());


        gPane.add(makeSavePane(primaryStage, 0), 0, 0);
        gPane.add(makeSavePane(primaryStage, 1), 1, 0);
        gPane.add(makeSavePane(primaryStage, 2), 2, 0);
        gPane.add(makeSavePane(primaryStage, 3), 0, 1);
        gPane.add(makeSavePane(primaryStage, 4), 1, 1);
        gPane.add(makeSavePane(primaryStage, 5), 2, 1);

        gPane.setHgap(25);
        gPane.setVgap(25);
        gPane.setAlignment(Pos.CENTER);
        gPane.add(backBtn, 1, 2);
        gPane.setPadding(new Insets(100, 0, 0, 0));

        Scene scene = new Scene(gPane, 1200, 884);
        mObject.presetStage(primaryStage, "Sprites/raticon.png", "Rats: Save Selection", scene);
    }

    /**
     * This method creates the Level if the user
     * entered an acceptable username.
     *
     * @param primaryStage the Stage that is displayed for the user.
     * @throws FileNotFoundException
     */
    public void buildLevel(Stage primaryStage, String level,int number) throws FileNotFoundException {
        primaryStage.setTitle("Rats: Steampunk Edition");
        primaryStage.getIcons().add(new Image("Sprites/raticon.png"));


        Level newLevel = new ReadFile(level, primaryStage).newLevel();
        newLevel.setLevelNumber(number);
        newLevel.addProfileName(p.getName());
        newLevel.addLevelName(level);
        //newLevel.save(); //testing - to be removed
    }

    /**
     * This method loads a level
     * @param primaryStage the Stage that is displayed for the user.
     * @throws FileNotFoundException
     */
    public void loadLevel(Stage primaryStage, String level) throws FileNotFoundException {
        primaryStage.setTitle("Rats: Steampunk Edition");
        primaryStage.getIcons().add(new Image("Sprites/raticon.png"));

        Level newLevel = new ReadFile(level, primaryStage).loadLevel();

        newLevel.addProfileName(p.getName());
        newLevel.addLevelName(level);
    }
}
