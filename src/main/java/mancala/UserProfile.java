package mancala;

import java.io.Serializable;

/**
 * A class that holds the game statistics of a specific player
 * 
 * @see Player
 */
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -7014144449814303951L;

    private String name;
    private int kalaPlayed;
    private int kalaWins;
    private int ayoPlayed;
    private int ayoWins;

    /**
     * Create new UserProfile Object
     * defaults to empty string name
     */
    public UserProfile() {
        this("");
    }

    /**
     * Creates new UserProfile Object
     * 
     * @param newName The name associated with the user profile
     */
    public UserProfile(final String newName) {
        setUserName(newName);
        reset();
    }

    /**
     * Sets a new name for the UserProfile
     * 
     * @param newName The new name for the UserProfile
     */
    public void setUserName(final String newName) {
        if (newName == null) {
            name = "";
        } else {
            name = newName;
        }
    }

    /**
     * Get the name of the UserProfile
     * 
     * @return The profile name
     */
    public String getUserName() {
        return name;
    }

    /**
     * Add 1 to the number of Kala games played
     */
    public void addKalaPlay() {
        kalaPlayed++;
    }

    /**
     * Get the number of Kala games played
     * 
     * @return The number of Kala games played
     */
    public int getKalaPlays() {
        return kalaPlayed;
    }

    /**
     * Add 1 to the number of Kala games played
     */
    public void addKalaWin() {
        kalaWins++;
    }

    /**
     * Get the number of Kala games won
     * 
     * @return The number of Kala games won
     */
    public int getKalaWins() {
        return kalaWins;
    }

    /**
     * Add 1 to the number of Ayo games played
     */
    public void addAyoPlay() {
        ayoPlayed++;
    }

    /**
     * Get the number of Ayo games played
     * 
     * @return The number of Ayo games played
     */
    public int getAyoPlays() {
        return ayoPlayed;
    }

    /**
     * Add 1 to the number of Ayo games won
     */
    public void addAyoWin() {
        ayoWins++;
    }

    /**
     * Get the number of Ayo games won
     * 
     * @return The number of Ayo games played
     */
    public int getAyoWins() {
        return ayoWins;
    }

    /**
     * Resets the number of games played and won to 0
     */
    public void reset() {
        kalaWins = 0;
        kalaPlayed = 0;
        ayoWins = 0;
        ayoPlayed = 0;
    }

    /**
     * Returns a string representation of a UserProfile object
     * 
     * @return a string that represents the UserProfile object
     */
    @Override
    public String toString() {
        final StringBuilder player = new StringBuilder();
        player.append("Kalah plays: " + Integer.toString(getKalaPlays()) + "\n");
        player.append("Ayoayo plays: " + Integer.toString(getAyoPlays()) + "\n");
        player.append("Kalah wins: " + Integer.toString(getKalaWins()) + "\n");
        player.append("Ayoayo wins: " + Integer.toString(getAyoWins()) + "\n");
        return player.toString();
    }

    /**
     * Returns a string representation of a UserProfile object using html formating
     * 
     * @return a string that represents the UserProfile object
     */
    public String toStringHTML() {
        final StringBuilder player = new StringBuilder();
        player.append("<html>Kalah plays: " + Integer.toString(getKalaPlays()) + "<br/>");
        player.append("Ayoayo plays: " + Integer.toString(getAyoPlays()) + "<br/>");
        player.append("Kalah wins: " + Integer.toString(getKalaWins()) + "<br/>");
        player.append("Ayoayo wins: " + Integer.toString(getAyoWins()) + "<html/>");
        return player.toString();
    }
}