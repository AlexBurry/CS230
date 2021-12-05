package RatClasses;

import Game.Level;
import Sprites.ImageRefs;

import java.util.Random;

public class BabyRat extends Rat {



    public BabyRat(char sex, int xPos, int yPos /*int speed, Boolean alive, Boolean isSterile,*/) {
        super(sex, xPos, yPos /*,speed, alive, isSterile*/);
        this.setImage(ImageRefs.babyRatUp);
        //Level.getInstance().getLevelBoard().addRat(this);
        setBaby(true);
    }

    @Override
    public void changeSprite() {
        switch (getCurrentDirection()) {
            case EAST -> setImage(ImageRefs.babyRatRight);
            case WEST -> setImage(ImageRefs.babyRatLeft);
            case NORTH -> setImage(ImageRefs.babyRatUp);
            case SOUTH -> setImage(ImageRefs.babyRatDown);
        }
    }

    /**
     * This is an event listener.
     * Called every x seconds by the Level class
     */
    @Override
    public void tickEvent(int count) {

        //If 250ms have passed
        getInstance().getLevelBoard().redrawTile(getX(), getY(), true);
        move();
        if (getInstance().getLevelBoard().getTileMap()[getX()][getY()].getTileType().equalsIgnoreCase("t")) {
            getInstance().getLevelBoard().redrawTile(getX(), getY(), false);
        }





        if (count == 4) { //If one second has passed.
            counter();

            //giveBirth();

            if (isInGas()) {
                setSecondsInGas(getSecondsInGas() + 1);
            } else {
                setSecondsInGas(0); //if we are not in gas, we are safe.
            }
        }


    }
}
