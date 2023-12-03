package mancala;

import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable{
    private static final long serialVersionUID = -8736695373538954380L; 

    transient private final static int PLAYER_1 = 1;
    transient private final static int PLAYER_2 = 2;
    transient private final static int PLAYER_1_STORE = 6;
    transient private final static int PLAYER_2_STORE = 13;

    final private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)

    /**
     * Constructor to initialize the game board.
     */
    public GameRules() {
        gameBoard = new MancalaDataStructure();
        resetBoard();
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }
    
    /**
     * Gets the stone count in a player's store.
     *
     * @param playerNum The player number (1 or 2).
     * @return The stone count in the player's store.
     */
    public int getStoreCount(final int storeNum) {
        return gameBoard.getStoreCount(storeNum);
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    /* default */ MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Gets current player number
     * @return The number of the current player
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setCurrentPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Check if a side (player's pits) is empty.
     *
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    /* default */ boolean isSideEmpty(final int pitNum) {
        boolean empty = false;
        if (pitNum >= 1 && pitNum <= 6){
            empty = true;
            for (int i = 1; i <= 6; i++){
                if (gameBoard.getNumStones(i) != 0) {
                    empty = false;
                    break;
                }
            }
        } else if (pitNum >= 7 && pitNum < 13) {
            empty = true;
            for (int i = 7; i < 13; i++){
                if (gameBoard.getNumStones(i) != 0) {
                    empty = false;
                    break;
                }
            }
        }
        return empty;
    }

    /**
     * Determines if a valid move was made
     * 
     * @param pitNum The pit chosen for a given move
     * @return Whether or not the move was valid
     */
    /* default */ boolean isValidMove(final int pitNum) {
        boolean validMove;
        validMove = (pitNum >= 1 && pitNum <= PLAYER_1_STORE && getCurrentPlayer() == PLAYER_1) ||
            (pitNum > PLAYER_1_STORE && pitNum < PLAYER_2_STORE && getCurrentPlayer() == PLAYER_2);
        return validMove;
    }

     /**
     * Determines if the conditions for the capture stones rule have been met
     * 
     * @param stoppingPoint The final pit where a stone was placed while distributing
     * @return If the conditions for the capture stones rule have been met
     */
    /* default */ boolean shouldCaptureStones(final int stoppingPoint) {
        boolean captureStones = false;
        final MancalaDataStructure structure = getDataStructure();
        if (isValidMove(stoppingPoint) && structure.getNumStones(stoppingPoint) == 1) {
                captureStones = true;
            }
        return captureStones;
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    /* default */ abstract int distributeStones(int startPit);

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    /* default */ abstract int captureStones(int stoppingPoint);

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        final Store p1Store = new Store();
        p1Store.setOwner(one);
        one.setStore(p1Store);
        gameBoard.setStore(p1Store, PLAYER_1);

        final Store p2Store = new Store();
        p2Store.setOwner(two);
        two.setStore(p2Store);
        gameBoard.setStore(p2Store, PLAYER_2);
    }

    /**
     * Switches the current player
     */
    public void switchPlayers() {
        if (getCurrentPlayer() == PLAYER_1){
            setCurrentPlayer(PLAYER_2);
        } else {
            setCurrentPlayer(PLAYER_1);
        }
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.setUpPits();
        gameBoard.emptyStores();
    }

    /**
     * Distributes stones remaining on the board to the correct players
     */
    public void clearBoard() {
        final MancalaDataStructure structure = getDataStructure();
        for (int i = 1; i <= 6; i++) {
            structure.addToStore(1, structure.removeStones(i));
        }
        for (int i = 7; i < 13 ; i++) {
            structure.addToStore(2, structure.removeStones(i));
        }
    }

    /**
     * converts arrayindex to actual index
     * 
     * @param index the index in the array
     * @return the board index
     */
    /* default */ int pitPos(int index) {
        int pit = index;
        if (pit < 6) {
            pit += 1;
        }
        return pit; 
    }

    /**
     * Returns a string representation of the gameRules class
     * 
     * @return string that represents the gameRules object
     */
    @Override
    public String toString() {
        final MancalaDataStructure structure = getDataStructure();
        return structure.toString();
    }
}
