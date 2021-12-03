package ItemClasses;

import Sprites.ImageRefs;

/**
 * Smaller node of gasItem, reproduces on condition.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class GasChild extends GasItem{

    private boolean isAbleToReproduce = false;

    public GasChild(int x, int y) {
        super(x, y);
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.gasOuter);
        this.setMyItemType(itemType.Gas);
    }
}
