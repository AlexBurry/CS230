import javafx.scene.image.Image;

import java.util.Vector;

import javafx.scene.canvas.GraphicsContext;

/**
 * The main rat class. Covers all basic features of Rats.
 *
 * @author Marcus
 * @author Iggy
 * @version 0.1
 * @since 0.1
 */

public class Rat {

    private char sex;
    private boolean isBaby;
    private boolean isDeath;
    private boolean alive;
    private boolean isSterile;
    private int speed;

    private Image sprite;
    private double imgWidth;
    private double imgHeight;
    private Level instance;

    private Vector position;
    private Vector velocity;

    private int xPos;
    private int yPos;

    public enum Directions{
        EAST,
        WEST,
        NORTH,
        SOUTH
    }

    private Directions currentDirection;

    public Rat(char sex, boolean isBaby, boolean isDeathRat, boolean alive, boolean isSterile, int xPos, int yPos) {
        currentDirection = Directions.NORTH;

        if (sex == 'f' || sex == 'm') {
            this.sex = sex;
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.isBaby = isBaby;
        this.isDeath = isDeathRat;
        this.alive = alive;
        this.isSterile = isSterile;

        position = new Vector(0, 0);
        velocity = new Vector(0, 0);
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void move() {
        if(currentDirection == Directions.EAST) {
            // Do something. Write your logic
        } else if(currentDirection == Directions.WEST) {
            // Do something else
        } else if(currentDirection == Directions.NORTH) {
            yPos -= 1;
        } else {
            /* Do Something. Write logic for
             * the remaining constant SOUTH
             */
        }
    }

    /*
    sets and gets the width and height of the image.
    currently imgWidth and imgHeight has no use.
     */
    public void setImage(String filename) {
        sprite = new Image("spriteRat.png");
    }

    public Image getSprite() {
        return sprite = new Image("spriteRat.png");
    }

    public void setPosition(double x, double y) {
        //position.set(x,y);
    }

    public void draw(int x, int y) {

    }

    // kill rat method
    public void deleteRat(){
        instance.getLevelBoard().removeRatFromMap(this);
    }

    @Override
    public String toString() {
        return "";
    }

    public void initiateSexChange(char gender) {
        if (gender != sex) {
            if (gender == 'm') {
                sex = 'f';
            } else {
                sex = 'm';
            }
        }
    }

}
