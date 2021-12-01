import javafx.scene.image.Image;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Trafford
 * Represents the Item behind a death rat. Follows it around the board.
 */
public class DeathRatItem extends Item implements Tick{

    private int killCount = 0; //the amount of rats it has killed.
    private DeathRat deathRat; //the deathrat object.
    Runnable makeRat = () -> spawnDeathRat();



    public DeathRatItem (int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("DeathRat.png"));
        this.setMyItemType(itemType.DeathRat);
        waitToSpawnRat();
    }

    //checks the killcount and deletes the ratItem if necessary.
    public void checkKillCount(){
        if(killCount >= 5){
            deathRat.deleteRat();
            getLocalInstance().markListenerForRemoval(this);
            deleteItem();
        }
    }


    public void incrementKills(){
        killCount++;
        checkKillCount();
    }

    //waits to spawn a deathRat after 2 seconds.
    private void waitToSpawnRat(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(makeRat, 2, TimeUnit.SECONDS);

    }

    //spawns a deathRat with the same coordinates.
    private void spawnDeathRat(){
        this.deathRat = new DeathRat('m',true,true,true, getX(), getY(), 3);
        this.deathRat.setItem(this); // give this class to the death rat, so it can reference it.
        this.setImage(new Image("DeathRat.png")); //make it invisible.
        getLocalInstance().addListener(this);

    }

    //on tick update
    @Override
    public void tickEvent() {
        getLocalInstance().getLevelBoard().redrawTile(getX(),getY(),false);
        this.setX(deathRat.getX());
        this.setY(deathRat.getY());

    }
}
