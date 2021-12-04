package ItemClasses;

import Game.ITickHandler;
import Game.Tile;
import RatClasses.Rat;
import Sprites.ImageRefs;
import javafx.scene.image.Image;

import java.util.ArrayList;

//TODO: FINISH THIS CLASS
/**
 * Steralize rats, removing their ability to reproduce, permanently.
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class SteriliseItem extends Item implements ITickHandler {
    private int timer = 4;
    private ArrayList<Tile> sterilizeZone;

    public SteriliseItem(int x, int y){
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.steriliseImage);
        this.setMyItemType(itemType.Sterilise);
        this.sterilizeZone = getSterilizeZone();
    }

    @Override
    public void tickEvent(int count) {
        if(count >= 4){
            countdown();
            System.out.println("timer test");
        }

    }

    private void countdown() {
System.out.println("Sterilize timer = " + timer);
        if (timer <= 0) {
            getLocalInstance().markListenerForRemoval(this); //Stop listening so that we dont call Tick on a dead object
            deleteItem();

        } else {
            timer -= 1;
            sterilize();
        }

    }

    private ArrayList<Tile> getSterilizeZone() {
        ArrayList<Tile> allTiles = getLocalInstance().getLevelBoard().getTraversableTiles();
        ArrayList<Tile> sterilizeZone = new ArrayList<>();
        Tile[][] localMap = getLocalInstance().getLevelBoard().getTileMap();

        //store the current pos as the items position.
        int currentXPos = getX();
        int currentYPos = getY();
        for (int x = -2; x <= 2; x++) {
           for (int y = -2; y <= 2; y++){
                sterilizeZone.add( localMap[currentXPos+x][currentYPos+y]);
                System.out.println(localMap[currentXPos+x][currentYPos+y].getLocation());
            }
       }
        return sterilizeZone;
    }

    private void sterilize() {
        ArrayList<Rat> rats = getLocalInstance().getLevelBoard().getRats();
        ArrayList<Rat> toSterilize = new ArrayList<>();
        sterilizeZone = getSterilizeZone();
        for (Tile tile : sterilizeZone) {
            //TODO: Optimise and add VFX
            for (Rat rat : rats) { //for each rat on the entire board
                if (rat.getX() == tile.getLocation()[0] && rat.getY() == tile.getLocation()[1]) {
                    //if the rat is on this tile
                    toSterilize.add(rat); //mark it to be sterilized. Cannot do this INSIDE this for loop, or it will break.
                }
            }
        }

        //Without this, we are changing the collection as we iterate on it, causing an exception.
        for (Rat sterilizeRat : toSterilize) {
            sterilizeRat.sterilizeRat();
        }

    }
}
