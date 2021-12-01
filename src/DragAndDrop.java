import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    /*
    Used to track what type of item you're currently selecting. This should
    be changed whenever you click on a new item in the inventory.
     */
    private enum itemType{
        Bomb,
        Gas,
        Sterilisation,
        MSexChange,
        FSexChange,
        Poison,
        DeathRat,
        NoEntry
    }

    private itemType selectedItem;

    public DragAndDrop(Canvas canvas, Tile[][] tileMap) {
        noEntryIcon = new Image("NoEntry.png");
        this.canvas = canvas;
        this.tileMap = tileMap;
        selectedItem = itemType.NoEntry; //Default to NoEntry for now.
    }

    // Not scaled to all items yet.
    public HBox makeToolBar() {
        HBox toolBar = new HBox();
        toolBar.setPadding(new Insets(10, 10, 10, 10));
        toolBar.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView draggableItem = new ImageView();
        draggableItem.setImage(noEntryIcon);
        dragCode(draggableItem);
        toolBar.getChildren().add(draggableItem);

        return toolBar;
    }

    // Currently only draws it on top of the map.
    public void canvasDragDroppedOccured(DragEvent event) {
        int tileSize = 60;

        double x = event.getX();
        double y = event.getY();

        int tileMapX = (int) (x / tileSize);
        int tileMapY = (int) (y / tileSize);

        String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
        System.out.println(s);
        System.out.println(tileMapX + ", " + tileMapY);

        if (tileMap[tileMapX][tileMapY].getTileType().equalsIgnoreCase("P")) {
            convertAndCreate(tileMapX,tileMapY); //-> replaces the need for drawing on canvas
        } else {
            System.out.println("You Can Only Place Items On Path Tiles!");
        }
    }

    /**
     * converts draggableItems into real Items and creates their class.
     * @param x the xPos of the item relative to the board
     * @param y the yPos of the item relative to the board
     */
    public void convertAndCreate(int x,int y){
        switch (selectedItem){
            case Bomb -> new BombItem(x,y);
            case Gas -> new GasItem(x,y);
            case Sterilisation -> new SteralizeItem(x,y);
            case MSexChange -> new SexChangeItem(x,y);
            case FSexChange -> new SexChangeItem(x,y);
            case Poison -> new PoisonItem(x,y);
            case DeathRat -> new DeathRatItem(x,y);
            case NoEntry -> new NoEntryItem(x,y);
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
                canvasDragDroppedOccured(event);
                event.consume();
            }
        });
    }
}
