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

    /**
     * Constructor for rat class.
     * Used when loading rats from a file
     *
     * @param sex  the gender of the rat.
     * @param xPos x position.
     * @param yPos y position.
     */
    public Rat(char sex, int xPos, int yPos) {
        currentDirection = Directions.NORTH;

        this.isBaby = Character.isLowerCase(sex);

        if (Character.toLowerCase(sex) == 'f' || Character.toLowerCase(sex) == 'm') {
            this.sex = sex;
        }

        this.xPos = xPos;
        this.yPos = yPos;
        isDeathRat = false;
        isSterile = false;
        instance = Level.getInstance();
        //instance.addListener(this);
        isPregnant = false;
        changeSprite();

    }

    /**
     * Constructor for baby rat and death rat class.
     * Only used for adult rats
     *
     * @param sex  the gender of the rat.
     * @param xPos x position.
     * @param yPos y position.
     */
    public Rat(char sex, boolean isDeathRat, boolean isSterile, int xPos, int yPos, boolean isBaby) {
        currentDirection = Directions.NORTH;
        this.isBaby = isBaby;
        sex = Character.toUpperCase(sex);
        this.sex = sex;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isDeathRat = isDeathRat;
        this.isSterile = isSterile;
        instance = Level.getInstance();

        if (isDeathRat) {
            instance.getLevelBoard().addRat(this);
            instance.addListener(this);
        }


        changeSprite();
        //pregnantDuration();

    }

    /**
     * setter for a baby rat.
     *
     * @param baby a boolean value true/false.
     */
    public void setBaby(boolean baby) {
        isBaby = baby;
    }

    /**
     * a counter for the delay required when female rat gives birth and,
     * the time it takes for a baby to grow into an adult rat.
     */
    public void gestationGrowthCounter() {

        //tick timer: increments one after each tick
        // and resets when certain if statements are reached.
        tickTimer += 1;
        if(isPregnant){
            if(tickTimer == 8){ //8 seconds of gestation, produce children.
                System.out.println("Sex is female, adding to queue: " + gestatingChildren.size());
                babyRatsToQueue();

            }
            else if (tickTimer > 30){ //22 seconds since producing children, reset timer, and allow pregnancy.
                tickTimer = 0;
                isPregnant = false;
            }
        }
        //Deletes this babyRat when it grows
        if (tickTimer == 9 && isBaby) {
            growRat();
        }

    }

    /**
     * removes this rat and adds a new adult rat in the same pos
     */
    public void growRat() {
        Rat adultRat = new Rat(getSex(),isDeathRat,isSterile, xPos, yPos,false);
        instance.addRatToQueue(adultRat);
        instance.getLevelBoard().removeRat(this);
    }

    /**
     * adds new baby rats to the queue and calls giveBirth
     * for the amount of baby rats to be born.
     */
    public void babyRatsToQueue() {

        for (BabyRat rats : gestatingChildren) {
            rats.setPosition(getX(), getY());
            babyRatsQueue.add(rats);
        }
        gestatingChildren.clear();
        giveBirth();
    }

    /**
     * schedules a delay for the rat, before it begins to move again.
     */
    public void waitToReleaseRat() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(releaseRat, 3, TimeUnit.SECONDS);
    }

    private void releaseRat() {
        setCanMove(true);
    }

    /**
     * takes the amount of babyRats in queue and adds the amount of rats to the level,
     * sets the female rat pregnant back to false.
     */
    public void giveBirth() {
        System.out.println("giving birth.");
        for (Rat br : babyRatsQueue) {
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

            checkRatCollision();



            if (count == 4) { //If one second has passed.
                if(sex == 'F'){
                    gestationGrowthCounter();
                }



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
     * changes the direction and position of the rat
     */
    public void move() {
        int newxPos = xPos;
        int newyPos = yPos;
        //remembers the old direction of the rat
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
        //checks if the new coordinates is traversable
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

        if (sex == 'M') {
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
     * and with those available options, it randomly chooses a path to take.
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
        //if there is more than 1 option to go, then it removes the option to go back
        //then randomly chooses a direction to take.
        if (options.size() > 1) {
            options.remove(relativeBack);

            for (int i = 0; i < options.size(); i++) {
                int rand = new Random().nextInt(options.size());
                currentDirection = options.get(rand);
            }
        } else if (options.size() == 1) {
            currentDirection = options.get(0);
        }
        //death rats
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
                            if (secondsInGas >= 2) {
                                deleteRat();
                            }
                        }
                        case MSex -> {
                            instance.getLevelBoard().getToolBar().setSexChanged(true);
                            if(isBaby){
                                sex = 'm';
                            }else{
                                sex = 'M';
                            }

                            itemsToDeleteOnCollision.add(it);
                        }
                        case FSex -> {
                            instance.getLevelBoard().getToolBar().setSexChanged(true);
                            if(isBaby){
                                sex = 'f';
                            }else{
                                sex = 'F';
                            }
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
     * checks if rats collide into each other, and
     * if two male and female rats collide they stay in the same tile and have sex.
     * The female rat becomes pregnant, and adds baby rats
     * into gestating children to prepare to give birth.
     */
    public void checkRatCollision() {
        ArrayList<Rat> existingRats = instance.getLevelBoard().getRats();

        for (Rat rt : existingRats) {
            if (rt != this) {
                if(sex == 'M'){
                    if (rt.getX() == xPos && rt.getY() == yPos) {

                        //check if male and female rat in same tile then sexy time

                        if (rt.getSex() == 'F' && !rt.isPregnant && !(rt.isSterile || isSterile)) {

                            int numOfBabies = new Random().nextInt(5);
                            if(numOfBabies == 0){
                                numOfBabies = 1;
                            }
                            for (int i = 0; i < numOfBabies; i++) {
                                char ratGender = new Random().nextBoolean() ? 'f' : 'm';
                                BabyRat babyRat = new BabyRat(ratGender, xPos, yPos);
                                rt.gestatingChildren.add(babyRat);
                                System.out.println("Added a child to list. Size: " + rt.gestatingChildren.size());

                            }
                            rt.isPregnant = true;
                            this.canMove = false;
                            rt.setCanMove(false);
                            waitToReleaseRat();
                            rt.waitToReleaseRat();


                        }

                    }
                }

            }
        }
    }

    /**
     * sets if rat can move.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     * checks if the tile is traversable.
     * It is traversable if it is a path, or a sewer tile.
     */
    public boolean isTraversable(int x, int y) {
        return instance.getLevelBoard().getTileMap()[x][y].getTraversable();
    }

    /**
     * sets the image of the sprite.
     */
    public void setImage(Image newImage) {
        sprite = newImage;
    }

    /**
     * gets the sprite.
     *
     * @return type image
     */
    public Image getSprite() {
        return sprite;
    }

    /**
     * sets the position.
     *
     * @param x horizontal axis
     * @param y vertical axis
     */
    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * deletes rat from level, and increases the score every time a rat is removed.
     */
    public void deleteRat() {

        if (!isDeathRat) {
            instance.increaseScore(10);
        }
        if (gestatingChildren.size() > 0) {
            for (Rat bRat : gestatingChildren) {
                instance.increaseScore(10);
            }
        }

        instance.getLevelBoard().removeRat(this);
    }

    //sterilize rat method
    public void sterilizeRat() {
        isSterile = true;
    }

    /**
     * gets if the rat is sterile.
     */
    public boolean getIsSterile() {
        return isSterile;
    }

    /**
     * gets current direction
     */
    public Directions getCurrentDirection() {
        return currentDirection;
    }

    /**
     * checks if it's in gas
     */
    public boolean isInGas() {
        return inGas;
    }

    /**
     * gets the instance of the level
     */
    public Level getInstance() {
        return instance;
    }

    /**
     * gets the seconds in gas
     */
    public int getSecondsInGas() {
        return secondsInGas;
    }

    /**
     * sets the seconds in gas
     */
    public void setSecondsInGas(int secondsInGas) {
        this.secondsInGas = secondsInGas;
    }

    /**
     * gets the value of isBaby
     *
     * @return boolean
     */
    public boolean getIsBaby() {
        return isBaby;
    }

    /**
     * gets x position
     *
     * @return int
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * gets y position
     *
     * @return int
     */
    public int getyPos() {
        return yPos;
    }

    public void setSex(char sex) {
        this.sex = sex;

        if (this.sex == 'F' || this.sex == 'M') {
            this.isBaby = false;
        } else {
            isBaby = true;
        }
    }
}
