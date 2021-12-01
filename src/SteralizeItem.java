import javafx.scene.image.Image;

//TODO: FINISH THIS CLASS
/**
 * Steralize rats, removing their ability to reproduce, permanently.
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class SteralizeItem extends Item{

    public SteralizeItem (int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("Bomb4.png"));
        this.setMyItemType(itemType.Sterilise);
    }



}
