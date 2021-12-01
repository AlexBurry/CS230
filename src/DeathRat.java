public class DeathRat extends Rat{
    public DeathRat(char sex, boolean isDeath, boolean alive, boolean isSterile, int xPos, int yPos, int speed) {
        super(sex, isDeath, alive, isSterile, xPos, yPos, speed);
        this.setImage("DeathRat.png");
        Level.getInstance().getLevelBoard().addRat(this);

    }




}