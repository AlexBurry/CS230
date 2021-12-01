import java.util.Random;

public class DeathRat extends Rat{

    private DeathRatItem item;

    public DeathRat(char sex, boolean isDeath, boolean alive, boolean isSterile, int xPos, int yPos, int speed) {
        super(sex, isDeath, alive, isSterile, xPos, yPos, speed);
        this.setImage("DeathRat.png");
        Level.getInstance().getLevelBoard().addRat(this);

    }

    public DeathRatItem getItem(){
        return item;
    }

    public void setItem(DeathRatItem item){
        this.item = item;
    }

    @Override
    public void move() {
        int newxPos = getX();
        int newyPos = getY();


        if (getCurrentDirection() == Directions.EAST) {
            newxPos += 1;
        } else if (getCurrentDirection() == Directions.WEST) {
            newxPos -= 1;
        } else if (getCurrentDirection() == Directions.NORTH) {
            newyPos -= 1;
        } else {
            newyPos += 1;
        }
        if (isTraversable(newxPos, newyPos)) {
            setPosition(newxPos,newyPos);

            //for a death rat, we need to send the item along with it.
            item.updatePos();

        } else {
            Directions oldDirection = getCurrentDirection();
             setCurrentDirection(Directions.values()[new Random().nextInt(Directions.values().length)]);

        }




    }



}