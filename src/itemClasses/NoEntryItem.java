package itemClasses;

import sprites.ImageRefs;
/**
 * Makes a tile collide with rats, not allowing them to
 * move through it
 *
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public class NoEntryItem extends Item {

    private int healthPoints = 5;

    /**
     * Creates a new no entry item. In our game,
     * this is actually a stop sign.
     * @param x
     * @param y
     */
    public NoEntryItem(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.noEntryDamage0);
        this.setMyItemType(itemType.NoEntry);
    }

    /**
     * called from the rat that hit it, this will change the sprite of the object and reduce its health.
     */
    public void hitSign() {
        healthPoints -= 1;

        if (healthPoints == 4) {
            this.setImage(ImageRefs.noEntryDamage1);
        } else if (healthPoints == 3) {
            this.setImage(ImageRefs.noEntryDamage2);
        } else if (healthPoints == 2) {
            this.setImage(ImageRefs.noEntryDamage3);
        } else if (healthPoints == 1) {
            this.setImage(ImageRefs.noEntryDamage4);
        }

    }

    /**
     * Called from rat to check if this item should be removed.
     * Prevents a ConcurrentModificationException by handling removals there.
     */
    public boolean shouldKill() {
        return healthPoints <= 0;
    }


}
