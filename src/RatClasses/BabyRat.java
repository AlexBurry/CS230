package RatClasses;

import Game.Level;
import Sprites.ImageRefs;

import java.util.Random;

public class BabyRat extends Rat{

    public BabyRat(char sex,  int xPos, int yPos /*int speed, Boolean alive, Boolean isSterile,*/) {
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

}
