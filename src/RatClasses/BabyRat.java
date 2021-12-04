package RatClasses;

import Game.Level;
import Sprites.ImageRefs;

import java.util.Random;

public class BabyRat extends Rat{
    private Boolean isBaby;

    public BabyRat(char sex,  int xPos, int yPos /*int speed, Boolean alive, Boolean isSterile,*/) {
        super(sex, xPos, yPos /*,speed, alive, isSterile*/);
        this.setImage(ImageRefs.babyRatUp);
        Level.getInstance().getLevelBoard().addRat(this);
    }

}
