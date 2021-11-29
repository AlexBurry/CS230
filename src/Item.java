import javafx.scene.image.Image;

/**
 * Represents the fundamental functions of an Item object.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class Item {

    protected Image sprite;
    protected int xPos;
    protected int yPos;

    /**
     * 
     * Draws the item to a tile location
     * @param x The coordinates which will point to a tile location.
     * @param y
     */

    public void draw(int x, int y){
        Level.getInstance().getLevelBoard().addItemToMap(x,y,this);
    }



    /**
     * * Sets the current sprite of the item. Used for changing its appearance.
     * @param newImage The new sprite to replace the old one.
     */
    public void setImage(Image newImage){
        sprite = newImage;
    }


    public Image getImage(){
        return sprite;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    /**
     * * Implements a solution to delete that item,
     * this is where methods are called before it "dies".
     */
    public void deleteItem(){

    }



}