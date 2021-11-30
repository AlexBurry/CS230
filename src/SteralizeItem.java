import javafx.scene.image.Image;

/**
 * Steralize rats, removing their ability to reproduce, permanently.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class SteralizeItem extends Item{

    public SteralizeItem (int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.sprite = new Image("NoEntry.png");
    }



}
