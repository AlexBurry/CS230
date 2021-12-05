package ItemClasses;

import Sprites.ImageRefs;
import javafx.scene.image.Image;

/**
 * Change the sex of a rat
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class MFChange extends Item {

    /**
     * Creates a new MFItem
     *
     * @param x the XCoordinate of the item
     * @param y the YCoordinate of the item
     */
    public MFChange(int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.maleToFemaleSC);
        this.setMyItemType(itemType.FSex);
    }


}
