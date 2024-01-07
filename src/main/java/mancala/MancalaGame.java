package mancala;

import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;

/**
 * A class that reprents the operations of the board game Mancala
 * 
 * Can be used to play with either Mancala rules or Ayoayo rules
 * @see KalahRules
 * @see AyoRules
 */
public class MancalaGame implements Serializable {
    private static final long serialVersionUID = -4196769547156183645L;

    transient final static int PLAYER_ONE = 1;
    transient final static int PLAYER_TWO = 2;

    private GameRules gameBoard;

    private Player player1;
    private Player player2;

    /**
     * Create new MancalaGame object
     */
    public MancalaGame(){
    }

    /**
     * Creates new MancalaGame object
     * @param rules The game rules to play the game with
     */
    public MancalaGame(final GameRules rules) {
        gameBoard = rules;
    }

    /**
     * Creates new MancalaGame object
     * @param rules The game rules to play the game with (1 or 2)
     */
    public MancalaGame(final int rules) {
        if (rules == 2) {
            gameBoard = new AyoRules();
        } else {
            gameBoard = new KalahRules();
        }
    }

    /**
     * Wrapper method for the GameRules getStoreCount Method
     * 
     * @param store the store to get the stones count of (1 or 2)
     * @return the number of stones in the store
     */
    public int getStoreCount(final int store) {
        return gameBoard.getStoreCount(store);
    }

    /**
     * Sets the players for the game
     * @param onePlayer The first player for the game
     * @param twoPlayer The second player for the game
     */
    public void setPlayers(final Player onePlayer, final Player twoPlayer){
        gameBoard.registerPlayers(onePlayer, twoPlayer);
        player1 = onePlayer;
        player2 = twoPlayer;
        gameBoard.setCurrentPlayer(1);
    }

    public void replacePlayer(final Player player, final int oldPlayer) {
        final Store playerStore = getPlayer(oldPlayer).getStore();
        player.setStore(playerStore);
        if (oldPlayer == PLAYER_ONE) {
            player1 = player;
        } else if (oldPlayer == PLAYER_TWO) {
            player2 = player;
        }
    }

    /**
     * Returns an ArrayList of the players for the game
     * @return The ArrayList of the game players
     */
    public ArrayList<Player> getPlayers(){
        final List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return (ArrayList<Player>) players;
    }

    /**
     * Returns single player by number
     * 
     * @param player the desired player (1 or 2)
     * @return the player
     */
    public Player getPlayer(final int player) {
        Player returnPlayer = null;
        if (player == PLAYER_ONE) {
            returnPlayer =  player1;
        } else if (player == PLAYER_TWO) {
            returnPlayer = player2;
        }
        return returnPlayer;
    }

    /**
     * Get the player whos turn is next
     * @return The player whos turn is next
     */
    public Player getCurrentPlayer() {
        Player player = player1;
        if (gameBoard.getCurrentPlayer() == PLAYER_TWO) {
            player = player2;
        }
        return player;
    }

    /**
     * Sets the player who has the next turn
     * @param player The player who has the next turn
     */
    public void setCurrentPlayer(final Player player) {
        if (player.equals(player1)){
            gameBoard.setCurrentPlayer(PLAYER_ONE);
        } else if (player.equals(player2)) {
            gameBoard.setCurrentPlayer(PLAYER_TWO);
        }
    }

    /**
     * Incremenets the number of games played for both players
     */
    public void addGamePlayed() {
        if (gameBoard.getClass().equals(KalahRules.class)) {
            player1.getUserProfile().addKalaPlay();
            player2.getUserProfile().addKalaPlay();
        } else if (gameBoard.getClass().equals(AyoRules.class)) {
            player1.getUserProfile().addAyoPlay();
            player2.getUserProfile().addAyoPlay();
        }
    }

