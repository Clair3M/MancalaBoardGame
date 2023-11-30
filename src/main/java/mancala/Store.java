package mancala;

import java.io.Serializable;

/**
 * Class that represents a store in the mancala board game
 * 
 * Holds a certain number of stones and has an associated owner
 * @see Player
 */
public class Store implements Countable, Serializable {
    private static final long serialVersionUID = 5121375325712016675L;
    
    private Player owner = null;
    private int numStones;

    /**
     * Creates a Store object
     * Defaults to 0 stones
     */
    public Store(){
        numStones = 0;
    }

    /**
     * Sets the owner of the store
     * 
     * @param player The player that will be the owner of the store
     */
    public void setOwner(final Player player){
        owner = player;
    }

    /**
     * Gets the owner of the store
     * 
     * @return The owner of the store
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * Adds stones to the store
     * 
     * @param amount The number of stones to add to the store
     */
    @Override
    public void addStones(final int amount){
        numStones += amount;
    }

    /**
     * Adds 1 stone to the store
     */
    @Override
    public void addStone() {
        addStones(1);
    }

    /**
     * Gets the number of stones in the store
     * 
     * @return The number of stones in the store
     */
    @Override
    public int getStoneCount(){
        return numStones;
    }

    /**
     * Removes and returns all of the stones in the store
     * 
     * @return The number of stones in the store
     */
    @Override
    public int removeStones(){
        final int stones = getStoneCount();
        addStones(-1 * stones);
        return stones;
    }

    /**
     * Returns a string that represents the store
     * 
     * @return The string that represents the store
     */
    @Override
    public String toString(){
        return Integer.toString(getStoneCount());
    }
}