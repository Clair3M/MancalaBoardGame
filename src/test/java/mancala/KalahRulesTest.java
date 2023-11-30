package mancala;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KalahRulesTest {

    private KalahRules game;
    private Player player1;
    private Player player2;
    private MancalaDataStructure structure;

    @BeforeEach
    void setUp() {
        game = new KalahRules();
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
        for (int i = 2; i <= 5; i++){
            assertEquals(game.getNumStones(i), 5);
        }
        assertEquals(game.getNumStones(6), 4);
    }

    @Test
    void moveStonesValidP2() {
        try {
            game.setCurrentPlayer(2);
            game.moveStones(7, 2);
            for (int i = 8; i <= 11; i++) {
                assertEquals(game.getNumStones(i), 5);
            }
            assertEquals(game.getNumStones(12), 4);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void moveStonesBelowPit() {
        assertThrows(InvalidMoveException.class, () -> {
            game.moveStones(0, 1);
        });
    }

    @Test
    void moveStonesAbovePit() {
        assertThrows(InvalidMoveException.class, () -> {
            game.moveStones(13, 1);
        });
    }


    @Test
    void moveStonesInvalidPlayer() {
        assertThrows(InvalidMoveException.class, () -> {
            game.moveStones(10, 3);
        });
    }


    @Test
    void moveStonesStoreSame() {
        try {
            assertEquals(game.moveStones(0, 1), 0);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void moveStonesStoreIncr() {
        try {
            assertEquals(game.moveStones(4, 1), 1);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void moveStonesSwitchPlayer() {
        try {
            assertEquals(game.getCurrentPlayer(), 1);
            game.moveStones(0, 1);
            assertEquals(game.getCurrentPlayer(), 2);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void moveStonesSecondTurn() {
        try {
            assertEquals(game.getCurrentPlayer(), 1);
            game.moveStones(3, 1);
            assertEquals(game.getCurrentPlayer(), 1);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void moveStonesSecondTurnP2() {
        try {
            game.setCurrentPlayer(2);
            assertEquals(game.getCurrentPlayer(), 2);
            game.moveStones(9, 2);
            assertEquals(game.getCurrentPlayer(), 2);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void moveStonesCaptureStones() throws InvalidMoveException {
        structure.removeStones(5);
        assertEquals(game.moveStones(1, 1), 5);
    }

    @Test
    void moveStonesOverBoard() {
        try {
            game.setCurrentPlayer(2);
            assertEquals(game.moveStones(11, 2), 1);
            assertEquals(game.getNumStones(1), 5);
        } catch (InvalidMoveException err) {
            System.out.println(err.getMessage());
        }
    }

    @Test
    void distributeStonesValidPits() {
        game.setCurrentPlayer(1);
        for (int i = 1; i <= 12; i++) {
            if (i >= 7){
                game.setCurrentPlayer(2);
            }
            assertEquals(game.distributeStones(i), 4);
            game.resetBoard();
        }
    }

    @Test
    void distributeStonesValidPitsNext() {
        for (int i = 1; i <= 12; i++) {
            if (i >= 7){
                game.setCurrentPlayer(2);
            }
            assertEquals(game.distributeStones(i), 4);
            assertEquals(game.getNumStones(i), 0);
            for (int j = i+1; j <= i+3; j++) {
                if (j > 12){
                    assertEquals(game.getNumStones(j%12), 5);
                } else {
                    assertEquals(game.getNumStones(j), 5);
                }
            }
            game.resetBoard();
        }
    }

    @Test
    void getBonusTurnNonStore() {
        game.setCurrentPlayer(2);
        assertEquals(game.getsBonusTurn(10), false);
    }

    @Test
    void getBonusTurnStore1P2() {
        game.setCurrentPlayer(2);
        assertEquals(game.getsBonusTurn(6), false);
    }

    @Test
    void getBonusTurnStore2P1() {
        game.setCurrentPlayer(1);
        assertEquals(game.getsBonusTurn(13), false);
    }

    @Test
    void getBonusTurnStore1P1() {
        game.setCurrentPlayer(1);
        assertEquals(game.getsBonusTurn(6), true);
    }

    @Test
    void getBonusTurnStore2P2() {
        game.setCurrentPlayer(2);
        assertEquals(game.getsBonusTurn(13), true);
    }

    @Test
    void captureStonesRemovesOtherSide() {
        game.captureStones(4);
        assertEquals(game.getNumStones(9), 0);
    }

    @Test
    void captureStonesRemovesSide() {
        game.captureStones(7);
        assertEquals(game.getNumStones(7), 0);
    }

    @Test
    void captureStonesReturns() {
        assertEquals(game.captureStones(9), 8);
    }

    @Test
    void captureStonesBothEmpty() {
        structure.removeStones(3);
        structure.removeStones(10);
        assertEquals(game.captureStones(3), 0);
    }

    @Test
    void sCaptureStonesP1() {
        structure.removeStones(5);
        structure.addStones(5, 1);
        assertEquals(game.shouldCaptureStones(5), true);
    }

    @Test
    void sCaptureStonesP2() {
        structure.removeStones(8);
        structure.addStones(8, 1);
        game.setCurrentPlayer(2);
        assertEquals(game.shouldCaptureStones(8), true);
    }

    @Test
    void sCaptureStonesNoP1() {
        assertEquals(game.shouldCaptureStones(2), false);
    }

    @Test
    void sCaptureStonesNoP2() {
        game.setCurrentPlayer(2);
        assertEquals(game.shouldCaptureStones(9), false);
    }

}