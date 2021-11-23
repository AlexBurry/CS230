import javafx.scene.image.Image;

/**
 * Represents the fundamental functions of an Item object.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public abstract class Item {

    private Image sprite;


    /**
     * 
     * Draws the item to a tile location
     * @param position The coordinates which will point to a tile location.
     */
    
    public abstract void draw(int[][] position);


    /**
     * * Sets the current sprite of the item. Used for changing its appearance.
     * @param newImage The new sprite to replace the old one.
     */
    public void setImage(Image newImage){
        sprite = newImage;
    }


    /**
     * * Implements a solution to delete that item,
     * this is where methods are called before it "dies".
     */
    public abstract void deleteItem();

    protected void tryPlaceItemOnLocation(int x, int y){
        //Board.addItemToMap(x,y,this);
    };

}