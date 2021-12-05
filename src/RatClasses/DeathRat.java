package RatClasses;

import ItemClasses.DeathRatItem;
import Sprites.ImageRefs;

/**
 * Death rat kills all rats other than its own kind.
 *
 * @author Marcus, Trafford
 * @version 0.1
 * @since 0.1
 */

public class DeathRat extends Rat{

    private DeathRatItem item;

    public DeathRat(char sex, boolean isDeath, boolean isSterile, int xPos, int yPos) {
        super(sex, isDeath, isSterile, xPos, yPos,false);
        this.setImage(ImageRefs.deathRatUp);

    }

    /**
     * gets the item
     */
    public DeathRatItem getItem(){
        return item;
    }

    /**
     * sets the item
     * @param item must be a DeathRatItem
     */
    public void setItem(DeathRatItem item){
        this.item = item;
    }

    /**
     * changes the death rat sprite depending on the direction it's going.
     */
    @Override
    public void changeSprite() {
        switch (getCurrentDirection()) {
            case EAST -> setImage(ImageRefs.deathRatRight);
            case WEST -> setImage(ImageRefs.deathRatLeft);
            case NORTH -> setImage(ImageRefs.deathRatUp);
            case SOUTH -> setImage(ImageRefs.deathRatDown);
        }
    }



}