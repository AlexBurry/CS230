package itemClasses;

import game.ITickHandler;
import game.Tile;
import ratClasses.Rat;
import sprites.ImageRefs;

import java.util.ArrayList;

/**
 * Steralize rats, removing their ability to reproduce, permanently.
 *
 * @author Jack
 * @version 1.0
 * @see Item
 */
public class SteriliseItem extends Item implements ITickHandler {
    private int timer = 4;
    private ArrayList<Tile> sterilizeZone;

    /**
     * Creates a new steriliseItem and finds the tiles it affects
     *
     * @param x the XCoordinate of the item
     * @param y the YCoordinate of the item
     */
    public SteriliseItem(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.steriliseImage);
        this.setMyItemType(itemType.Sterilise);
        this.sterilizeZone = getSterilizeZone();
    }

    /**
     * This class is a listener of the ITickHandler event.
     * It will count down every tick (default: 1s)
     */
    @Override
    public void tickEvent(int count) {
        if (count >= 4) {
            countdown();
        }
    }

    /**
     * Finds and sterilizes any rats in the area each second, and deletes the item after 4 seconds has passed
     */
    private void countdown() {
        if (timer <= 0) {
            //Stop listening so that we dont call Tick on a dead object
            getLocalInstance().markListenerForRemoval(this);
            deleteItem();
        } else {
            timer -= 1;
            sterilize();
        }
    }

    /**
     * Finds the area for rats to be sterilized
     * by looping though the square from 2 left and 2 up, to 2 right and 2 below.
     *
     * @return sterilizeZone : a list of tiles where rats need to be sterilized.
     */
    private ArrayList<Tile> getSterilizeZone() {
        ArrayList<Tile> sterilizeZone = new ArrayList<>();
        Tile[][] localMap = getLocalInstance().getLevelBoard().getTileMap();

        //store the current pos as the items position.
        int currentXPos = getX();
        int currentYPos = getY();

        //set the radius of which rats will be sterilised
        final int radius = 2;

        //loop though all tiles within radius spaces and save them to array
        for (int x = (radius * -1); x <= radius; x++) {
            for (int y = (radius * -1); y <= radius; y++) {
                if ((currentXPos + x) >= 0 && (currentXPos + x) <= 19
                        && (currentYPos + y) >= 0 && (currentYPos + y <= 12)) {
                    sterilizeZone.add(localMap[currentXPos + x][currentYPos + y]);
                }
            }
        }
        return sterilizeZone;
    }

    /**
     * Checks all rats to see if any are in the area
     * Sterilizes all rats in the area
     */
    private void sterilize() {
        ArrayList<Rat> rats = getLocalInstance().getLevelBoard().getRats();
        for (Tile tile : sterilizeZone) {
            for (Rat rat : rats) { //for each rat on the entire board
                if (rat.getX() == tile.getLocation()[0] && rat.getY() == tile.getLocation()[1]) {
                    //if the rat is on this tile
                    rat.sterilizeRat();
                }
            }
        }
    }
}
