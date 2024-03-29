package itemClasses;

import game.ITickHandler;
import game.Tile;
import sprites.ImageRefs;

import java.util.ArrayList;

/**
 * The main gas node of a gas item. Controls gas dissipation
 *
 * @author Trafford
 * @version 1
 */
public class GasItem extends Item implements ITickHandler {

    //all tiles in each direction, allows us to create them in steps each second.
    private final ArrayList<Coordinate> northernTiles = new ArrayList<>();
    private final ArrayList<Coordinate> southernTiles = new ArrayList<>();
    private final ArrayList<Coordinate> westernTiles = new ArrayList<>();
    private final ArrayList<Coordinate> easternTiles = new ArrayList<>();
    private final ArrayList<GasChild> myChildren = new ArrayList<>();


    private boolean dissipating = false; //Should we reverse?

    private int expansionStage = 0;


    /**
     * Blank template, used for GasChild to call without calculating the AOE.
     */
    public GasItem() {

    }

    /**
     * Creates a new gas node which will produce children that replicate themselves.
     *
     * @param x The x Coordinate
     * @param y The y Coordinate
     */
    public GasItem(int x, int y) {
        super();
        this.setX(x);
        this.setY(y);
        this.setImage(ImageRefs.gasCenter);
        this.setMyItemType(itemType.Gas);

        calculateAOE();
        getLocalInstance().addListener(this);
    }


    /**
     * Calculates the area of effect that the gas node has.
     * This is achieved by checking 3 tiles in every direction.
     */
    private void calculateAOE() {

        //System.out.println(getLocalInstance().getLevelBoard().getTileMap() == null);
        final int DIRECTION_LIMIT = 3;
        Tile[][] localMap = getLocalInstance().getLevelBoard().getTileMap();
        //store the current pos as the items position.
        int currentXPos = getX();
        int currentYPos = getY();

        for (int i = 0; i < DIRECTION_LIMIT; i++) {
            currentYPos -= 1; //check north first
            if (localMap[currentXPos][currentYPos].getTraversable()) {
                northernTiles.add(new Coordinate(currentXPos, currentYPos));
            } else {
                i = DIRECTION_LIMIT; //stop looking.
            }


        }
        currentYPos = getY(); //reset to the center

        for (int i = 0; i < DIRECTION_LIMIT; i++) {
            currentYPos += 1; //check south now
            if (localMap[currentXPos][currentYPos].getTraversable()) {
                southernTiles.add(new Coordinate(currentXPos, currentYPos));
            } else {
                i = DIRECTION_LIMIT; //stop looking.
            }


        }
        currentYPos = getY(); //reset to the center

        for (int i = 0; i < DIRECTION_LIMIT; i++) {
            currentXPos -= 1; //check west next
            if (localMap[currentXPos][currentYPos].getTraversable()) {
                westernTiles.add(new Coordinate(currentXPos, currentYPos));
            } else {
                i = DIRECTION_LIMIT; //stop looking.
            }


        }
        currentXPos = getX(); //reset to the center
        for (int i = 0; i < DIRECTION_LIMIT; i++) {
            currentXPos += 1; //check east last
            if (localMap[currentXPos][currentYPos].getTraversable()) {
                easternTiles.add(new Coordinate(currentXPos, currentYPos));
            } else {
                i = DIRECTION_LIMIT; //stop looking.
            }


        }

    }

    /**
     * Creates a new gas child.
     * We keep the central node, so we know where to come back to.
     */
    private void replicateAt(int x, int y) {
        if (x >= 0 && y >= 0) {
            GasChild child = new GasChild(x, y, true);
            child.setParent(this);
            myChildren.add(child);
        }
    }

    /**
     * Removes existing gas
     * We keep the central node, so we know where to come back to.
     */
    private void removeAt(int xPos, int yPos) {
        GasChild child = myChildren.stream().filter(x -> x.getY() == yPos && x.getX() == xPos).findFirst().orElse(null);
        if (child != null) {
            myChildren.remove(child);
            child.removeSelf();
        }
    }


    /**
     * Handles the expansion of the gas per tick.
     * if there are tiles to expand on, get their coordinates.
     */
    private void handleGrowthTick() {

        if (expansionStage <= 3) {
            //if a tile exists there, spawn gas on it.
            if (northernTiles.size() > expansionStage) {
                if (northernTiles.get(expansionStage) != null) {
                    replicateAt(northernTiles.get(expansionStage).getX(), northernTiles.get(expansionStage).getY());
                    System.out.println("Replicating at: " + northernTiles.get(expansionStage).getX() + "," + northernTiles.get(expansionStage).getY());
                }
            }
            if (easternTiles.size() > expansionStage) {
                if (easternTiles.get(expansionStage) != null) {
                    replicateAt(easternTiles.get(expansionStage).getX(), easternTiles.get(expansionStage).getY());
                }
            }
            if (westernTiles.size() > expansionStage) {
                if (westernTiles.get(expansionStage) != null) {
                    replicateAt(westernTiles.get(expansionStage).getX(), westernTiles.get(expansionStage).getY());
                }
            }
            if (southernTiles.size() > expansionStage) {
                if (southernTiles.get(expansionStage) != null) {
                    replicateAt(southernTiles.get(expansionStage).getX(), southernTiles.get(expansionStage).getY());
                }
            }

            expansionStage++;

        } else {
            //we are done,
            dissipating = true;
        }

    }

    /**
     * Handles the disappearance of gas.
     */
    private void handleDissipationTick() {
        if (expansionStage >= 0) {
            //if a tile exists there, spawn gas on it.
            if (northernTiles.size() > expansionStage) {
                if (northernTiles.get(expansionStage) != null) {
                    removeAt(northernTiles.get(expansionStage).getX(), northernTiles.get(expansionStage).getY());
                }
            }
            if (easternTiles.size() > expansionStage) {
                if (easternTiles.get(expansionStage) != null) {
                    removeAt(easternTiles.get(expansionStage).getX(), easternTiles.get(expansionStage).getY());
                }
            }
            if (westernTiles.size() > expansionStage) {
                if (westernTiles.get(expansionStage) != null) {
                    removeAt(westernTiles.get(expansionStage).getX(), westernTiles.get(expansionStage).getY());
                }
            }
            if (southernTiles.size() > expansionStage) {
                if (southernTiles.get(expansionStage) != null) {
                    removeAt(southernTiles.get(expansionStage).getX(), southernTiles.get(expansionStage).getY());
                }
            }
            expansionStage--;

        } else {
            //we are done,
            getLocalInstance().markListenerForRemoval(this);
            deleteItem();
        }
    }

    /**
     * For this class, it handles growth and dissipations.
     *
     * @param count how many ticks have passed? Resets every second.
     */
    @Override
    public void tickEvent(int count) {

        if (count >= 4) {
            if (!dissipating) {
                handleGrowthTick();
            } else {
                handleDissipationTick();
            }
        }


    }
}


