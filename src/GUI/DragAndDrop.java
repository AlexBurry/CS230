package GUI;

import Game.Level;
import Game.Tile;
import ItemClasses.*;
import Sprites.ImageRefs;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class creates the HBox which stores items icons
 * and allows the player to drag and drop them onto the Board.
 *
 * @author Dominik
 * @author Trafford
 * @version 1.0
 * @since 1.0
 */
public class DragAndDrop {

    private Canvas canvas;
    private Tile[][] tileMap;
    private Level instance;

    private Item.itemType selectedItem;

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
        instance = Level.getInstance();
    }

    /**
     * This method creates display for the number of available
     * Items for each type of Item using a Label
     * and the Item Image in a GridPane layout.
     *
     * @param numberOfItem number of available Items.
     * @param item image of the Item.
     * @return formatted GridPane with item image and number embedded.
     */
    public GridPane makeItemWithCounter(int numberOfItem, ImageView item) {
        Label lbl = new Label(String.valueOf(numberOfItem));
        lbl.setFont(new Font("Comic Sans", 16));
        lbl.setTextFill(Color.WHITE);


        GridPane noEntryPane = new GridPane();
        GridPane.setHalignment(lbl, HPos.CENTER);
        noEntryPane.add(lbl, 0, 1);
        noEntryPane.add(item, 0, 2);

        return noEntryPane;
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
        BackgroundImage image = new BackgroundImage(new Image("Sprites/HBoxFill.png"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1200, 104, true, true, true, false));
        toolBar.setBackground(new Background(image));


        noEntry.setImage(ImageRefs.iconNoEntry);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfNoEntry(), noEntry));
        Tooltip.install(noEntry, new Tooltip("Hello"));

        deathRat.setImage(ImageRefs.iconDeathRat);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfDeathRat(), deathRat));

        poison.setImage(ImageRefs.iconPoison);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfPoison(), poison));

        femaleSexChange.setImage(ImageRefs.iconMFSC);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfFSexChange(), femaleSexChange));

        maleSexChange.setImage(ImageRefs.iconFMSC);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfMSexChange(), maleSexChange));

        sterilise.setImage(ImageRefs.iconSterilise);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfSterilisation(), sterilise));

        bomb.setImage(ImageRefs.iconBomb);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfBombs(), bomb));

        gas.setImage(ImageRefs.iconGas);
        toolBar.getChildren().add(makeItemWithCounter(instance.getLevelInv().getNumberOfGas(), gas));

        toolBar.setOnMouseDragged(mouseEvent -> itemMover(mouseEvent));

        return toolBar;
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
                if (instance.getLevelInv().getNumberOfBombs() > 0) {
                    instance.getLevelInv().decreaseNumberOfBombs();
                    new BombItem(x, y);
                } else {
                    System.out.println("No Bombs left");
                }
            }
            case Gas -> {
                if (instance.getLevelInv().getNumberOfGas() > 0) {
                    instance.getLevelInv().decreaseNumberOfGas();
                    new GasItem(x, y);
                } else {
                    System.out.println("No Gas left");
                }
            }
            case Sterilise -> {
                if (instance.getLevelInv().getNumberOfSterilisation() > 0) {
                    instance.getLevelInv().decreaseNumberOfSterilisation();
                    new SteriliseItem(x, y);
                } else {
                    System.out.println("No Sterilise left");
                }
            }
            case MSex -> {
                if (instance.getLevelInv().getNumberOfMSexChange() > 0) {
                    instance.getLevelInv().decreaseNumberOfMSexChange();
                    new MFChange(x, y);
                } else {
                    System.out.println("No Male Sex Change left");
                }
            }
            case FSex -> {
                if (instance.getLevelInv().getNumberOfFSexChange() > 0) {
                    instance.getLevelInv().decreaseNumberOfFSexChange();
                    new FMChange(x, y);
                } else {
                    System.out.println("No Female Sex Change left");
                }
            }
            case Poison -> {
                if (instance.getLevelInv().getNumberOfPoison() > 0) {
                    instance.getLevelInv().decreaseNumberOfPoison();
                    new PoisonItem(x, y);
                } else {
                    System.out.println("No Poison left");
                }
            }
            case DeathRat -> {
                if (instance.getLevelInv().getNumberOfDeathRat() > 0) {
                    instance.getLevelInv().decreaseNumberOfDeathRat();
                    new DeathRatItem(x, y);
                } else {
                    System.out.println("No Death Rats left");
                }
            }
            case NoEntry -> {
                if (instance.getLevelInv().getNumberOfNoEntry() > 0) {
                    instance.getLevelInv().decreaseNumberOfNoEntry();
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
}
