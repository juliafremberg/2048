package org.cis1200.game2048;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class GameTest {
    // test playTurn methods

    @Test
    public void testleft() {
        // create board w/ a 4 @ [2][2]
        Game2048 tfe = new Game2048();
        int[][] newBoard = new int[4][4];
        newBoard[2][2] = 4;
        tfe.setBoard(newBoard);
        // shift left
        tfe.playTurn('l');
        // check if cell [0][2] has the expected value
        int actual = tfe.getCell(0,2);
        int expected = 4;
        assertEquals(expected, actual);
    }
    @Test
    public void testRight() {
        // create board w/ a 4 @ [2][2]
        Game2048 tfe = new Game2048();
        int[][] newBoard = new int[4][4];
        newBoard[2][2] = 4;
        tfe.setBoard(newBoard);
        // shift right
        tfe.playTurn('r');
        // check if cell [3][2] has the expected value
        int actual = tfe.getCell(3,2);
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    public void testUp() {
        // create board w/ a 4 @ [2][2]
        Game2048 tfe = new Game2048();
        int[][] newBoard = new int[4][4];
        newBoard[2][2] = 4;
        tfe.setBoard(newBoard);
        // shift up
        tfe.playTurn('u');
        // check if cell [2][0] has the expected value
        int actual = tfe.getCell(2,0);
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    public void testDown() {
        // create board w/ a 4 @ [2][2]
        Game2048 tfe = new Game2048();
        int[][] newBoard = new int[4][4];
        newBoard[2][2] = 4;
        tfe.setBoard(newBoard);
        // shift down
        tfe.playTurn('d');
        // check if cell [2][3] has the expected value
        int actual = tfe.getCell(2,3);
        int expected = 4;
        assertEquals(expected, actual);
    }

    /*  Edge Case:
    // Test that the board does not add a random tile if the board has not changed.
    // If the user clicks the left key and all the tiles are in the left column, a
    // tile should not be added to the board and the board should remain the same
     */
    @Test
    public void testLeftKeyPressedButBoardShouldNotChange() {
        // create board w/ two tiles at [2][0] and [2]
        Game2048 tfe = new Game2048();
        int[][] newBoard = new int[4][4];
        newBoard[1][0] = 4;
        newBoard[2][0] = 2;
        tfe.setBoard(newBoard);
        // shift left
        tfe.playTurn('l');
        // board should not have changed at all
        int[][] actual = newBoard;
        int [][] expected = new int[][]{{0, 0, 0, 0}, {4, 0, 0, 0}, {2, 0, 0, 0}, {0, 0, 0, 0}};
        assertArrayEquals(expected, actual);
    }

    // Check Winner -> 2048 is on the board
    @Test
    public void testWinner() {
        // create board w/ a 2048 tile at [1][2]
        Game2048 tfe = new Game2048();
        int[][] newBoard = new int[4][4];
        newBoard[1][2] = 2048;
        tfe.setBoard(newBoard);
        // check if the game was won
        boolean actual = tfe.checkWinner();
        assertTrue(actual);
    }

    // Check Loser -> board is full and no cells can be merged
    @Test
    public void testLoser() {
        // create board w/ no valid moves
        Game2048 tfe = new Game2048();
        int[][] board = {
                {2, 4, 8, 16},
                {16, 8, 4, 2},
                {2, 4, 8, 16},
                {16, 8, 4, 2}
        };
        tfe.setBoard(board);
        // check if the game was lost
        boolean actual = tfe.checkLoser();
        assertTrue(actual);
    }

    /*  Edge Case:
    //  Test when the board is full w/ no mergable cells,
    // but 2048 is on the board, so they still win
     */
    @Test
    public void test2048CellOnBoardWithNoValidMoves() {
        // create board w/ no valid moves
        Game2048 tfe = new Game2048();
        int[][] board = {
                {2, 4, 8, 16},
                {16, 8, 4, 2},
                {2, 4, 8, 16},
                {16, 8, 4, 2}
        };
        // add 2048 to the board
        board[2][2] = 2048;
        tfe.setBoard(board);
        // check that the game was won
        boolean actual = tfe.checkWinner();
        assertTrue(actual);
    }

    /* Edge Case:
    // Check that the user does not lose if the board is full, but you can still merge cells
     */
    @Test
    public void checkLoserBoardFull() {
        // create board that is full but cells can still be merged
        Game2048 tfe = new Game2048();
        int[][] board = {
                {2, 4, 8, 16},
                {2, 8, 4, 2},
                {2, 4, 8, 16},
                {16, 8, 4, 2}
        };
        tfe.setBoard(board);
        // check if the game was lost
        boolean actual = tfe.checkWinner();
        assertFalse(actual);
    }
}
