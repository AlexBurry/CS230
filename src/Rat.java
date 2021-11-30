import javafx.scene.image.Image;

import java.util.Random;
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
    private boolean isDeathRat;
    private boolean alive;
    private boolean isSterile;
    private int speed;

    private Image sprite;
    private double imgWidth;
    private double imgHeight;
    private Level instance;

    private int xPos;
    private int yPos;

    public enum Directions{
        EAST,
        WEST,
        NORTH,
        SOUTH
    }

    private Directions currentDirection;

    //baby rat..
    public Rat(char sex, int xPos, int yPos){
        this.sex = sex;
        this.xPos = xPos;
        this.yPos = yPos;
        speed = 2;
        alive = true;
        isDeathRat = false;
        isSterile = false;
        instance = Level.getInstance();
    }

    public Rat(char sex, boolean isDeathRat, boolean alive, boolean isSterile, int xPos, int yPos, int speed) {
        currentDirection = Directions.NORTH;

        if (sex == 'f' || sex == 'm') {
            this.sex = sex;
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.isDeathRat = isDeathRat;
        this.alive = alive;
        this.isSterile = isSterile;
        instance = Level.getInstance();

    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void move() {
       int newxPos = xPos;
       int newyPos = yPos;
        if(currentDirection == Directions.EAST) {
            newxPos += 1;
        } else if(currentDirection == Directions.WEST) {
            newxPos -= 1;
        } else if(currentDirection == Directions.NORTH) {
            newyPos -= 1;
        } else {
            newyPos += 1;
        }
        if (isTraversable(newxPos, newyPos)) {
            xPos = newxPos;
            yPos = newyPos;
        } else {
                currentDirection = Directions.values()[new Random().nextInt(Directions.values().length)];
        }
    }

    private boolean isTraversable(int x, int y){
         return instance.getLevelBoard().getTileMap()[x][y].getTraversable();
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
