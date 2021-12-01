import javafx.scene.image.Image;

/**
 * Poison the rat and kill it
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public class PoisonItem extends Item{ //Poison is more of an electric trap in our art-style.

    /**
     * Simple class, doesn't need any unique methods as Rat handles it.
     *
     * Set all relevant info for Poison.
     * @param x the XCoordinate.
     * @param y the YCoordinate.
     */
    public PoisonItem (int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("PoisonTrap.png"));
        this.setMyItemType(itemType.Poison);

    }


    
}
