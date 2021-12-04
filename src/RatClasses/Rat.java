package RatClasses;

import Game.Level;
import Game.Tile;
import ItemClasses.NoEntryItem;
import Sprites.ImageRefs;
import ItemClasses.DeathRatItem;
import ItemClasses.Item;
import Game.ITickHandler;
import javafx.scene.image.Image;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Random;

/**
 * The main rat class. Covers all basic features of Rats.
 *
 * @author Marcus, Iggy
 * @version 0.1
 * @since 0.1
 */

public class Rat implements ITickHandler {

    private char sex;
    private boolean isDeathRat;
    private boolean alive;
    private boolean isSterile;
    private int speed;
    private boolean isPregnant;
    private int tickTimer;
    private int internalTickCount;
    private boolean isBaby;
    private Image sprite;

    private double imgWidth;
    private double imgHeight;
    private Level instance;


    private int xPos;
    private int yPos;

    private ArrayList<Item> itemsToDeleteOnCollision = new ArrayList<>();
    private ArrayList<Rat> babyRatsQueue = new ArrayList<>();


    public enum Directions {
        EAST,
        WEST,
        NORTH,
        SOUTH
    }

    private Directions currentDirection;
    private Directions oldDirection;

    //constructor for baby rat..
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
        //instance.addListener(this);
        isPregnant = false;
        changeSprite();

    }

    //To be checked -> constructor below not actually being used for male and female rat? only death rat????
    public Rat(char sex, boolean isDeathRat, boolean alive, boolean isSterile, int xPos, int yPos, int speed, boolean isBaby) {
        currentDirection = Directions.NORTH;
        this.isBaby = isBaby;
        if (sex == 'f' || sex == 'm') {
            this.sex = sex;
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.isDeathRat = isDeathRat;
        this.alive = alive;
        this.isSterile = isSterile;
        instance = Level.getInstance();
        //instance.addListener(this);
        if (isDeathRat) {
            sprite = ImageRefs.deathRatUp;
        } else {
            changeSprite();
        }
        //pregnantDuration();

    }

    public void setBaby(boolean baby) {
        isBaby = baby;
    }

    public void pregnantDuration() {
        if (isPregnant && tickTimer < 3) {
            tickTimer += 1;
            System.out.println("during pregnancy " + tickTimer);
        } else {
            tickTimer = 0;
            isPregnant = false;
            System.out.println("no longer pregnant");
        }
    }

    public void counter(){
        tickTimer += 1;
        //resets the counter
        if (tickTimer == 4){
            tickTimer = 0;
            //if the female rat is pregnant it will no longer be.
            if(sex == 'f') {
                isPregnant = false;
                //System.out.println("no longer pregnant " + isPregnant);

            }
        }
    }

    public void giveBirth(){
        for(Rat br : babyRatsQueue){
            instance.addRatToQueue(br);
        }
        babyRatsQueue.clear();
    }

    /**
     * This is an event listener.
     * Called every x seconds by the Level class
     */
    @Override
    public void tickEvent(int count) {

        if (count == 2 || count == 4) {
            instance.getLevelBoard().redrawTile(xPos, yPos, true);
            move();
            if (instance.getLevelBoard().getTileMap()[xPos][yPos].getTileType().equalsIgnoreCase("t")) {
                instance.getLevelBoard().redrawTile(xPos, yPos, false);
            }
            if(count == 4) {
                counter();
            }
            giveBirth();
            checkRatCollision();

        }

    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    /**
     * @author Marcus and Trafford
     */
    public void move() {
        int newxPos = xPos;
        int newyPos = yPos;

        oldDirection = currentDirection;

        detectOptions();
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
            if (isDeathRat) {
                ((DeathRat) this).getItem().updatePos();
            }
            changeSprite();

        }

    }

    /**
     * @author Trafford
     * changes the direction the rat is facing depending on the direction it is going.
     */
    public void changeSprite() {
        if (sex == 'm') {
            switch (currentDirection) {
                case EAST -> sprite = ImageRefs.maleRatRight;
                case WEST -> sprite = ImageRefs.maleRatLeft;
                case NORTH -> sprite = ImageRefs.maleRatUp;
                case SOUTH -> sprite = ImageRefs.maleRatDown;
            }
        } else {
            switch (currentDirection) {
                case EAST -> sprite = ImageRefs.femaleRatRight;
                case WEST -> sprite = ImageRefs.femaleRatLeft;
                case NORTH -> sprite = ImageRefs.femaleRatUp;
                case SOUTH -> sprite = ImageRefs.femaleRatDown;
            }
        }
    }

    /**
     * @author Trafford and Marcus
     * checks for all available options for the rat to go,
     * and with those available options, it randomly chooses
     * a path to take.
     */
    public void detectOptions() {
        boolean f;
        boolean b;
        boolean l;
        boolean r;
        Directions relativeBack = switch (oldDirection) {
            case SOUTH -> Directions.NORTH;
            case NORTH -> Directions.SOUTH;
            case WEST -> Directions.EAST;
            case EAST -> Directions.WEST;
        };

        Tile[][] tileMap = instance.getLevelBoard().getTileMap();
        f = tileMap[xPos][yPos - 1].getTraversable();
        b = tileMap[xPos][yPos + 1].getTraversable();
        l = tileMap[xPos - 1][yPos].getTraversable();
        r = tileMap[xPos + 1][yPos].getTraversable();

        ArrayList<Directions> options = new ArrayList<>();
        if (f && b && l && r) {
            options.add(Directions.NORTH);
            options.add(Directions.WEST);
            options.add(Directions.EAST);
            options.add(Directions.SOUTH);
        } else if (f) {
            options.add(Directions.NORTH);
            if (l) {
                options.add(Directions.WEST);
            }
            if (r) {
                options.add(Directions.EAST);
            }
            if (b) {
                options.add(Directions.SOUTH);
            }
        } else if (b) {
            options.add(Directions.SOUTH);
            if (l) {
                options.add(Directions.WEST);
            }
            if (r) {
                options.add(Directions.EAST);
            }
        } else if (l) {
            options.add(Directions.WEST);
            if (r) {
                options.add(Directions.EAST);
            }
        } else if (r) {
            options.add(Directions.EAST);
        }

        if (options.size() > 1) {
            if (options.contains(relativeBack)) {
                options.remove(relativeBack);
            }
            for (int i = 0; i < options.size(); i++) {
                int rand = new Random().nextInt(options.size());
                currentDirection = options.get(rand);
            }
        } else if (options.size() == 1) {
            currentDirection = options.get(0);
        }
        if (!isDeathRat) {
            checkCollision();
        }

    }

    /**
     * @author Trafford (made for Items)
     * Goes through each item on the board and checks if its at our location.
     */
    public void checkCollision() {
        ArrayList<Item> existingItems = instance.getLevelBoard().getItems();
        for (Item it : existingItems) {
            if (it != null) {
                if (it.getX() == xPos && it.getY() == yPos) {
                    //Falls through cases to check if available.
                    switch (it.getMyItemType()) {
                        case Poison -> {
                            itemsToDeleteOnCollision.add(it); //adds it to the array to be deleted after we have iterated
                            deleteRat();
                        }
                        case Gas -> System.out.println("Gas");
                        //case Sterilise -> System.out.println("Sterilise");

                        case MSex -> {
                            sex = 'm';
                            itemsToDeleteOnCollision.add(it);}
                        case FSex -> {
                            sex = 'f';
                            itemsToDeleteOnCollision.add(it);
                        }

                        case NoEntry -> {
                            currentDirection = switch (oldDirection) {
                                case SOUTH -> Directions.NORTH;
                                case NORTH -> Directions.SOUTH;
                                case WEST -> Directions.EAST;
                                case EAST -> Directions.WEST;
                            };
                            NoEntryItem noEntryItem = (NoEntryItem) it;
                            noEntryItem.hit();
                            if (noEntryItem.shouldKill()) {
                                itemsToDeleteOnCollision.add(it);
                            }
                        }
                        case DeathRat -> {
                            if (!this.isDeathRat) {
                                DeathRatItem a = (DeathRatItem) it;
                                a.incrementKills();
                                deleteRat();
                            }

                        }
                    }
                }
            }


        }

        for (Item it : itemsToDeleteOnCollision) {
            it.deleteItem();
        }
    }

    public char getSex() {
        return sex;
    }

    /**
     *
     * checks if rats collide into each other
     */
    public void checkRatCollision() {
        ArrayList<Rat> existingRats = instance.getLevelBoard().getRats();

        for (Rat rt : existingRats) {
            if (rt != this) {
                if (rt.getX() == xPos && rt.getY() == yPos) {

                    //check if male and female rat in same tile then sexy time
                    if (sex == 'f' && rt.getSex() == 'm' && !isPregnant && !isBaby) {
                        isPregnant = true;
                        System.out.println("this rat is now pregnant = " + isPregnant);
                        char ratGender = new Random().nextBoolean() ? 'f' : 'm';
                        BabyRat babyRat = new BabyRat(ratGender, xPos, yPos);
                        babyRatsQueue.add(babyRat);
                        System.out.println("added baby rat");
                    }

                }
            }
        }

    }

    public void listOfItems() {
        System.out.println(instance.getLevelBoard().getItems());
    }

    public boolean isTraversable(int x, int y) {
        return instance.getLevelBoard().getTileMap()[x][y].getTraversable();
    }

    /*
    sets and gets the width and height of the image.
    currently imgWidth and imgHeight has no use.
     */
    public void setImage(Image newImage) {
        sprite = newImage;
    }

    public Image getSprite() {
        if (isDeathRat) {
            return sprite;
        }
        if (sex == 'm') {
            return sprite;
        }
        //if female
        else {
            return sprite;
        }
    }

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    // kill rat method
    public void deleteRat() {
        instance.markListenerForRemoval(this);
        instance.getLevelBoard().removeRat(this);
        instance.increaseScore(5);
    }

    //sterilize rat method
    public void sterilizeRat() {
        isSterile = true;
    }

    public boolean getIsSterile(){return isSterile;};

    public Directions getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Directions currentDirection) {
        this.currentDirection = currentDirection;
    }

    @Override
    public String toString() {
        return "";
    }


    /**
     * @author Jack
     * takes in a gender for rats to become, checks the rat isn't the same as the gender
     */
    public void initiateSexChange(char gender) {
            sex = gender;
        }
}
