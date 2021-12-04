package ItemClasses;

import Game.Tile;
import Sprites.ImageRefs;

import java.util.ArrayList;

/**
 * Smaller node of gasItem, reproduces on condition.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class GasChild extends GasItem{
    private GasItem centralNode;
    private ArrayList<GasChild> associatedGas = new ArrayList<>();


    public GasChild(int x, int y, boolean growFurther) {

        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.gasOuter);
        this.setMyItemType(itemType.Gas);

        if(growFurther){
            tryGrow();
        }


    }

    public void setCentralNode(GasItem parent){
        this.centralNode = parent;
    }


    /**
     * Try to grow once in all directions.
     */
    public void tryGrow(){
        Coordinate left = new Coordinate(getX() - 1,getY());
        Coordinate right = new Coordinate(getX() + 1,getY());
        Coordinate up = new Coordinate(getX(),getY() - 1);
        Coordinate down = new Coordinate(getX(),getY() + 1);

        Tile[][] allTiles = getLocalInstance().getLevelBoard().getTileMap();


        if(left.x >= 0 && left.y >= 0 && allTiles[left.x][left.y].getTraversable()){
            System.out.println(allTiles[left.x][left.y].getTraversable());
            if(!getLocalInstance().getLevelBoard().existsItemAt(left.x,left.y)){
                GasChild child = new GasChild(left.x,left.y,false);
                associatedGas.add(child);
            }
        }
        if(right.x >= 0 && right.y >= 0 && allTiles[right.x][right.y].getTraversable()){
            if(!getLocalInstance().getLevelBoard().existsItemAt(left.x,left.y)){
                GasChild child = new GasChild(left.x,left.y,false);
                associatedGas.add(child);
            }
        }
        if(up.x >= 0 && up.y >= 0 && allTiles[up.x][up.y].getTraversable()){
            if(!getLocalInstance().getLevelBoard().existsItemAt(up.x,up.y)){
                GasChild child = new GasChild(up.x,up.y,false);
                associatedGas.add(child);
            }
        }
        if(down.x >= 0 && down.y >= 0 && allTiles[down.x][down.y].getTraversable()){
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
