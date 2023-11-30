package mancala;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AyoRulesTest {

    private AyoRules game;
    private Player player1;
    private Player player2;
    private MancalaDataStructure structure;

    @BeforeEach
    void setUp() {
        game = new AyoRules();
        game.resetBoard();
        player1 = new Player("Claire");
        player2 = new Player("Sarah");
        structure = game.getDataStructure();
        game.registerPlayers(player1, player2);
    }

    @Test
    void moveStonesValidP1() throws InvalidMoveException {
        game.moveStones(1, 1);
        assertEquals(game.getNumStones(1), 0);
        assertEquals(game.getNumStones(2), 7);
        assertEquals(game.getNumStones(3), 1);
        assertEquals(game.getNumStones(4), 6);
        assertEquals(game.getNumStones(5), 1);
        assertEquals(game.getNumStones(6), 6);
        assertEquals(game.getNumStones(7), 6);
        assertEquals(game.getNumStones(8), 0);
        assertEquals(game.getNumStones(9), 1);
        assertEquals(game.getNumStones(10), 0);
        assertEquals(game.getNumStones(11), 6);
        assertEquals(game.getNumStones(12), 6);
        assertEquals(game.getStoreCount(1), 8);
        assertEquals(game.getStoreCount(2), 0);
    }

    @Test
    void moveStonesValidP2() throws InvalidMoveException {
        game.setCurrentPlayer(2);
        game.moveStones(12,2);

        assertEquals(game.getNumStones(1), 1);
        assertEquals(game.getNumStones(2), 6);
        assertEquals(game.getNumStones(3), 1);
        assertEquals(game.getNumStones(4), 6);
        assertEquals(game.getNumStones(5), 6);
        assertEquals(game.getNumStones(6), 6);
        assertEquals(game.getNumStones(7), 0);
        assertEquals(game.getNumStones(8), 1);
        assertEquals(game.getNumStones(9), 6);
        assertEquals(game.getNumStones(10), 6);
        assertEquals(game.getNumStones(11), 6);
        assertEquals(game.getNumStones(12), 0);
        assertEquals(game.getStoreCount(1), 0);
        assertEquals(game.getStoreCount(2), 3);
    }

    @Test
    void moveBelowBoard() {
        assertThrows(InvalidMoveException.class, () -> {
            game.moveStones(0,1);
        });
    }

    @Test
    void moveAboveBoard() {
        game.setCurrentPlayer(2);
        assertThrows(InvalidMoveException.class, () -> {
            game.moveStones(13,2);
        });
    }

    @Test
    void moveStonesInvalidPlayer() {
        assertThrows(InvalidMoveException.class, () -> {
            game.moveStones(5,3);
            });
    }

    @Test
    void moveStonesP1NoAddToStore() throws InvalidMoveException{
        structure.removeStones(5);
        structure.removeStones(8);
        assertEquals(structure.getNumStones(5), 0);
        game.moveStones(1,1);
        assertEquals(game.getStoreCount(1), 0);
    }

    @Test
    void moveStonesP2AddToStore() throws InvalidMoveException{
        structure.removeStones(2);
        game.setCurrentPlayer(2);
        game.moveStones(11,2);
        assertEquals(game.getStoreCount(2), 1);
    }

    @Test
    void moveStonesSwitchPlayer() throws InvalidMoveException{
        game.moveStones(5,1);
        assertEquals(game.getCurrentPlayer(), 2);
    }

    @Test
    void moveStonesReturnP1() throws InvalidMoveException{
        assertEquals(game.moveStones(4,1), 3);
    }

    @Test
    void moveStonesReturnP2() throws InvalidMoveException{
        game.setCurrentPlayer(2);
        assertEquals(game.moveStones(7,2), 8);
    }

    @Test
    void moveStonesLandsInStoreP1() throws InvalidMoveException {
        assertEquals(game.moveStones(3,1), 1);

        assertEquals(game.getNumStones(1), 4);
        assertEquals(game.getNumStones(2), 4);
        assertEquals(game.getNumStones(3), 0);
        assertEquals(game.getNumStones(4), 5);
        assertEquals(game.getNumStones(5), 5);
        assertEquals(game.getNumStones(6), 5);
        assertEquals(game.getNumStones(7), 4);
        assertEquals(game.getNumStones(8), 4);
        assertEquals(game.getNumStones(9), 4);
        assertEquals(game.getNumStones(10), 4);
        assertEquals(game.getNumStones(11), 4);
        assertEquals(game.getNumStones(12), 4);
        assertEquals(game.getStoreCount(1), 1);
        assertEquals(game.getStoreCount(2), 0);
    }

    @Test
    void moveStonesLandsInStoreP2() throws InvalidMoveException{
        game.setCurrentPlayer(2);
        assertEquals(game.moveStones(9,2), 1);

        assertEquals(game.getNumStones(1), 4);
        assertEquals(game.getNumStones(2), 4);
        assertEquals(game.getNumStones(3), 4);
        assertEquals(game.getNumStones(4), 4);
        assertEquals(game.getNumStones(5), 4);
        assertEquals(game.getNumStones(6), 4);
        assertEquals(game.getNumStones(7), 4);
        assertEquals(game.getNumStones(8), 4);
        assertEquals(game.getNumStones(9), 0);
        assertEquals(game.getNumStones(10), 5);
        assertEquals(game.getNumStones(11), 5);
        assertEquals(game.getNumStones(12), 5);
        assertEquals(game.getStoreCount(1), 0);
        assertEquals(game.getStoreCount(2), 1);
    }

    @Test
    void distributeStones1Cycle() {
        structure.removeStones(8);
        assertEquals(game.distributeStones(5), 4);
    } 

    @Test
    void distributeStonesMultiCycle() {
        game.setCurrentPlayer(2);
        assertEquals(game.distributeStones(10), 26);
    }

    @Test
    void distributeStonesEmpty() {
        structure.removeStones(1);
        assertEquals(game.distributeStones(1), 0);
    }

    @Test
    void distributeStonesStore1() {
        assertEquals(game.distributeStones(3), 4);
    }

    @Test
    void distributeStonesStore2() {
        game.setCurrentPlayer(2);
        assertEquals(game.distributeStones(9), 4);
    }

    @Test
    void captureStonesP1() {
        structure.removeStones(5);
        assertEquals(game.captureStones(5), 4);
        assertEquals(game.getNumStones(8), 0);
    }

    @Test
    void captureStonesP2() {
        structure.removeStones(12);
        assertEquals(game.captureStones(12), 4);
        assertEquals(game.getNumStones(1), 0);
    }

}