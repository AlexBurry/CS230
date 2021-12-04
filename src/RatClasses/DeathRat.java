package RatClasses;

import Game.Level;
import ItemClasses.DeathRatItem;
import Sprites.ImageRefs;

import java.util.Random;

public class DeathRat extends Rat{

    private DeathRatItem item;

    public DeathRat(char sex, boolean isDeath, boolean alive, boolean isSterile, int xPos, int yPos, int speed) {
        super(sex, isDeath, alive, isSterile, xPos, yPos, speed);
        this.setImage(ImageRefs.deathRatUp);
        Level.getInstance().getLevelBoard().addRat(this);
    }

    public DeathRatItem getItem(){
        return item;
    }

    public void setItem(DeathRatItem item){
        this.item = item;
    }





}