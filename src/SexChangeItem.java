import javafx.scene.image.Image;

/**
 * Change the sex of a rat
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class SexChangeItem extends Item {

    private int sex = 0; // default to male

    public SexChangeItem(int xPos, int yPos){
        sprite = new Image("NoEntry.png");
        this.xPos = xPos;
        this.yPos = yPos;
    }


}
