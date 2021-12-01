import javafx.scene.image.Image;

public class AdultRat extends Rat{

    private Image sprite;



        public AdultRat(char sex, Boolean isDeath, Boolean alive, Boolean isSterile, int xPos, int yPos, int speed) {
            super(sex, isDeath, alive, isSterile, xPos, yPos, speed);
            while (alive){
               /* if (sex == "f"){
                    setFemaleImage();
                }*/
            }

        }
}

