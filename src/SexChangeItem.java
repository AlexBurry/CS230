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
        sprite = new Image("raticon.png");
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void draw(int x, int y) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteItem() {
        // TODO Auto-generated method stub
        
    }

}
