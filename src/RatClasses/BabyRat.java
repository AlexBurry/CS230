package RatClasses;

import java.util.Random;

public class BabyRat extends Rat{
    private Boolean isBaby;

    public BabyRat(char sex, Boolean isBaby, Boolean isDeath, Boolean alive, Boolean isSterile, int xPos, int yPos, int speed) {
        super(sex, isDeath, alive, isSterile, xPos, yPos, speed);
        if (isBaby){
                /*some tick rate timer where after a certain amount of ticks pass
                this isBaby becomes false and turns into adult rat.
                 */
                //tickEvent();
                isBaby = false;
            } else {

            }
    }



}
