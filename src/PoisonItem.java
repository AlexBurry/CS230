import javafx.scene.image.Image;

/**
 * Poison the rat and kill it
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class PoisonItem extends Item{

    public PoisonItem (int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.sprite = new Image("NoEntry.png");
    }


    
}
