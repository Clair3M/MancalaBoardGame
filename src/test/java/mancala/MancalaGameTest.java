package mancala;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MancalaGameTest {

    private MancalaGame game;
    private MancalaDataStructure structure;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        // Initialize a new game before each test
        KalahRules rules = new KalahRules(); // Assuming you have a GameRules class
        game = new MancalaGame(rules);
        structure = rules.getDataStructure();
        // Create players
        player1 = new Player("Claire");
        player2 = new Player("Sarah");
        // Set up players for the game
        game.setPlayers(player1, player2);
    }

    @Test
    public void testGetNumStonesOnSideThrowsExceptionForZero() {
        assertThrows(PitNotFoundException.class, () -> {
            game.getNumStonesOnSide(0);
        });
    }

    @Test
    public void testGetNumStonesOnSideThrowsExceptionFor13() {
        assertThrows(PitNotFoundException.class, () -> {
            game.getNumStonesOnSide(13);
        });
    }

    @Test
    public void testGetNumStonesOnSideForPit3() throws PitNotFoundException {
        assertEquals(game.getNumStonesOnSide(3), 24);
    }

    @Test
    public void testGetNumStonesOnSideForPit8() throws PitNotFoundException {
        assertEquals(game.getNumStonesOnSide(8), 24);
    }

    @Test
    public void testGetNumStonesOnSideEmptyPit4() throws PitNotFoundException {
        for (int i = 1; i <= 6; i++) {
            structure.removeStones(i);
            structure.addStones(i, 0);
        }

        assertEquals(game.getNumStonesOnSide(6), 0);
    }

    @Test
    public void testGetNumStonesOnSideEmptyPit11() throws PitNotFoundException {
        // Assuming player 2's side (pits 7-12) starts empty
        for (int i = 7; i <= 12; i++) {
            structure.removeStones(i);
            structure.addStones(i, 0);
        }

        assertEquals(game.getNumStonesOnSide(12), 0);
    }

    @Test
    public void testGetNumStonesOnSideMixedPits() throws PitNotFoundException {
        structure.removeStones(1);
        structure.addStones(1, 2);
        structure.removeStones(2);
        structure.removeStones(3);
        structure.addStones(3, 5);
        structure.removeStones(4);
        structure.addStones(4, 0);
        structure.removeStones(5);
        structure.addStones(5, 0);
        structure.removeStones(6);
        structure.addStones(6, 3);

        assertEquals(game.getNumStonesOnSide(3), 10);
    }

    @Test
    public void testMoveInvalidPit0() {
        assertThrows(InvalidMoveException.class, () -> {
            game.move(0);
        });
    }

    @Test
    public void testMoveInvalidPit13() {
        assertThrows(InvalidMoveException.class, () -> {
            game.move(13);
        });
    }

    @Test
    public void testMoveOpponentsP1() {
        assertThrows(InvalidMoveException.class, () -> {
            game.move(8);
        });
    }

    @Test
    public void testMoveOpponentsP2() {
        game.getBoard().setCurrentPlayer(2);

        assertThrows(InvalidMoveException.class, () -> {
            game.move(2);
        });
    }

    @Test
    public void testMoveReturnsCorrectStones() throws InvalidMoveException {
        assertEquals(game.move(4), 22);
    }

    @Test
    public void testGetWinnerP1() throws GameNotOverException {
        for (int i = 1; i <= 6; i++) {
            structure.removeStones(i);
        }
        assertNotNull(player1.getStore());
        assertNotNull(player2.getStore());
        structure.addToStore(1, 25);
        assertEquals(game.getWinner(), player1);
    }

    @Test
    public void testGetWinnerP2() throws GameNotOverException {
        for (int i = 1; i <= 6; i++) {
            structure.removeStones(i);
        }
        assertNotNull(player1.getStore());
        assertNotNull(player2.getStore());
        structure.addToStore(2, 25);
        assertEquals(game.getWinner(), player2);
    }

    @Test
    public void testGetWinnerTie() throws GameNotOverException {
        for (int i = 1; i <= 12; i++) {
            structure.removeStones(i);
        }
        assertNotNull(player1.getStore());
        assertNotNull(player2.getStore());
        structure.addToStore(1, 2);
        structure.addToStore(2, 2);
        assertNull(game.getWinner());
    }

    @Test
    public void testGetWinnerNotOver() {
        assertThrows(GameNotOverException.class, () -> {
            game.getWinner();
        });
    }

}