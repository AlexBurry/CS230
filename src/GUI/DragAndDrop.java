package GUI;

import Game.Level;
import Game.Tile;
import ItemClasses.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This class creates the HBox which stores items icons
 * and allows the player to drag and drop them onto the Board.
 *
 * @author Dominik
 * @version 1.0
 * @since 1.0
 */
public class DragAndDrop {

    private Image noEntryIcon;
    private Canvas canvas;
    private Tile[][] tileMap;
    private Level instance;


    private Item.itemType selectedItem;

    public DragAndDrop(Canvas canvas, Tile[][] tileMap) {
        noEntryIcon = new Image("Sprites/NoEntry.png");
        this.canvas = canvas;
        this.tileMap = tileMap;
        selectedItem = Item.itemType.NoEntry; //Default to NoEntry for now.
        instance = Level.getInstance();
    }

    // Not scaled to all items yet.
    public HBox makeToolBar() {
        HBox toolBar = new HBox();
        toolBar.setPadding(new Insets(10, 10, 10, 10));
        toolBar.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

//        ImageView draggableItem = new ImageView();
//        draggableItem.setImage(noEntryIcon);
//        dragCode(draggableItem);
//        toolBar.getChildren().add(draggableItem);

        selectedItem = Item.itemType.DeathRat;
        ImageView draggableItem3 = new ImageView();
        draggableItem3.setImage(new Image("Sprites/DeathRat.png"));
        dragCode(draggableItem3);
        toolBar.getChildren().add(draggableItem3);

//        selectedItem = ItemClasses.Item.itemType.Poison;
//        ImageView draggableItem4 = new ImageView();
//        draggableItem4.setImage(new Image("PoisonTrap.png"));
//        dragCode(draggableItem4);
//        toolBar.getChildren().add(draggableItem4);
//
//        selectedItem = ItemClasses.Item.itemType.MSex;
//        ImageView draggableItem5 = new ImageView();
//        draggableItem5.setImage(new Image("femaleMaleSexChange.png"));
//        dragCode(draggableItem5);
//        toolBar.getChildren().add(draggableItem5);
//
//        selectedItem = ItemClasses.Item.itemType.FSex;
//        ImageView draggableItem6 = new ImageView();
//        draggableItem6.setImage(new Image("maleFemaleSexChange.png"));
//        dragCode(draggableItem6);
//        toolBar.getChildren().add(draggableItem6);
//
//        selectedItem = ItemClasses.Item.itemType.Sterilise;
//        ImageView draggableItem7 = new ImageView();
//        draggableItem7.setImage(new Image("RatClasses.DeathRat.png"));
//        dragCode(draggableItem7);
//        toolBar.getChildren().add(draggableItem7);
//
//        selectedItem = ItemClasses.Item.itemType.Bomb;
//        ImageView draggableItem8 = new ImageView();
//        draggableItem8.setImage(new Image("Bomb4.png"));
//        dragCode(draggableItem8);
//        toolBar.getChildren().add(draggableItem8);

        return toolBar;
    }


    public void canvasDragDroppedOccurred(DragEvent event) {
        int tileSize = 60;

        double x = event.getX();
        double y = event.getY();

        int tileMapX = (int) (x / tileSize);
        int tileMapY = (int) (y / tileSize);

        //String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
        //System.out.println(s);
        //System.out.println(tileMapX + ", " + tileMapY);

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
            case Bomb -> new BombItem(x, y);
            case Gas -> new GasItem(x, y);
            case Sterilise -> new SteriliseItem(x, y);
            case MSex -> new MFChange(x, y);
            case FSex -> new FMChange(x, y);
            case Poison -> new PoisonItem(x, y);
            case DeathRat -> new DeathRatItem(x, y);
            case NoEntry -> new NoEntryItem(x, y);
        }
    }

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
                if (event.getGestureSource() == item) {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                }
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
