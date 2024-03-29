package ratClasses;

import sprites.ImageRefs;

/**
 * baby rats grow into adult rats.
 *
 * @author Marcus
 * @version 1
 */

public class BabyRat extends Rat {

    /**
     * Constructor for baby rat. It takes all its data from the Rats class and sets its sprite as a baby rat.
     *
     * @param sex  gender of rat
     * @param xPos x position
     * @param yPos y position
     */
    public BabyRat(char sex, int xPos, int yPos) {
        super(sex, xPos, yPos);
        this.setImage(ImageRefs.babyRatUp);
        setBaby(true);
    }

    /**
     * changes the baby sprite depending on the direction it's going.
     */
    @Override
    public void changeSprite() {
        switch (getCurrentDirection()) {
            case EAST -> setImage(ImageRefs.babyRatRight);
            case WEST -> setImage(ImageRefs.babyRatLeft);
            case NORTH -> setImage(ImageRefs.babyRatUp);
            case SOUTH -> setImage(ImageRefs.babyRatDown);
            default -> throw new IllegalStateException("Unexpected value: " + getCurrentDirection());
        }
    }

    /**
     * This is an event listener.
     * Called every x seconds by the Level class
     */
    @Override
    public void tickEvent(int count) {
        final int TICKS_PER_SECOND = 4;
        //If 250ms have passed
        getInstance().getLevelBoard().redrawTile(getX(), getY(), true);
        move();

        if (count == TICKS_PER_SECOND) { //If one second has passed.
            gestationGrowthCounter();

            if (isInGas()) {
                setSecondsInGas(getSecondsInGas() + 1);
            } else {
                setSecondsInGas(0); //if we are not in gas, we are safe.
            }
        }

    }
}
