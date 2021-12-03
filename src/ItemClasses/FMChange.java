package ItemClasses;

import javafx.scene.image.Image;

/**
 * Change the sex of a rat
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class FMChange extends Item {


    public FMChange(int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("Sprites/femaleMaleSexChange.png"));
        this.setMyItemType(itemType.FSex);
    }


}