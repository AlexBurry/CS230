package ItemClasses;

import Game.Tile;
import Sprites.ImageRefs;
import java.util.ArrayList;

/**
 * Smaller node of gasItem, produces 2 duplicate nodes
 * to give it more of a natural look.
 * @author Trafford
 * @version 1.0
 * @since 04/12/2021
 */
public class GasChild extends GasItem{

    private ArrayList<GasChild> associatedGas = new ArrayList<>();


    /**
     * Creates a new gas child. If this node can grow more, it should be told to do so.
     * @param x
     * @param y
     * @param growFurther should this node spawn up to 4 more nodes?
     */
    public GasChild(int x, int y, boolean growFurther) {

        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.gasOuter);
        this.setMyItemType(itemType.Gas);

        if(growFurther){
            tryGrow();
        }


    }

    private boolean checkIfSafe(int x, int y){
        return x >= 0 && y >= 0 && x <= getLocalInstance().getLevelBoard().getGameWidthInTiles()
                && y <= getLocalInstance().getLevelBoard().getGameHeightInTiles();
    }

    /**
     * Try to grow once in all directions.
     * Works by checking each value is in range,
     */
    public void tryGrow(){
        Coordinate left = new Coordinate(getX() - 1,getY());
        Coordinate right = new Coordinate(getX() + 1,getY());
        Coordinate up = new Coordinate(getX(),getY() - 1);
        Coordinate down = new Coordinate(getX(),getY() + 1);

        Tile[][] allTiles = getLocalInstance().getLevelBoard().getTileMap();


        if(checkIfSafe(left.x, left.y) && allTiles[left.x][left.y].getTraversable()){

            if(!getLocalInstance().getLevelBoard().existsItemAt(left.x,left.y)){
                GasChild child = new GasChild(left.x,left.y,false);
                associatedGas.add(child);
            }
        }
        if(checkIfSafe(right.x, right.y) && allTiles[right.x][right.y].getTraversable()){
            if(!getLocalInstance().getLevelBoard().existsItemAt(left.x,left.y)){
                GasChild child = new GasChild(left.x,left.y,false);
                associatedGas.add(child);
            }
        }
        if(checkIfSafe(up.x, up.y) && allTiles[up.x][up.y].getTraversable()){
            if(!getLocalInstance().getLevelBoard().existsItemAt(up.x,up.y)){
                GasChild child = new GasChild(up.x,up.y,false);
                associatedGas.add(child);
            }
        }
        if(checkIfSafe(down.x, down.y) && allTiles[down.x][down.y].getTraversable()){
            if(!getLocalInstance().getLevelBoard().existsItemAt(down.x,down.y)){
                GasChild child = new GasChild(down.x,down.y,false);
                associatedGas.add(child);
            }
        }
    }

    public void removeSelf(){
        for (Item it:associatedGas) {
            getLocalInstance().getLevelBoard().removeItem(it);
        }

        getLocalInstance().getLevelBoard().removeItem(this);

    }
}
