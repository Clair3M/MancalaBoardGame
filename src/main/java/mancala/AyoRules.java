package mancala;

/**
 * A class that provides the ability to play the board game Mancala according the Ayoayo rules
 * 
 * @see MancalaGame
 */
public class AyoRules extends GameRules {
    private static final long serialVersionUID = 8868613842003379656L;

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
        final int currentPit = structure.getIteratorPos();
        if (shouldCaptureStones(pitPos(currentPit))){
            int stolenStones;
            stolenStones = captureStones(pitPos(currentPit));
            structure.addToStore(playerNum, stolenStones);
        }
        switchPlayers();
        return getStoreCount(playerNum) - playerStones;
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    /* default */ int distributeStones(final int startPit) {
        int stones;
        int totalStones = 0;
        Countable current = null;
        final MancalaDataStructure structure = getDataStructure();
        structure.setIterator(startPit, getCurrentPlayer(), true);
        stones = structure.removeStones(startPit);
        do {
            totalStones += stones;
            for (int i = 0; i < stones; i++) {
                current = structure.next();
                current.addStone();
            }
            stones = 0;
            if (current != null && current.getClass() != Store.class) {
                stones = current.getStoneCount();
                if (stones > 1) {
                    stones = current.removeStones();
                }
            }
        } while (stones > 1);
        return totalStones;
    }

    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    @Override
    /* default */ int captureStones(final int stoppingPoint) {
        int stolenStones = 0;
        final MancalaDataStructure structure = getDataStructure();
        if (stoppingPoint > 0 && stoppingPoint < 13){
            stolenStones = structure.removeStones(12 - (stoppingPoint-1));
        }
        return stolenStones;
    }
}