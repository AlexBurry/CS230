package itemClasses;

import game.Tile;
import ratClasses.Rat;
import sprites.ImageRefs;

import java.util.ArrayList;

/**
 * Smaller node of gasItem, produces 2 duplicate nodes
 * to give it more of a natural look.
 *
 * @author Trafford
 * @version 1.0
 */
public class GasChild extends GasItem {

    private final ArrayList<GasChild> associatedGas = new ArrayList<>();
    private Rat.Directions expansionDirection;
    private GasItem parent;

    /**
     * Creates a new gas child. If this node can grow more, it should be told to do so.
     *
     * @param x
     * @param y
     * @param growFurther should this node spawn up to 4 more nodes?
     */
    public GasChild(int x, int y, boolean growFurther) {

        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.gasOuter);
        this.setMyItemType(itemType.Gas);

        if (growFurther) {
            tryGrow();
        } else {
            setImage(ImageRefs.gasOuterChild);
        }


    }

    public void setParent(GasItem center) {
        this.parent = center;
        setRelativePosToCenter();
    }

    /**
     * Calculate the relative position (direction) to the center node.
     */
    private void setRelativePosToCenter() {
        int x = parent.getX();
        int y = parent.getY();

        if (x < getX()) {
            expansionDirection = Rat.Directions.EAST;
        } else if (x > getX()) {
            expansionDirection = Rat.Directions.WEST;
        } else if (y < getY()) {
            expansionDirection = Rat.Directions.NORTH;
        } else if (y > getY()) {
            expansionDirection = Rat.Directions.SOUTH;
        }
    }


    /**
     * Try to grow once in all directions.
     * Works by checking each value is in range,
     */
    public void tryGrow() {
        Coordinate left = new Coordinate(getX() - 1, getY());
        Coordinate right = new Coordinate(getX() + 1, getY());
        Coordinate up = new Coordinate(getX(), getY() - 1);
        Coordinate down = new Coordinate(getX(), getY() + 1);

        Tile[][] allTiles = getLocalInstance().getLevelBoard().getTileMap();


        if (expansionDirection != Rat.Directions.EAST && allTiles[left.getX()][left.getY()].getTraversable()) {

            if (!getLocalInstance().getLevelBoard().existsItemAt(left.getX(), left.getY())) {
                GasChild child = new GasChild(left.getX(), left.getY(), false);
                child.setParent(this);
                associatedGas.add(child);
            }
        }
        if (expansionDirection != Rat.Directions.WEST && allTiles[right.getX()][right.getY()].getTraversable()) {

            if (!getLocalInstance().getLevelBoard().existsItemAt(left.getX(), left.getY())) {
                GasChild child = new GasChild(left.getX(), left.getY(), false);
                child.setParent(this);
                associatedGas.add(child);
            }
        }
        if (expansionDirection != Rat.Directions.NORTH && allTiles[up.getX()][up.getY()].getTraversable()) {

            if (!getLocalInstance().getLevelBoard().existsItemAt(up.getX(), up.getY())) {
                GasChild child = new GasChild(up.getX(), up.getY(), false);
                child.setParent(this);
                associatedGas.add(child);
            }
        }
        if (expansionDirection != Rat.Directions.SOUTH && allTiles[down.getX()][down.getY()].getTraversable()) {

            if (!getLocalInstance().getLevelBoard().existsItemAt(down.getX(), down.getY())) {
                GasChild child = new GasChild(down.getX(), down.getY(), false);
                child.setParent(this);
                associatedGas.add(child);
            }
        }
    }


    /**
     * remove this gas from the map
     */
    public void removeSelf() {
        for (Item it : associatedGas) {
            getLocalInstance().getLevelBoard().removeItem(it);
        }

        getLocalInstance().getLevelBoard().removeItem(this);

    }
}
