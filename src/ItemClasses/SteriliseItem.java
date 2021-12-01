package ItemClasses;

import javafx.scene.image.Image;

//TODO: FINISH THIS CLASS
/**
 * Steralize rats, removing their ability to reproduce, permanently.
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class SteriliseItem extends Item{

    public SteriliseItem(int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("Sprites/Bomb4.png"));
        this.setMyItemType(itemType.Sterilise);
    }



}
