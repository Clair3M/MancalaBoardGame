package mancala;

/**
 * A class that provides the ability to play the board game Mancala according the Kalah rules
 * 
 * @see MancalaGame
 */
public class KalahRules extends GameRules {
    private static final long serialVersionUID = 1887193766321851712L;

    transient private final static int PLAYER_1 = 1;
    transient private final static int PLAYER_2 = 2;
    transient private final static int PLAYER_1_STORE = 6;
    transient private final static int PLAYER_2_STORE = 13;

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException {
        if (!isValidMove(startPit) || playerNum != getCurrentPlayer()) {
            throw new InvalidMoveException("Must choose pit on your side");
        }
        final int playerStones = getStoreCount(playerNum);
        if (distributeStones(startPit) == 0) {
            throw new InvalidMoveException("Cannot chose empty pit");
        }
        final MancalaDataStructure structure = getDataStructure();
        int currentPit = structure.getIteratorPos();
        if (shouldCaptureStones(pitPos(currentPit))) {
            int stolenStones;
            stolenStones = captureStones(pitPos(currentPit));
            structure.addToStore(playerNum, stolenStones);
        }
        if (!getsBonusTurn(currentPit)) {
            switchPlayers();
        }
        return getStoreCount(playerNum) - playerStones;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(final int startPit) {
        int stones;
        Countable current;
        final MancalaDataStructure structure = getDataStructure();
        structure.setIterator(startPit, getCurrentPlayer(), false);
        stones = structure.removeStones(startPit);
        for (int i = 0; i < stones; i++) {
            current = structure.next();
            current.addStone();
        }
        return stones;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    /* default */ int captureStones(final int stoppingPoint) {
        int stolenStones;
        final MancalaDataStructure structure = getDataStructure();
        stolenStones = structure.removeStones(12 - (stoppingPoint-1));
        stolenStones += structure.removeStones(stoppingPoint);
        return stolenStones;
    }

    /**
     * Determines if the conditions for a second turn have been met
     * 
     * @param stoppingPoint The final pit where a stone was placed while distributing
     * @return If the conditions for the second turn rule have been met
     */
    /* default */ boolean getsBonusTurn(final int stoppingPoint) {
        boolean bonusTurn = false;
        if ((stoppingPoint == PLAYER_1_STORE && getCurrentPlayer() == PLAYER_1) ||
            (stoppingPoint == PLAYER_2_STORE && getCurrentPlayer() == PLAYER_2)) {
            bonusTurn = true;
        }
        return bonusTurn;
    }
}