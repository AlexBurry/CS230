package gui;

import game.Level;
import game.Tile;
import itemClasses.*;
import ratClasses.Rat;
import sprites.ImageRefs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * This class creates the HBox which stores items icons
 * and allows the player to drag and drop them onto the Board.
 *
 * @author Dominik
 * @author Trafford
 * @version 1.0
 */
public class DragAndDrop {

    private Canvas canvas;
    private Tile[][] tileMap;
    private Level instance;

    private int maleRats;
    private int femaleRats;
    private int babyRats;

    private int ratListSize = 0;
    private boolean sexChangeUsed;
    private Item.itemType selectedItem;
    private final int AVAILABLE_SPACE = 380;

    private ImageView noEntry = new ImageView();
    private ImageView deathRat = new ImageView();
    private ImageView poison = new ImageView();
    private ImageView femaleSexChange = new ImageView();
    private ImageView maleSexChange = new ImageView();
    private ImageView sterilise = new ImageView();
    private ImageView bomb = new ImageView();
    private ImageView gas = new ImageView();

    public DragAndDrop(Canvas canvas, Tile[][] tileMap) {
        this.canvas = canvas;
        this.tileMap = tileMap;
        selectedItem = Item.itemType.NoEntry; //Default to NoEntry for now.
        instance = Level.getCurrentLevelInstance();
    }

    /**
     * This method creates display for the number of available
     * Items for each type of Item using a Label
     * and the Item Image in a GridPane layout.
     *
     * @param numberOfItem number of available Items.
     * @param item         image of the Item.
     * @return formatted GridPane with item image and number embedded.
     */
    public GridPane makeItemWithCounter(int numberOfItem, ImageView item) {
        Label lbl = new Label(String.valueOf(numberOfItem));
        lbl.setFont(new Font("Comic Sans", 16));
        lbl.setTextFill(Color.WHITE);

        GridPane counterPane = new GridPane();
        GridPane.setHalignment(lbl, HPos.CENTER);
        counterPane.add(lbl, 0, 1);
        counterPane.add(item, 0, 2);

        return counterPane;
    }

