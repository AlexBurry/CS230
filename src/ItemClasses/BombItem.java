package ItemClasses;

import Game.ITickHandler;
import Game.Tile;
import RatClasses.Rat;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * A bomb which explodes across several tiles in 4 directions.
 *
 * @author Trafford
 * @version 1.0
 * @see Item
 * @since 01/12/21
 */
public class BombItem extends Item implements ITickHandler {

    private int timer = 4; //The countdown period
    private ArrayList<Tile> bombZone; //The tiles to detonate on

    /**
     * The direction type, allowing us to switch through them.
     */
    private enum checkDir {
        North,
        South,
        West,
        East
    }

    private checkDir directionToCheck; //our local variable which holds our current direction.

    /**
     * Creates a new bomb and calculates the zone in which it will explode.
     *
     * @param x the XCoordinate
     * @param y the YCoordinate
     */
    public BombItem(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(new Image("Sprites/Bomb4.png")); //Defaults to the highest number, bomb-4seconds
        this.setMyItemType(itemType.Bomb); //Sets the item type to a Bomb.
        this.directionToCheck = checkDir.North; //Default to north as our first direction to check.
        this.bombZone = getBombZone();

    }

    /**
     * This class is a listener of the ITickHandler event.
     * It will count down every tick (default: 1s)
     */
    @Override
    public void tickEvent() {

        countdown();
    }


    /**
     * Explodes the bomb after 5 seconds has passed
     */
    private void countdown() {

        if (timer <= 0) {
            detonate();

        } else {
            timer -= 1;
        }

    }


    /**
     * Figures out the bomb zone by iterating through each direction and continuing until it hits a wall. (grass tile)
     *
     * @return tilesChecked : the list of tiles that need to be detonated on.
     */
    private ArrayList<Tile> getBombZone() {
        ArrayList<Tile> allTiles = getLocalInstance().getLevelBoard().getTraversableTiles();
        ArrayList<Tile> tilesChecked = new ArrayList<>();
        Tile[][] localMap = getLocalInstance().getLevelBoard().getTileMap();


        //check if we've found all the tiles in every direction.
        boolean foundAllTiles = false;

        //store the current pos as the items position.
        int currentXPos = getX();
        int currentYPos = getY();

        //First check the tile we are on.
        Tile firstTile = localMap[currentXPos][currentYPos];
        if (allTiles.contains(firstTile)) {
            tilesChecked.add(firstTile);
        }

        while (!foundAllTiles) { //while we haven't checked all directions
            switch (directionToCheck) { //adds or removes 1 on each index to move the "check"
                case North -> currentYPos -= 1;
                case South -> currentYPos += 1;
                case West -> currentXPos -= 1;
                case East -> currentXPos += 1;
            }

            Tile tempTile = localMap[currentXPos][currentYPos];
            if (allTiles.contains(tempTile)) { //if its traversable (being in allTiles means its traversable)
                tilesChecked.add(tempTile);
            } else {
                //If it is null, or not traversable, then we should check a different direction from
                //the starting point.
                switch (directionToCheck) {
                    case North -> directionToCheck = checkDir.East;
                    case South -> directionToCheck = checkDir.West;
                    case West -> foundAllTiles = true;
                    case East -> directionToCheck = checkDir.South;
                }
                //reset starting point, so we check in relative directions from the center.
                currentXPos = getX();
                currentYPos = getY();
            }

        }


        return tilesChecked;

    }

    /**
     * Destroy everything on the tiles in tilesChecked by iterating over all rats
     * and checking if they are on a tile in our list.
     * The bomb is instantaneous.
     */
    private void detonate() {
        ArrayList<Rat> rats = getLocalInstance().getLevelBoard().getRats();
        ArrayList<Rat> toKill = new ArrayList<>();

        for (Tile tile : bombZone) {
            //TODO: Optimise and add VFX
            for (Rat rat : rats) { //for each rat on the entire board
                if (rat.getX() == tile.getLocation()[0] && rat.getY() == tile.getLocation()[1]) {
                    //if the rat is on this tile
                    toKill.add(rat); //mark it to be killed. Cannot do this INSIDE this for loop, or it will break.
                }
            }
        }

        //Without this, we are changing the collection as we iterate on it, causing an exception.
        for (Rat killRat : toKill) {
            killRat.deleteRat();
        }

        getLocalInstance().markListenerForRemoval(this); //Stop listening so that we dont call Tick on a dead object
        deleteItem();

    }


}