    /**
     * Increases the number of wins for the winning player
     * @param player the player that won
     */
    public void addPlayerWin(final Player player) {
        if (gameBoard.getClass().equals(KalahRules.class)) {
            player.getUserProfile().addKalaWin();
        } else if (gameBoard.getClass().equals(AyoRules.class)) {
            player.getUserProfile().addAyoWin();
        }
    }

    /**
     * Sets the rules of the game
     * @param theBoard The new game rules for the game
     */
    public void setBoard(final GameRules theBoard){
        gameBoard = theBoard;
    }

    /**
     * Gets the current gameRules object of the current game
     * @return The GameRules object of the game
     */
    public GameRules getBoard(){
        return gameBoard;
    }

    /**
     * Gets the number of stones in a given pit
     * 
     * @param pitNum The pit number to get the stones of
     * @return The number of stones in the pit
     */
    public int getNumStones(final int pitNum) throws PitNotFoundException {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * Distributes the stones from chosen pit
     * 
     * @param startPit The pit where the stones will be taken from
     * @return The number of Stones remaining on the players side
     */
    public int move(final int startPit) throws InvalidMoveException {
        try {   
            gameBoard.moveStones(startPit, gameBoard.getCurrentPlayer());
            return getNumStonesOnSide(startPit);
        } catch (PitNotFoundException err) {
            throw new InvalidMoveException(err.getMessage());
        }
    }

    /**
     * Returns the number of stones remaining on side of the board
     * 
     * @param startPit The pit on the side of the baord to check
     * @return The number of stones on the side of the board
     */
    public int getNumStonesOnSide(final int startPit) throws PitNotFoundException{
        int totalStones = 0;
        if (startPit >= 1 && startPit <= 6) {
            for (int i = 1; i <= 6; i++){
                totalStones += gameBoard.getNumStones(i);
            }
        } else if (startPit >= 7 && startPit < 13){
            for (int i = 7; i < 13; i++) {
                totalStones += gameBoard.getNumStones(i);
            }
        } else {
            throw new PitNotFoundException();
        }
        return totalStones;
    }

    /**
     * Gets the number of stones in the players store
     * 
     * @param player The player whos store is being considered
     * @return The number of stones in the players store
     */
    public int getStoreCount(final Player player) throws NoSuchPlayerException{
        if (player.equals(player1) || player.equals(player2)){
            return player.getStoreCount();
        } else {
            throw new NoSuchPlayerException("Player is Not in Game");
        }
    }

    /**
     * Gets the winner of the game
     * 
     * @return The player who won the game, return null if the players tied
     */
    public Player getWinner() throws GameNotOverException{
        if (!isGameOver()){
            throw new GameNotOverException();
        }
        Player winner = null;
        gameBoard.clearBoard();
        if (player1.getStoreCount() > player2.getStoreCount()){
            winner = player1;
        } else if (player1.getStoreCount() < player2.getStoreCount()){
            winner = player2;
        }
        return winner;
    }

    /**
     * Checks if the game is over
     * 
     * @return A boolean value that says if the game is over
     */
    public boolean isGameOver(){
        boolean gameOver = false;
        if (gameBoard.isSideEmpty(3) || gameBoard.isSideEmpty(9)){
            gameOver = true;
        }
        return gameOver;
    }
    
    /**
     * Resets the game board
     */
    public void startNewGame(){
        gameBoard.resetBoard();
    }

    /**
     * Returns a string representation of a MancalaGame object
     * 
     * @return The string that represents the game
     */
    @Override
    public String toString(){
        final StringBuilder string = new StringBuilder();
        for (int i = 12;i >= 7;i--){
            string.append(String.format("%3d ", i));
        }
        string.append("\n" + gameBoard.toString());
        for (int i = 1;i <= 6;i++){
            string.append(String.format("%3d ", i));
        }
        string.append("\n");
        string.append(player1.toString() + "\n");
        string.append(player2.toString() + "\n");
        return string.toString();
    }
}