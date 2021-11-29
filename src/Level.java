import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.application.Application.launch;

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
    private static Level instance;
    private Canvas canvas;
    private Timeline tickTimeline;
    private final int TICKRATE = 1000;

    public Level (int mapX, int mapY, String[][] tiles, Stage primaryStage) {
        levelBoard = new Board(tiles, mapX, mapY);
        levelBoard.start(primaryStage);
        levelBoard.drawBoard();
        //game tick system
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(TICKRATE), event -> tick()));
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }

    public void tick() {
        System.out.println("tick");
        levelBoard.drawBoard();
    }

    public Board getLevelBoard(){
        return levelBoard;
    }

    public static Level getInstance(){
        return instance;
    }

//    public Board constructBoard(Tile[][] tiles, Item[][] items, Rat[][] mRats, Rat[][] fRats) {
//        return new Board(tiles, items, mRats, fRats);
//    }
}
