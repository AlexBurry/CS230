package ItemClasses;

import javafx.scene.image.Image;

/**
 * The main gas node of a gas item. Controls gas dissipation
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class GasItem extends Item{

    public GasItem (int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("Sprites/Bomb4.png"));
        this.setMyItemType(itemType.Gas);
    }



}
