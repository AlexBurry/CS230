import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeathRatItem extends Item{

    private int killCount = 0; //the amount of rats it has killed.
    private DeathRat deathRat; //the deathrat object.
    Runnable makeRat = () -> spawnDeathRat();

    public void checkKillCount(){
        if(killCount >= 5){
            deleteItem();
        }
    }


    //waits to spawn a deathRat after 2 seconds.
    private void waitToSpawnRat(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(makeRat, 2, TimeUnit.SECONDS);
    }

    //spawns a deathRat with the same coordinates.
    private void spawnDeathRat(){
        deathRat = new DeathRat('m',false,true,true,true);
    }

}
