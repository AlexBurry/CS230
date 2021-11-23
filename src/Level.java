public class Level {
    private float timeRemaining;
    private int currentScore;
    private int allowedTime;
    private int bombSpawnRate;
    private int mSexChangeSpawnRate;
    private int fSexChangeSpawnRate;
    private int gasSpawnRate;
    private int poisonSpawnRate;
    private int sterilisationSpawnRate;
    private int noEntrySpawnRate;
    private int deathRatSpawnRate;
    private int numberOfBombs;
    private int numberOfMSexChange;
    private int numberOfFSexChange;
    private int numberOfGas;
    private int numberOfSterilisation;
    private int numberOfPoison;
    private int numberOfNoEntry;
    private final int MAX_ITEM_NUMBER = 4;
    private Board levelBoard;

    public Level (Tile[][] tiles, Item[][] items, Rat[][] mRats, Rat[][] fRats, int allowedTime, int timeRemaining,
                  int currentScore) {
        levelBoard = new Board(tiles, items, mRats, fRats);
    }

//    public Board constructBoard(Tile[][] tiles, Item[][] items, Rat[][] mRats, Rat[][] fRats) {
//        return new Board(tiles, items, mRats, fRats);
//    }

//    public void checkCollisions() {
//        Rat[][] mRatMap = levelBoard.getmRatMap();
//        Rat[][] fRatMap = levelBoard.getfRatMap();
//        Item[][] itemMap = levelBoard.getItemMap();
//
//    }


}
