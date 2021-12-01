import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The main rat class. Covers all basic features of Rats.
 *
 * @author Marcus
 * @author Iggy
 * @version 0.1
 * @since 0.1
 */

public class Rat implements Tick{

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

    private ArrayList<Item> itemsToDeleteOnCollision = new ArrayList<>();



    public enum Directions {
        EAST,
        WEST,
        NORTH,
        SOUTH
    }

    private enum DirectionsWhenFaceNorth {
        WEST,
        EAST
    }

    private enum DirectionsWhenFaceWest {
        NORTH,
        SOUTH,
    }

    public enum DirectionsWhenFaceEAST {
        NORTH,
        SOUTH
    }

    public enum DirectionWhenFaceSouth {
        WEST,
        EAST
    }

    private Directions currentDirection;

    //baby rat..
    public Rat(char sex, int xPos, int yPos) {
        currentDirection = Directions.NORTH;
        this.sex = sex;
        this.xPos = xPos;
        this.yPos = yPos;
        speed = 2;
        alive = true;
        isDeathRat = false;
        isSterile = false;
        instance = Level.getInstance();
        instance.addListener(this);
        this.sprite = new Image("babyRat.png");
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
        instance.addListener(this);
        this.sprite = new Image("ratMale.png");

    }

    /**
     * This is an event listener.
     * Called every x seconds by the Level class
     */
    @Override
    public void tickEvent() {
        instance.getLevelBoard().redrawTile(xPos,yPos,true);
        move();
        checkCollision();
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


        if (currentDirection == Directions.EAST) {
            newxPos += 1;
        } else if (currentDirection == Directions.WEST) {
            newxPos -= 1;
        } else if (currentDirection == Directions.NORTH) {
            newyPos -= 1;
        } else {
            newyPos += 1;
        }
        if (isTraversable(newxPos, newyPos)) {
            xPos = newxPos;
            yPos = newyPos;
        } else {
            Directions oldDirection = currentDirection;
            currentDirection = Directions.values()[new Random().nextInt(Directions.values().length)];
           /* while (currentDirection == oldDirection) {
                currentDirection = Directions.values()[new Random().nextInt(DirectionsWhenFaceNorth.values().length)];
                System.out.println(Arrays.toString(DirectionsWhenFaceNorth.values()));
            }*/
        }
    }

    /**
     * @author Trafford (made for Items)
     * Goes through each item on the board and checks if its at our location.
     */
    public void checkCollision(){

        for (Item it: instance.getLevelBoard().getItems()) {
            if(it.getX() == xPos && it.getY() == yPos){
                //Falls through cases to check if available.
                switch (it.getMyItemType()){
                    case Poison -> {
                        itemsToDeleteOnCollision.add(it); //adds it to the array to be deleted after we have iterated
                        deleteRat();
                    }
                    case Gas -> System.out.println("Gas");
                    case Sterilise -> System.out.println("Sterilise");
                    case MSex -> System.out.println("Male Sex Change");
                    case FSex -> System.out.println("Female Sex Change");
                    case NoEntry -> System.out.println("No Entry");
                    case DeathRat -> {
                        DeathRatItem a = (DeathRatItem) it;
                        a.incrementKills();
                        deleteRat();
                    }
                }
            }
        }

        for (Item it : itemsToDeleteOnCollision){
            it.deleteItem();
        }
    }

    public void listOfItems() {
        System.out.println( instance.getLevelBoard().getItems());
    }

    private boolean isTraversable(int x, int y) {
        return instance.getLevelBoard().getTileMap()[x][y].getTraversable();
    }

    /*
    sets and gets the width and height of the image.
    currently imgWidth and imgHeight has no use.
     */
    public void setImage(String filename) {
        sprite = new Image(filename);
    }

    public Image getSprite() {
        if(isDeathRat){
            return new Image("DeathRat.png");
        }
        if (sex == 'm') {
            return sprite = new Image("ratMale.png");
        }
        else{
            return sprite = new Image("ratFemale.png");
        }
    }

    public void setPosition(double x, double y) {
        //position.set(x,y);
    }

    public void draw(int x, int y) {

    }

    // kill rat method
    public void deleteRat() {
        instance.getLevelBoard().removeRat(this);
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
