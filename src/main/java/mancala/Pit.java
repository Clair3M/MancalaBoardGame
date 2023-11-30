package mancala;

import java.io.Serializable;

/**
 * A class that represents a basic mancala board game pit
 * 
 * The pit holds a number of stones in the pit
 */
public class Pit implements Countable, Serializable {
    private static final long serialVersionUID = -3807213840442923128L;
    
    private int stones;

    /**
     * Creates Pit object, defaults 0 stones
     */
    public Pit(){
        stones = 0;
    }

    /**
     * Get the number of stones in the pit
     * 
     * @return The number of stones in the pit
     */
    @Override
    public int getStoneCount(){
        return stones;
    }

    /**
     * Adds 1 stone to the pit
     */
    @Override
    public void addStone(){
        addStones(1);
    }

    /**
     * Adds stones to the pit
     * 
     * @param numToAdd The number of stones to add to the pit
     */
    @Override
    public void addStones(final int amount) {
        stones += amount;
    }

    /**
     * Removes and returns the number of stones in the pit
     * 
     * @return The number of stones that were in the pit
     */
    @Override
    public int removeStones(){
        final int numStones = getStoneCount();
        addStones(-1 * numStones);
        return numStones;
    }

    /**
     * Returns a string that represents the Pit
     * 
     * @return the string that represents the Pit
     */
    @Override
    public String toString(){
        return Integer.toString(getStoneCount());
    }

}