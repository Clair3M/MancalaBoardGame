package mancala;

import java.io.Serializable;

/**
 * A class that represents a player in the Mancala board game
 * 
 * Has an associated user profile and store
 * @see UserProfile
 * @see Store
 */
public class Player implements Serializable {
    private static final long serialVersionUID = -689609149637637821L;
    
    private String name;
    transient private Store playerStore;
    private UserProfile profile;

    /**
     * Creates new Player object
     * Defaults to empty string name
     */
    public Player(){
        this("");
        profile = new UserProfile("");
    }

    /** 
     * Creates new Player object
     * 
     * @param newName The name of the new Player object
     */
    public Player(final String newName){
        profile = new UserProfile(newName);
        setName(newName);
    }

    /**
     * Creates new Player object
     * 
     * @param newProfile The profile of the new Player object
     */
    public Player(final UserProfile newProfile) {
        setUserProfile(newProfile);
    }

    /**
     * Gets the name of the player
     * 
     * @return The name of the player
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the player
     * 
     * @param newName The new name of the player. Null value defaults to empty string.
     */
    public void setName(final String newName){
        if (newName == null){
            name = "";
            profile.setUserName("");
        } else {
            name = newName;
            profile.setUserName(newName);
        }
    }

    /**
     * Sets the profile for the player
     * 
     * @param newProfile The new profile for the player
     */
    public void setUserProfile(final UserProfile newProfile) {
        profile = new UserProfile();
        setName(newProfile.getUserName());
    }

    /**
     * Sets the store for the player
     * 
     * @param store The new store for the player
     */
    public void setStore(final Store store){
        playerStore = store;
    }

    /**
     * Gets the UserProfile associated with the player
     * 
     * @return The UserProfile associated with the player
     */
    public UserProfile getUserProfile() {
        return profile;
    }
    
    /**
     * Gets the store associated with the player
     * 
     * @return The Store that belongs to the player
     */
    public Store getStore(){
        return playerStore;
    }

    /**
     * Get the number of stones in the player's store
     * 
     * @return The number of stones in the store
     */
    public int getStoreCount(){
        return playerStore.getStoneCount();
    }  

    /**
     * Resets the UserProfile of the player
     */
    public void clearProfile() {
        profile.reset();
    }

    /**
     * Returns a string that represents the player
     * 
     * @return The name of the player
     */
    @Override
    public String toString(){
        return name;
    }

}