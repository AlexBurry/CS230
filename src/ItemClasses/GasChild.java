package ItemClasses;

/**
 * Smaller node of gasItem, reproduces on condition.
 * @author Trafford
 * @author Jack
 * @version 0.1
 * @since 0.1
 */
public class GasChild extends GasItem{

    private boolean isAbleToReproduce = false;

    public GasChild(int x, int y) {
        super(x, y);
    }
}