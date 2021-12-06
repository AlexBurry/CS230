package sprites;

import javafx.scene.image.Image;

/**
 * Holds references to all images, reducing the calls of new Image("").
 *
 * @author Trafford
 */
public class ImageRefs {
    //rats -------------------------------------
    //male
    public static final Image maleRatUp = new Image("sprites/ratMale.png");
    public static final Image maleRatDown = new Image("sprites/ratMale-Down.png");
    public static final Image maleRatLeft = new Image("sprites/ratMale-Left.png");
    public static final Image maleRatRight = new Image("sprites/ratMale-Right.png");
    //female
    public static final Image femaleRatUp = new Image("sprites/ratFemale.png");
    public static final Image femaleRatDown = new Image("sprites/ratFemale-Down.png");
    public static final Image femaleRatLeft = new Image("sprites/ratFemale-Left.png");
    public static final Image femaleRatRight = new Image("sprites/ratFemale-Right.png");
    //babies
    public static final Image babyRatUp = new Image("sprites/babyRat.png");
    public static final Image babyRatDown = new Image("sprites/babyRat-Down.png");
    public static final Image babyRatLeft = new Image("sprites/babyRat-Left.png");
    public static final Image babyRatRight = new Image("sprites/babyRat-Right.png");
    //deathRat
    public static final Image deathRatUp = new Image("sprites/DeathRat.png");
    public static final Image deathRatDown = new Image("sprites/DeathRat-Down.png");
    public static final Image deathRatLeft = new Image("sprites/DeathRat-Left.png");
    public static final Image deathRatRight = new Image("sprites/DeathRat-Right.png");

    //items -------------------------------------
    //No Entry
    public static final Image noEntryDamage0 = new Image("sprites/NoEntry.png");
    public static final Image noEntryDamage1 = new Image("sprites/NoEntry-Damaged1.png");
    public static final Image noEntryDamage2 = new Image("sprites/NoEntry-Damaged2.png");
    public static final Image noEntryDamage3 = new Image("sprites/NoEntry-Damaged3.png");
    public static final Image noEntryDamage4 = new Image("sprites/NoEntry-Damaged4.png");
    //Sterilise
    public static final Image steriliseImage = new Image("sprites/steriliseImage.png");
    //Poison
    public static final Image poisonImage = new Image("sprites/PoisonTrap.png");
    //bombs
    public static final Image bombStage4 = new Image("sprites/Bomb4.png");
    public static final Image bombStage3 = new Image("sprites/Bomb3.png");
    public static final Image bombStage2 = new Image("sprites/Bomb2.png");
    public static final Image bombStage1 = new Image("sprites/Bomb1.png");
    //Gas
    public static final Image gasCenter = new Image("sprites/GasC.png");
    public static final Image gasOuter = new Image("sprites/GasO.png");
    public static final Image gasOuterChild = new Image("sprites/GasOC.png");
    //Sex Changes
    public static final Image maleToFemaleSC = new Image("sprites/maleFemaleSexChange.png");
    public static final Image femaleToMaleSC = new Image("sprites/femaleMaleSexChange.png");

    //UI -------------------------------------
    //for menu, buttons and backgrounds
    public static final Image iconBomb = new Image("sprites/iconBomb.png");
    public static final Image iconSterilise = new Image("sprites/iconSterilise.png");
    public static final Image iconNoEntry = new Image("sprites/iconNoEntry.png");
    public static final Image iconGas = new Image("sprites/iconGas.png");
    public static final Image iconMFSC = new Image("sprites/iconMFSC.png");
    public static final Image iconFMSC = new Image("sprites/iconFMSC.png");
    public static final Image iconPoison = new Image("sprites/iconPoison.png");
    public static final Image iconDeathRat = new Image("sprites/iconDeathRat.png");
    //other -------------------------------------
    //for anything else
}
