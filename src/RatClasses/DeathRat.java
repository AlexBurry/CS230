package RatClasses;

import Game.Level;
import ItemClasses.DeathRatItem;
import Sprites.ImageRefs;

import java.util.Random;

public class DeathRat extends Rat{

    private DeathRatItem item;

    public DeathRat(char sex, boolean isDeath, boolean alive, boolean isSterile, int xPos, int yPos) {
        super(sex, isDeath, alive, isSterile, xPos, yPos,false);
        this.setImage(ImageRefs.deathRatUp);

    }

    public DeathRatItem getItem(){
        return item;
    }

    public void setItem(DeathRatItem item){
        this.item = item;
    }

    @Override
    public void changeSprite() {
        switch (getCurrentDirection()) {
            case EAST -> setImage(ImageRefs.deathRatRight);
            case WEST -> setImage(ImageRefs.deathRatLeft);
            case NORTH -> setImage(ImageRefs.deathRatUp);
            case SOUTH -> setImage(ImageRefs.deathRatDown);
        }
    }



}