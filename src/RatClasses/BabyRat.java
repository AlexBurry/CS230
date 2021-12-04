package RatClasses;

import java.util.Random;

public class BabyRat extends Rat{
    private Boolean isBaby;

    public BabyRat(char sex,  int xPos, int yPos /*int speed, Boolean alive, Boolean isSterile,*/) {
        super(sex, xPos, yPos /*,speed, alive, isSterile*/);
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
