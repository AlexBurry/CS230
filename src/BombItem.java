import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * A bomb which explodes across several tiles.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class BombItem extends Item implements Tick{

    private  int TIMER = 4;

    @Override
    public void tickEvent() {
        TIMER -= 1;
        System.out.println("listening");
    }

    private enum checkDir{
        North,
        South,
        West,
        East
    }

    private checkDir directionToCheck;

    public BombItem (int x, int y){
        super();
        this.xPos = x;
        this.yPos = y;
        this.sprite = new Image("Bomb4.png");
        directionToCheck = checkDir.North;

       // countdown();

    }

    //After a 4-second countdown, explode the bomb!
    private void countdown(){
        int currentTime = instance.getTimeLeft() - 1; //Get the time left in the level

        int explodeTime = currentTime - TIMER; //Get the time 4 seconds from now

        while(currentTime >= explodeTime){

            currentTime = instance.getTimeLeft(); //set the current time to the time left
            //TODO: Change sprite every second
        }

        detonate(getBombZone());
    }

    private ArrayList<Tile> getBombZone(){
        ArrayList<Tile> allTiles = new ArrayList<>();
        Tile[][] localMap =  instance.getLevelBoard().getTileMap();


        //check if we've found all the tiles in every direction.

        boolean foundAllTiles = false;

        //store the current pos as the items position.
        int currentXPos = xPos;
        int currentYPos = yPos;

        //First check the tile we are on.
        Tile firstTile = localMap[currentXPos][currentYPos];
        if(firstTile != null && firstTile.getTraversable()){ //if the tile exists here, and is traversable:
            allTiles.add(firstTile); //add it to our list.
        }

        while(!foundAllTiles){ //while we havent checked all directions
            switch (directionToCheck){
                case North -> currentYPos -= 1;
                case South -> currentYPos += 1;
                case West -> currentXPos -= 1;
                case East -> currentXPos += 1;
            }

            Tile tempTile = localMap[currentXPos][currentYPos];
            if(tempTile != null && tempTile.getTraversable()){ //if the tile exists here, and is traversable:
                allTiles.add(tempTile); //add it to our list.
            }
            else{
                //If it is null, or not traversable, then we should check a different direction from
                //the starting point.
                switch (directionToCheck){
                    case North -> directionToCheck = checkDir.East;
                    case South -> directionToCheck = checkDir.West;
                    case West -> foundAllTiles = true;
                    case East -> directionToCheck = checkDir.South;
                }
                //reset starting point so we check in relative directions from the center.
                currentXPos = xPos;
                currentYPos = yPos;
            }

        }


        return allTiles;

    }

    private void detonate(ArrayList<Tile> tilesToDetonateOn){
        ArrayList<Rat> rats = instance.getLevelBoard().getRats();
        ArrayList<Rat> toKill = new ArrayList<>();
        System.out.println(rats.get(0));
        for (Tile tile:tilesToDetonateOn) {
            //TODO: Optimise and add VFX
            for (Rat rat:rats) { //for each rat on the entire board
                if(rat.getX() == tile.getLocation()[0] && rat.getY() == tile.getLocation()[1]){
                    //if the rat is on this tile
                    toKill.add(rat); //TODO: make sure this adds points as well.
                }
            }
        }

        //Without this, we are changing the collection as we iterate on it.
        for (Rat killRat:toKill) {
            killRat.deleteRat();
        }

    }



}
