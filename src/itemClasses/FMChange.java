package itemClasses;

import sprites.ImageRefs;

/**
 * Change the sex of a rat
 *
 * @author Jack
 * @version 1
 */
public class FMChange extends Item {

    /**
     * Creates a new MFItem
     *
     * @param x the XCoordinate of the item
     * @param y the YCoordinate of the item
     */
    public FMChange(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.femaleToMaleSC);
        this.setMyItemType(itemType.MSex);
    }


}
