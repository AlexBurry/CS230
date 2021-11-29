import javafx.scene.image.Image;
import java.util.Vector;
import javafx.scene.canvas.GraphicsContext;

/**
 * The main rat class. Covers all basic features of Rats.
 * @author Marcus
 * @author Iggy
 * @version 0.1
 * @since 0.1
 */

public class Rat {

    private boolean sex;
    private boolean isBaby;
    private boolean isDeath;
    private boolean alive;
    private boolean isSterile;
    private int speed;

    private Image sprite;
    private double imgWidth;
    private double imgHeight;

    private Vector position;
    private Vector velocity;




    public Rat(boolean sex, boolean isBaby, boolean isDeathRat, boolean alive, boolean isSterile) {
        this.sex = sex;
        this.isBaby = isBaby;
        this.isDeath = isDeathRat;
        this.alive = alive;
        this.isSterile = isSterile;

        position = new Vector(0,0);
        velocity = new Vector(0,0);
    }
        /*
        sets and gets the width and height of the image.
        currently imgWidth and imgHeight has no use.
         */
    public void setImage(String filename){
        sprite = new Image("rat.png");
    }

    public Image getSprite() {
        return sprite = new Image("spriteRat.png");
    }

    public void setPosition(double x, double y){
        //position.set(x,y);
    }

    public void draw(int x, int y) {

    }

    @Override
    public String toString() {
        return "";
    }

    public void initiateSexChange(char gender){
        if(!isBaby){
            if(gender == 'm'){

            }
            else{

            }
        }


    }
    
}
