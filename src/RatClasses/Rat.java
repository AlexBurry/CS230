package RatClasses;

import Game.Level;
import Game.Tile;
import ItemClasses.NoEntryItem;
import Sprites.ImageRefs;
import ItemClasses.DeathRatItem;
import ItemClasses.Item;
import Game.ITickHandler;
import javafx.scene.image.Image;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private final boolean isDeathRat;
    private boolean isSterile;
    private boolean isPregnant;
    private boolean inGas;
    private int secondsInGas;
    private int tickTimer;
    private boolean isBaby;
    private Image sprite;
    private boolean canMove = true;
    Runnable releaseRat = () -> releaseRat(); //Assigns the method to be a "runnable", used to execute a wait function.


    private double imgWidth;
    private double imgHeight;
    private final Level instance;

    private int xPos;
    private int yPos;

    private ArrayList<Item> itemsToDeleteOnCollision = new ArrayList<>();
    private ArrayList<Rat> babyRatsQueue = new ArrayList<>();
    private ArrayList<BabyRat> gestatingChildren = new ArrayList<>();


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
        isDeathRat = false;
        isSterile = false;
        instance = Level.getInstance();
        //instance.addListener(this);
        isPregnant = false;
        changeSprite();

    }

    //To be checked -> constructor below not actually being used for male and female rat? only death rat????
    public Rat(char sex, boolean isDeathRat, boolean alive, boolean isSterile, int xPos, int yPos, boolean isBaby) {
        currentDirection = Directions.NORTH;
        this.isBaby = isBaby;
        if (sex == 'f' || sex == 'm') {
            this.sex = sex;
        }
        this.xPos = xPos;
        this.yPos = yPos;
        this.isDeathRat = isDeathRat;
        this.isSterile = isSterile;
        instance = Level.getInstance();

        if(isDeathRat){
            instance.getLevelBoard().addRat(this);
            instance.addListener(this);
        }


        changeSprite();
        //pregnantDuration();

    }

    public void setBaby(boolean baby) {
        isBaby = baby;
    }

    public void counter(){

        //tick timer: increments one after each tick
        // and resets when certain if statements are reached.
        tickTimer += 1;
        //resets the counter

        if (tickTimer == 8 && !isBaby) {
            tickTimer = 0;
            //if the female rat is pregnant it will no longer be.
            if (sex == 'f' ) {
    
                babyRatsToQueue();
            }
        } else if (tickTimer == 9 && isBaby) {

            setBaby(false);
            tickTimer = 0;
            Rat adultRat = new Rat(getSex(), xPos, yPos);
            instance.getLevelBoard().removeRat(this);
            instance.addRatToQueue(adultRat);
        }
    }

    public void babyRatsToQueue() {
        for (BabyRat rats : gestatingChildren) {
            rats.setPosition(getX(), getY());
            babyRatsQueue.add(rats);
        }
        gestatingChildren.clear();
        giveBirth();
    }

    public void waitToReleaseRat() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(releaseRat, 3, TimeUnit.SECONDS);
    }

    private void releaseRat() {
        setCanMove(true);
    }

    public void giveBirth() {
        for (Rat br : babyRatsQueue) {
            instance.addRatToQueue(br);
        }
        babyRatsQueue.clear();
        isPregnant = false;
    }

    /**
     * This is an event listener.
     * Called every x seconds by the Level class
     */
    @Override
    public void tickEvent(int count) {

        //250ms 1/4 1s
        //1...2...3....4 > 1
        //2 and 4 turns up half the time 2/4
        if (count == 2 || count == 4) { //If 500ms have passed (2/4 values is 500(ms) / 1000(1s))
            instance.getLevelBoard().redrawTile(xPos, yPos, true);

            if (canMove) {
                move();
            }
            if (instance.getLevelBoard().getTileMap()[xPos][yPos].getTileType().equalsIgnoreCase("t")) {
                instance.getLevelBoard().redrawTile(xPos, yPos, false);
            }




            checkRatCollision(); //do this before to make sure we are still in gas.


            if (count == 4) { //If one second has passed.
                counter();


                if (inGas) {
                    secondsInGas++;
                } else {
                    secondsInGas = 0; //if we are not in gas, we are safe.
                }
            }


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
            changeSprite();
            if (isDeathRat) {
                ((DeathRat) this).getItem().updatePos();
            }


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
        boolean north;
        boolean south;
        boolean west;
        boolean east;
        Directions relativeBack = switch (oldDirection) {
            case SOUTH -> Directions.NORTH;
            case NORTH -> Directions.SOUTH;
            case WEST -> Directions.EAST;
            case EAST -> Directions.WEST;
        };

        Tile[][] tileMap = instance.getLevelBoard().getTileMap();

        north = tileMap[xPos][yPos - 1].getTraversable();
        south = tileMap[xPos][yPos + 1].getTraversable();
        west = tileMap[xPos - 1][yPos].getTraversable();
        east = tileMap[xPos + 1][yPos].getTraversable();

        ArrayList<Directions> options = new ArrayList<>();
        if (north && south && west && east) {
            options.add(Directions.NORTH);
            options.add(Directions.WEST);
            options.add(Directions.EAST);
            options.add(Directions.SOUTH);
        } else if (north) {
            options.add(Directions.NORTH);
            if (west) {
                options.add(Directions.WEST);
            }
            if (east) {
                options.add(Directions.EAST);
            }
            if (south) {
                options.add(Directions.SOUTH);
            }
        } else if (south) {
            options.add(Directions.SOUTH);
            if (west) {
                options.add(Directions.WEST);
            }
            if (east) {
                options.add(Directions.EAST);
            }
        } else if (west) {
            options.add(Directions.WEST);
            if (east) {
                options.add(Directions.EAST);
            }
        } else if (east) {
            options.add(Directions.EAST);
        }

        if (options.size() > 1) {
            options.remove(relativeBack);

            for (int i = 0; i < options.size(); i++) {
                int rand = new Random().nextInt(options.size());
                currentDirection = options.get(rand);
            }
        } else if (options.size() == 1) {
            currentDirection = options.get(0);
        }
        if (!isDeathRat) {
            checkItemCollision();
        }

    }

    /**
     * @author Trafford (made for Items)
     * Goes through each item on the board and checks if its at our location.
     */

    public void checkItemCollision() {
        ArrayList<Item> existingItems = instance.getLevelBoard().getItems();
        inGas = false; //always start off as false
        for (Item it : existingItems) {
            if (it != null) {
                if (it.getX() == xPos && it.getY() == yPos) {
                    //Falls through cases to check if available.
                    switch (it.getMyItemType()) {
                        case Poison -> {
                            itemsToDeleteOnCollision.add(it); //adds it to the array to be deleted after we have iterated
                            deleteRat();
                        }
                        case Gas -> {
                            inGas = true; //change back to true before the method finishes.
                            System.out.println(secondsInGas);

                            if (secondsInGas >= 2) {
                                instance.getLevelBoard().removeRat(this);
                            }
                        }
                        case MSex -> {
                            sex = 'm';
                            itemsToDeleteOnCollision.add(it);
                        }
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
                            DeathRatItem a = (DeathRatItem) it;
                            a.incrementKills();
                            deleteRat();


                        }
                    }
                }
            }


        }

        for (Item it : itemsToDeleteOnCollision) {
            it.deleteItem();
        }


        if (!inGas) {
            secondsInGas = 0;
        }
    }

    public char getSex() {
        return sex;
    }

    /**
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

                        int numOfBabies = new Random().nextInt(3);
                        for (int i = 0; i < numOfBabies; i++) {
                            char ratGender = new Random().nextBoolean() ? 'f' : 'm';
                            BabyRat babyRat = new BabyRat(ratGender, xPos, yPos);
                            gestatingChildren.add(babyRat);
                            System.out.println("Added a child to list. Size: " + gestatingChildren.size());
                        }
                        this.canMove = false;
                        rt.setCanMove(false);
                        waitToReleaseRat();
                        rt.waitToReleaseRat();


                    }

                }
            }
        }
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
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
        return sprite;
    }

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    // kill rat method
    public void deleteRat() {
        //instance.markListenerForRemoval(this);
        instance.getLevelBoard().removeRat(this);
        instance.increaseScore(10);

        if(babyRatsQueue.size() > 0){
            for (Rat bRat:babyRatsQueue) {
                instance.increaseScore(10);
            }
        }
    }

    //sterilize rat method
    public void sterilizeRat() {
        isSterile = true;
    }


    public boolean getIsSterile() {
        return isSterile;
    }

    public Directions getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Directions currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isInGas() {
        return inGas;
    }

    public Level getInstance() {
        return instance;
    }

    public int getSecondsInGas() {
        return secondsInGas;
    }

    public void setSecondsInGas(int secondsInGas) {
        this.secondsInGas = secondsInGas;
    }

    public boolean getIsBaby() {
        return isBaby;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }


    @Override
    public String toString() {
        return "";
    }
}
