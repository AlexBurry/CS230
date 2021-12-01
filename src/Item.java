import javafx.scene.image.Image;

/**
 * Represents the fundamental functions of an Item object.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public abstract class Item{

    private Image sprite;
    private int xPos;
    private int yPos;
    private Level instance;
    private itemType myItemType;

    public enum itemType {
        Poison,
        Gas,
        Bomb,
        Sterilise,
        MSex,
        FSex,
        NoEntry,
        DeathRat
    }

    public Item(){
        setInstance();
        addToBoard();
    }

    public Level getLocalInstance(){
        return this.instance;
    }

    public void addToBoard(){
        instance.getLevelBoard().addItem(this);
    }

    protected void setInstance(){
        instance = Level.getInstance();
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

    public void setX(int x){
        this.xPos = x;
    }

    public void setY(int y){
        this.yPos = y;
    }

    public itemType getMyItemType() {
        return myItemType;
    }

    public void setMyItemType(itemType myItemType) {
        this.myItemType = myItemType;
    }

    /**
     * * Implements a solution to delete that item,
     * this is where methods are called before it "dies".
     */
    public void deleteItem(){
        instance.getLevelBoard().removeItem(this);
    }



}