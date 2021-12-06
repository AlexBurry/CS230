package game;

/**
 * This interface manages our time system.
 * Implement this to be able to call events at intervals. Remember to add it as a listener!
 *
 * @author Trafford
 * @author Alex
 * @version 1.0
 */
public interface ITickHandler {
    /**
     * This is called every tick. The value is defined in the TICKRATE variable of LEVEL.
     *
     * @param count how many ticks have passed? Resets every second.
     */
    void tickEvent(int count);
}
