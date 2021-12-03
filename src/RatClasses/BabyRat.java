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

    @Override
    public void move() {
        int newxPos = getX();
        int newyPos = getY();


        if (getCurrentDirection() == Directions.EAST) {
            newxPos += 1;
        } else if (getCurrentDirection() == Directions.WEST) {
            newxPos -= 1;
        } else if (getCurrentDirection() == Directions.NORTH) {
            newyPos -= 1;
        } else {
            newyPos += 1;
        }
        if (isTraversable(newxPos, newyPos)) {
            setPosition(newxPos,newyPos);

        } else {
            Directions oldDirection = getCurrentDirection();
            setCurrentDirection(Directions.values()[new Random().nextInt(Directions.values().length)]);

        }
    }


}
