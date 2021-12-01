import javafx.scene.image.Image;

/**
 * Change the sex of a rat
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class SexChangeItem extends Item {
    

    public SexChangeItem(int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("Bomb4.png"));
        this.setMyItemType(itemType.MSex);
    }


}
