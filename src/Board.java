/**
 * Represents the board.
 * @author Alex
 * @version 0.1
 * @since 0.1
 */
public class Board {
    private Tile[][] tileMap;
    private Item[][] itemMap;
    private Rat[][] mRatMap;
    private Rat[][] fRatMap;

    /**
     * Constructor function for board
     * @param tiles tile map in 2D array
     * @param items item map in 2D array
     * @param mRats male rat map in 2D array
     * @param fRats female rat map in 2D array
     */
    public Board(Tile[][] tiles, Item[][] items, Rat[][] mRats, Rat[][] fRats) {
        tileMap = tiles;
        itemMap = items;
        mRatMap = mRats;
        fRatMap = fRats;
    }

    /**
     * Function to add an item to the itemMap
     * @param x x coordinate of the item
     * @param y y coordinate of the item
     * @param item the item object
     */
    public void addItemToMap(int x, int y, Item item) {
        itemMap[x][y] = item;
    }

    /**
     * Function to add a male rat to the mRatMap
     * @param x x coordinate of the rat
     * @param y y coordinate of the rat
     * @param rat the rat object
     */
    public void addMRatToMap(int x, int y, Rat rat) {
        mRatMap[x][y] = rat;
    }

    /**
     * Function to add a female rat to the fRatMap
     * @param x x coordinate of the rat
     * @param y y coordinate of the rat
     * @param rat the rat object
     */
    public void addFRatToMap(int x, int y, Rat rat) {
        fRatMap[x][y] = rat;
    }

    public void removeItemFromMap() {

    }

    public void removeMRatFromMap() {

    }

    public void removeFRatFromMap() {

    }
}
