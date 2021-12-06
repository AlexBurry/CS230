package ratClasses;

import itemClasses.DeathRatItem;
import sprites.ImageRefs;

/**
 * Death rat kills all rats other than its own kind.
 *
 * @author Marcus, Trafford
 * @version 1
 */

public class DeathRat extends Rat {

    private DeathRatItem item;

    /**
     * Creates a new death rat.
     *
     * @param sex       the gender. This does not matter.
     * @param isDeath   this is always a death rat.
     * @param isSterile this is always true.
     * @param xPos      starting x position of the rat.
     * @param yPos      starting y position of the rat.
     */
    public DeathRat(char sex, boolean isDeath, boolean isSterile, int xPos, int yPos) {
        super(sex, isDeath, isSterile, xPos, yPos, false);
        this.setImage(ImageRefs.deathRatUp);

    }

    /**
     * gets the item
     */
    public DeathRatItem getItem() {
        return item;
    }

    /**
     * sets the item
     *
     * @param item must be a DeathRatItem
     */
    public void setItem(DeathRatItem item) {
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