    /**
     * This method creates a HBox, formats it
     * adds images and a 'onMouseDragged' event.
     *
     * @return formatted HBox.
     */
    public HBox makeToolBar() {
        HBox toolBar = new HBox();
        toolBar.setPadding(new Insets(10, 10, 10, 10));
        BackgroundImage image = new BackgroundImage(new Image("sprites/HBoxFill.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1200, 104, true, true, true, false));
        toolBar.setBackground(new Background(image));

        noEntry.setImage(ImageRefs.iconNoEntry);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfNoEntry(), noEntry));

        deathRat.setImage(ImageRefs.iconDeathRat);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfDeathRat(), deathRat));

        poison.setImage(ImageRefs.iconPoison);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfPoison(), poison));

        femaleSexChange.setImage(ImageRefs.iconMFSC);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfFSexChange(), femaleSexChange));

        maleSexChange.setImage(ImageRefs.iconFMSC);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfMSexChange(), maleSexChange));

        sterilise.setImage(ImageRefs.iconSterilise);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfSterilisation(), sterilise));

        bomb.setImage(ImageRefs.iconBomb);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfBombs(), bomb));

        gas.setImage(ImageRefs.iconGas);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInventory().getNumberOfGas(), gas));

        toolBar.getChildren().add(makeScore());
        toolBar.getChildren().add(makeHealthBar());
        toolBar.getChildren().add(makeOptionsButton());

        toolBar.setOnMouseDragged(mouseEvent -> itemMover(mouseEvent));

        return toolBar;
    }

    /**
     * This method creates a formatted GridPane
     * embedded with the user's current score.
     *
     * @return formatted GridPane.
     */
    public GridPane makeScore() {
        GridPane scoreGPane = new GridPane();
        Label scoreLbl = new Label(("Current Score: " + String.valueOf(Menu.getProfile().getScore())));
        scoreLbl.setFont(new Font("Comic Sans", 16));
        scoreLbl.setTextFill(Color.WHITE);
        Pane invisiblePane = new Pane();
        invisiblePane.setPrefSize(270, 40);
        GridPane.setHalignment(scoreLbl, HPos.CENTER);
        scoreGPane.add(scoreLbl, 0, 1);
        scoreGPane.add(invisiblePane, 0, 2);

        return scoreGPane;
    }

    /**
     * Creates a GridPane and formats it into a Health Bar
     * using Rectangle objects and the Array list of Rats from Level.
     * Rats are recalculated if rat list's size changes or sex change is used.
     *
     * @return formatted GridPane as Health Bar.
     */
    public GridPane makeHealthBar() {
        HBox hpBar = new HBox();
        GridPane gPane = new GridPane();

        Label invisibleLbl = new Label("");
        invisibleLbl.setFont(new Font("Comic Sans", 24));

        hpBar.setPrefSize(AVAILABLE_SPACE, 50);
        countRats();

        hpBar.getChildren().addAll(rectangleWithText(Color.BLUE, maleRats)
                , rectangleWithText(Color.PINK, femaleRats)
                , rectangleWithText(Color.WHITE, babyRats));

        gPane.add(invisibleLbl, 0, 1);
        gPane.add(hpBar, 0, 2);

        return gPane;
    }

    /**
     * This method creates a StackPane, it overlaps a
     * rectangle with a label to create a numerical and
     * visual display for enemy Rats.
     *
     * @param color      Color of the Rectangle.
     * @param multiplier Number of Rats.
     * @return formatted StackPane.
     */
    public StackPane rectangleWithText(Color color, int multiplier) {
        StackPane completedPane = new StackPane();
        Rectangle rectName = new Rectangle((AVAILABLE_SPACE / instance.getLOSS_CONDITION()) * multiplier, 50, color);
        Label ratCount = new Label(String.valueOf(multiplier));
        ratCount.setTextFill(Color.BLACK);
        ratCount.setFont(new Font("Comic Sans", 24));
        if (multiplier != 0) {
            completedPane.getChildren().addAll(rectName, ratCount);
        }

        return completedPane;
    }

    /**
     * Creates a GridPane and formats it into a Health Bar
     * using Rectangle objects and the Array list of Rats from Level.
     * Rats are recalculated if rat list's size changes or sex change is used.
     *
     * @return formatted GridPane as Health Bar.
     */
    public GridPane makeOptionsButton() {
        GridPane gPane = new GridPane();

        Label textDisplay = new Label("Save? Will overwrite existing files!");
        Label invisibleLbl = new Label("");
        invisibleLbl.setFont(new Font("Comic Sans", 26));

        Button optionBtn = new Button("Options");
        optionBtn.setPrefSize(100, 35);

        gPane.setHalignment(optionBtn, HPos.RIGHT);
        gPane.add(invisibleLbl, 0, 1);
        gPane.add(optionBtn, 0, 2);

        optionBtn.setOnAction(mouseEvent ->
        {
            Stage optionWindow = new Stage();
            optionWindow.initModality(Modality.APPLICATION_MODAL);

            Button saveBtn = new Button("Save");
            Button backToMenuBtn = new Button("Back To Menu");
            backToMenuBtn.setPrefSize(100, 35);
            saveBtn.setPrefSize(70, 35);
            saveBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //temporary.
                    instance.save();
                    Menu.getProfile().addToSavedLevels(instance.getLevelNumber());
                    Menu.getProfile().saveToProfile(Menu.getProfile().getName());
                }
            });

            GridPane gPane2 = new GridPane();

            gPane2.setAlignment(Pos.CENTER);

            gPane2.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

            gPane2.add(saveBtn, 0, 0);

            Scene optionScene = new Scene(gPane2, 300, 150);

            optionWindow.getIcons().add(new Image("sprites/raticon.png"));
            optionWindow.setTitle("Rats: Save");
            optionWindow.setResizable(false);
            optionWindow.setScene(optionScene);
            optionWindow.centerOnScreen();
            optionWindow.show();

            instance.getTickTimeline().pause(); //pauses the game tick
            optionWindow.setOnCloseRequest(windowEvent -> {
                instance.getTickTimeline().play(); //resumes the game tick on close
            });
        });

        return gPane;
    }

    /**
     * Counts the number of male, female and baby rats
     * stores in the Rat array stored in Level.
     */
    public void countRats() {
        ArrayList<Rat> rats = instance.getLevelBoard().getRats();
        if (ratListSize != rats.size() || sexChangeUsed) {
            maleRats = 0;
            femaleRats = 0;
            babyRats = 0;
            sexChangeUsed = false;
            for (int i = 0; i < rats.size(); i++) {
                if (rats.get(i).getIsBaby()) {
                    babyRats++;
                } else {
                    switch (rats.get(i).getSex()) {
                        case 'M' -> maleRats++;
                        case 'F' -> femaleRats++;
                    }
                }
            }
            ratListSize = rats.size();
        }
    }

    /**
     * This method is called when the player drags something
     * on the "toolBar" HBox. It splits up the HBox into squares
     * for each item which helps decide which item was used.
     *
     * @param mouseEvent object used to get the mouse's coordinates.
     */
    public void itemMover(MouseEvent mouseEvent) {
        int firstSprite = 70;
        int secondSprite = 60;
        double x = 0;

        x = mouseEvent.getX();

        if (x < firstSprite && x > 0) {
            selectedItem = Item.itemType.NoEntry;
            dragCode(noEntry);
        } else if (x < firstSprite + secondSprite && x > firstSprite) {
            selectedItem = Item.itemType.DeathRat;
            dragCode(deathRat);
        } else if (x < (firstSprite + (secondSprite * 2)) && x > (firstSprite + secondSprite)) {
            selectedItem = Item.itemType.Poison;
            dragCode(poison);
        } else if (x < (firstSprite + (secondSprite * 3)) && x > (firstSprite + (secondSprite * 2))) {
            selectedItem = Item.itemType.MSex;
            dragCode(femaleSexChange);
        } else if (x < (firstSprite + (secondSprite * 4)) && x > (firstSprite + (secondSprite * 3))) {
            selectedItem = Item.itemType.FSex;
            dragCode(maleSexChange);
        } else if (x < (firstSprite + (secondSprite * 5)) && x > (firstSprite + (secondSprite * 4))) {
            selectedItem = Item.itemType.Sterilise;
            dragCode(sterilise);
        } else if (x < (firstSprite + (secondSprite * 6)) && x > (firstSprite + (secondSprite * 5))) {
            selectedItem = Item.itemType.Bomb;
            dragCode(bomb);
        } else if (x < (firstSprite + (secondSprite * 7)) && x > (firstSprite + (secondSprite * 6))) {
            selectedItem = Item.itemType.Gas;
            dragCode(gas);
        }
    }

    /**
     * This method places the item dragged by the player onto the Grid
     * and snaps it to the center of the closest Tile.
     *
     * @param event DragEvent object used to get mouse coordinates.
     */
    public void canvasDragDroppedOccurred(DragEvent event) {
        int tileSize = 60;

        double x = event.getX();
        double y = event.getY();

        int tileMapX = (int) (x / tileSize);
        int tileMapY = (int) (y / tileSize);

        if (tileMap[tileMapX][tileMapY].getTileType().equalsIgnoreCase("P")) {
            if (!noItemPresent(tileMapX, tileMapY)) {
                convertAndCreate(tileMapX, tileMapY);
            } else {
                System.out.println("There is already an item here!");
            }

        } else {
            System.out.println("You Can Only Place Items On Path Tiles!");
        }
    }

    /**
     * Checks to see if there is an item at a location before placement
     *
     * @param x xPosition
     * @param y yPosition
     * @return true or false.
     */
    private boolean noItemPresent(int x, int y) {
        //this works by checking every item for a match of the predicate item where item.x = x etc
        return instance.getLevelBoard().getItems().stream().anyMatch(item -> item.getX() == x && item.getY() == y);
    }

    /**
     * converts draggableItems into real Items and creates their class.
     *
     * @param x the xPos of the item relative to the board
     * @param y the yPos of the item relative to the board
     */
    public void convertAndCreate(int x, int y) {
        switch (selectedItem) {
            case Bomb -> {
                if (instance.getLevelInventory().getNumberOfBombs() > 0) {
                    instance.getLevelInventory().decreaseNumberOfBombs();
                    new BombItem(x, y);
                } else {
                    System.out.println("No Bombs left");
                }
            }
            case Gas -> {
                if (instance.getLevelInventory().getNumberOfGas() > 0) {
                    instance.getLevelInventory().decreaseNumberOfGas();
                    new GasItem(x, y);
                } else {
                    System.out.println("No Gas left");
                }
            }
            case Sterilise -> {
                if (instance.getLevelInventory().getNumberOfSterilisation() > 0) {
                    instance.getLevelInventory().decreaseNumberOfSterilisation();
                    new SteriliseItem(x, y);
                } else {
                    System.out.println("No Sterilise left");
                }
            }
            case MSex -> {
                if (instance.getLevelInventory().getNumberOfMSexChange() > 0) {
                    instance.getLevelInventory().decreaseNumberOfMSexChange();
                    new MFChange(x, y);
                } else {
                    System.out.println("No Male Sex Change left");
                }
            }
            case FSex -> {
                if (instance.getLevelInventory().getNumberOfFSexChange() > 0) {
                    instance.getLevelInventory().decreaseNumberOfFSexChange();
                    new FMChange(x, y);
                } else {
                    System.out.println("No Female Sex Change left");
                }
            }
            case Poison -> {
                if (instance.getLevelInventory().getNumberOfPoison() > 0) {
                    instance.getLevelInventory().decreaseNumberOfPoison();
                    new PoisonItem(x, y);
                } else {
                    System.out.println("No Poison left");
                }
            }
            case DeathRat -> {
                if (instance.getLevelInventory().getNumberOfDeathRat() > 0) {
                    instance.getLevelInventory().decreaseNumberOfDeathRat();
                    new DeathRatItem(x, y);
                } else {
                    System.out.println("No Death Rats left");
                }
            }
            case NoEntry -> {
                if (instance.getLevelInventory().getNumberOfNoEntry() > 0) {
                    instance.getLevelInventory().decreaseNumberOfNoEntry();
                    new NoEntryItem(x, y);
                } else {
                    System.out.println("No No Entry Sign signs left");
                }
            }
        }

        instance.getLevelBoard().redrawTile(x, y, true);
    }

    /**
     * Handles the drag event and calls canvasDragDroppedOccurred
     * when the user releases the item.
     *
     * @param item the selected item to be drag and dropped.
     */
    public void dragCode(ImageView item) {
        item.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = item.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello");
                db.setContent(content);
                event.consume();
            }
        });
        canvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
            }
        });
        canvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                canvasDragDroppedOccurred(event);
                event.consume();
            }
        });
    }

    public boolean setSexChanged(boolean sexChangedUsed) {
        return this.sexChangeUsed = sexChangedUsed;
    }
}
