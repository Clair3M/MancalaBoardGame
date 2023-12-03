package mancala;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameRulesTest {
    private GameRules game;
    private Player player1; 
    private Player player2;
    MancalaDataStructure structure;

    @BeforeEach
    public void setUp() {
        game = new AyoRules();
        player1 = new Player("Claire");
        player2 = new Player("Sarah");
        structure = game.getDataStructure();
        game.registerPlayers(player1, player2);
    }

    @Test
    public void isSideEmptyP1True() {
        for (int i = 1; i <= 6; i++) {
            structure.removeStones(i);
        }
        assertEquals(game.isSideEmpty(1), true);
    } 

    @Test
    public void isSideEmptyP2True() {
        for (int i = 7; i <= 12; i++) {
            structure.removeStones(i);
        }
        assertEquals(game.isSideEmpty(7), true);
    } 

    @Test
    public void isSideEmptyP1False() {
        for (int i = 1; i <= 6; i++) {
            structure.removeStones(i);
        }
        structure.addStones(1,1);
        assertEquals(game.isSideEmpty(1), false);
    }

    @Test
    public void isSideEmptyP2False() {
        for (int i = 7; i <= 12; i++) {
            structure.removeStones(i);
        }
        structure.addStones(12, 1);
        assertEquals(game.isSideEmpty(7), false);
    } 

    @Test
    public void isSideEmptyZero() {
        for (int i = 1; i <= 6; i++) {
            structure.removeStones(i);
        }
        assertEquals(game.isSideEmpty(0), false);
    } 

    @Test
    public void isSideEmpty13() {
        for (int i = 7; i <= 12; i++) {
            structure.removeStones(i);
        }
        assertEquals(game.isSideEmpty(13), false);
    }

    @Test
    public void testRegisterPlayers() {
        Player p1 = new Player("test1");
        Player p2 = new Player("test2");
        game.registerPlayers(p1, p2);
        assertNotNull(p1.getStore());
        assertNotNull(p2.getStore());
    }

    @Test
    public void testClearBoardEmpty() {
        for (int i = 1; i <= 12; i++) {
            structure.removeStones(i);
        }
        game.clearBoard();
        assertEquals(player1.getStoreCount(), 0);
        assertEquals(player2.getStoreCount(), 0);
    }

    @Test
    public void testClearBoardRemovesStones() {
        game.clearBoard();
        for (int i = 1; i <= 12; i++) {
            assertEquals(structure.getNumStones(i), 0);
        }
    }

    @Test
    public void testClearBoardDefaultStones() {
        game.clearBoard();
        assertEquals(player1.getStoreCount(), 24);
        assertEquals(player2.getStoreCount(), 24);
    }

    @Test
    public void testClearBoardMixedStones() {
        structure.removeStones(1);
        structure.addStones(1, 2);
        structure.removeStones(2);
        structure.removeStones(3);
        structure.addStones(3, 5);
        structure.removeStones(4);
        structure.removeStones(5);
        structure.removeStones(6);
        structure.addStones(6, 3);

        structure.removeStones(7);
        structure.addStones(7, 32);
        structure.removeStones(8);
        structure.removeStones(9);
        structure.addStones(9, 6);
        structure.removeStones(10);
        structure.removeStones(11);
        structure.addStones(9, 9);
        structure.removeStones(12);
        structure.addStones(12, 4);

        game.clearBoard();
        assertEquals(player1.getStoreCount(), 10);
        assertEquals(player2.getStoreCount(), 51);
    }

}