import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * A bomb which explodes across several tiles.
 * @author Trafford
 * @version 0.1
 * @since 0.1
 */
public class BombItem extends Item implements Tick{

    private  int timer = 4;
    private ArrayList<Tile> bombZone;

    @Override
    public void tickEvent() {

        countdown();
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

        bombZone = getBombZone();

    }

    //After a 4-second countdown, explode the bomb!
    private void countdown(){

       if(timer <= 0){
           detonate();

       }
       else{
           timer -= 1;
       }

    }



    private ArrayList<Tile> getBombZone(){
        ArrayList<Tile> allTiles = instance.getLevelBoard().getTraversableTiles();
        ArrayList<Tile> tilesChecked = new ArrayList<>();
        Tile[][] localMap =  instance.getLevelBoard().getTileMap();


        //check if we've found all the tiles in every direction.

        boolean foundAllTiles = false;

        //store the current pos as the items position.
        int currentXPos = xPos;
        int currentYPos = yPos;

        //First check the tile we are on.
        Tile firstTile = localMap[currentXPos][currentYPos];
        if(allTiles.contains(firstTile)){
            tilesChecked.add(firstTile);
        }

        while(!foundAllTiles){ //while we haven't checked all directions
            switch (directionToCheck){
                case North -> currentYPos -= 1;
                case South -> currentYPos += 1;
                case West -> currentXPos -= 1;
                case East -> currentXPos += 1;
            }

            Tile tempTile = localMap[currentXPos][currentYPos];
            if(allTiles.contains(tempTile)){
                tilesChecked.add(tempTile);
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


        return tilesChecked;

    }

    private void detonate(){
        ArrayList<Rat> rats = instance.getLevelBoard().getRats();
        ArrayList<Rat> toKill = new ArrayList<>();

        for (Tile tile:bombZone) {
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

        instance.markListenerForRemoval(this);
        deleteItem();

    }



}
