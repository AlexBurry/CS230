/**
 * Represents the board.
 * @author Alex
 * @version 0.1
 * @since 0.1
 */
public class Board {
    private Tile[][] tileMap;
    private static Item[][] itemMap;
    private Rat[][] ratMap;

    /**
     * Constructor function for board
     * @param tiles tile map in 2D array
     * @param items item map in 2D array
     * @param rats male rat map in 2D array
     */
    public Board(Tile[][] tiles, Item[][] items, Rat[][] rats) {
        tileMap = tiles;
        itemMap = items;
        ratMap =  rats;
    }

    /**
     * Function to add an item to the itemMap
     * @param x x coordinate of the item
     * @param y y coordinate of the item
     * @param item the item object
     */
    public static void addItemToMap(int x, int y, Item item) {
        itemMap[x][y] = item;
    }

    /**
     * Function to add a male rat to the mRatMap
     * @param x x coordinate of the rat
     * @param y y coordinate of the rat
     * @param rat the rat object
     */
    public void addMRatToMap(int x, int y, Rat rat) {
        ratMap[x][y] = rat;
    }

    public void removeItemFromMap() {

    }

    public void removeMRatFromMap() {

    }

    public void removeFRatFromMap() {

    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public Item[][] getItemMap() {
        return itemMap;
    }

    public Rat[][] getmRatMap() {
        return ratMap;
    }
}
