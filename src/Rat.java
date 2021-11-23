import javax.sound.sampled.SourceDataLine;

/**
 * The main rat class. Covers all basic features of Rats.
 * @author Marcus
 * @author Iggy
 * @version 0.1
 * @since 0.1
 */

public class Rat {

    private Boolean sex;
    private Boolean isBaby;
    private Boolean isDeath;
    private Boolean alive;
    private Boolean isSterile;

    public Rat(Boolean sex, Boolean isBaby, Boolean isDeath, Boolean alive, Boolean isSterile) {
        this.sex = sex;
        this.isBaby = isBaby;
        this.isDeath = isDeath;
        this.alive = alive;
        this.isSterile = isSterile;
    }

    @Override
    public String toString() {
        return "";
    }
    
}
