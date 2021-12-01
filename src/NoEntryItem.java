import javafx.scene.image.Image;

//TODO: FINISH THIS CLASS
/**
 * Makes a tile collide with rats, not allowing them to 
 * move through it
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public class NoEntryItem extends Item{

    private int healthPoints = 5;


    public NoEntryItem (int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("NoEntry.png"));
        this.setMyItemType(itemType.NoEntry);
    }

    
}
