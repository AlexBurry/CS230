import javafx.scene.image.Image;

import java.lang.management.GarbageCollectorMXBean;

/**
 * Represents the fundamental functions of an Item object.
 * Cannot be instantiated itself.
 *
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public abstract class Item {

    private Image sprite; //The sprite or image used by this item. Set this in constructor
    private int xPos;
    private int yPos;
    private Level instance; //This stores a reference to our current level.
    private itemType myItemType; //This is the type of item this is
    private boolean drawMyself = true; //TODO: Use this to stop drawing when on tunnels

    //Represents an item type and all its datatypes / choices.
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

    /**
     * Sets the instance and adds the item to the main board.
     */
    public Item() {
        setInstance();
        addToBoard();
    }

    /**
     * Used to access the instance of LEVEL from a child class
     *
     * @return this.instance, the instance of level class.
     */
    public Level getLocalInstance() {
        return this.instance;
    }

    /**
     * Adds an item to the main board associated with this level.
     */
    public void addToBoard() {
        instance.getLevelBoard().addItem(this);
    }

    /**
     * Sets the instance.
     */
    protected void setInstance() {
        instance = Level.getInstance();
    }

    /**
     * * Sets the current sprite of the item. Used for changing its appearance.
     *
     * @param newImage The new sprite to replace the old one.
     */
    public void setImage(Image newImage) {
        sprite = newImage;
    }

    /**
     * Used to access the current sprite of this item
     *
     * @return the current sprite of this item
     */
    public Image getImage() {
        return sprite;
    }

    /**
     * Used to get the XCoordinate of this item.
     * For pixel coordinate, multiply by 60.
     *
     * @return xPos
     */
    public int getX() {
        return xPos;
    }

    /**
     * Used to get the XCoordinate of this item.
     * For pixel coordinate, multiply by 60.
     *
     * @return yPos
     */
    public int getY() {
        return yPos;
    }

    /**
     * Sets the XCoordinate of this item.
     *
     * @param x the x value
     */
    public void setX(int x) {
        this.xPos = x;
    }

    /**
     * Sets the YCoordinate of this item.
     *
     * @param y the y value
     */
    public void setY(int y) {
        this.yPos = y;
    }

    /**
     * Used to check which type of item this is
     *
     * @return myItemType, the current type of the item.
     */
    public itemType getMyItemType() {
        return myItemType;
    }

    /**
     * Sets the type of item.
     * Should only be used in-hierarchy.
     *
     * @param myItemType the item you want to set it to.
     */
    public void setMyItemType(itemType myItemType) {
        this.myItemType = myItemType;
    }

    /**
     * Removes an item from the board, effectively killing it.
     */
    public void deleteItem() {
        instance.getLevelBoard().removeItem(this);

    }


}