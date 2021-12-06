package itemClasses;

import sprites.ImageRefs;


/**
 * Change the sex of a rat
 *
 * @author Jack
 * @version 1
 */
public class MFChange extends Item {

    /**
     * Creates a new MFItem
     *
     * @param x the XCoordinate of the item
     * @param y the YCoordinate of the item
     */
    public MFChange(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.maleToFemaleSC);
        this.setMyItemType(itemType.FSex);
    }


}
