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
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("NoEntry.png"));
        this.setMyItemType(itemType.Poison);

    }


    
}
