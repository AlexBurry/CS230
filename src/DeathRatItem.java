import javafx.scene.image.Image;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Trafford
 * @version 1.0
 * @see Item
 * Represents the Item behind a death rat. Follows it around the board.
 * Keeps track of kills, Handles deletion.
 * @since 01/12/21
 */
public class DeathRatItem extends Item {

    private int killCount = 0; //the amount of rats it has killed.
    private DeathRat deathRat; //the deathrat object.
    Runnable makeRat = () -> spawnDeathRat(); //Assigns the method to be a "runnable", used to execute a wait function.


    /**
     * Constructs an Item of type Death Rat. NOT the same as DeathRat of type Rat.
     * Immediately calls wait function on instantiation.
     *
     * @param x
     * @param y
     */
    public DeathRatItem(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("DeathRat.png"));
        this.setMyItemType(itemType.DeathRat); //set the type
        waitToSpawnRat();
    }

    /**
     * Checks the kill count of the deathRat and marks it for deletion if its task is completed.
     */
    public void checkKillCount() {
        if (killCount >= 5) {
            deathRat.deleteRat();

            deleteItem();
        }
    }


    /**
     * Used to increment the kill counter from outside this class. I.e. from rat.
     */
    public void incrementKills() {
        killCount++;
        checkKillCount();
    }

    /**
     * Waits to spawn a rat. Default time is 2 seconds. This is so rats have a chance to move.
     */
    private void waitToSpawnRat() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(makeRat, 2, TimeUnit.SECONDS);

    }

    /**
     * Spawns death rat of type rat by using the deathRat constructor. Assigns it to the variable
     * deathRat and assigns this instance of deathRatItem inside the deathRat's class so they can
     * communicate.
     */
    private void spawnDeathRat() {
        this.deathRat = new DeathRat('m', true, true, true, getX(), getY(), 3);
        this.deathRat.setItem(this); // give this class to the death rat, so it can reference it.
        this.setImage(new Image("DeathRat.png")); //make it invisible.


    }

    /**
     * Update the position of this item as the deathRat moves around the board.
     * This is called by deathRat of type rat, so it will only be called when deathRat != null
     * as it relies on the location.
     */
    public void updatePos() {
        int prevX = getX(); //Get the position are at now
        int prevY = getY();
        this.setX(deathRat.getX()); //Move to where the death rat is so that other rats can collide with us.
        this.setY(deathRat.getY());
        getLocalInstance().getLevelBoard().redrawTile(prevX, prevY, false); //redraw the tile we came from.

    }
}
