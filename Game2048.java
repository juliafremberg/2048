package org.cis1200.game2048;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */
import java.util.Arrays;
import java.util.LinkedList;
import java.io.*;

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Game2048 {

    private int[][] board;
    private LinkedList<int[][]> moves;
    private int score;

    /**
     * Constructor sets up game state.
     */
    public Game2048() {
        reset();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        moves = new LinkedList<>();
        board = new int[4][4];
        addRandomTiles();
        score = 0;
    }
    // undoes the user's last move.
    // if the board is not empty, removes last array in the linked list
    public void undo() {
        if (moves.size() > 1) {
            moves.removeLast();
            for (int[] a : moves.getLast()) {
                System.out.println(Arrays.toString(a));
            }
            this.board = moves.getLast();
        }
    }

    public void save() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[i][j] + "");
                // if not the last element in row, add a comma after each value
                if (j < board.length - 1) {
                    sb.append(",");
                }
            }
            // new line after each row
            sb.append("\n");
        }
        BufferedWriter br = new BufferedWriter(new FileWriter(
                "/Users/juliafremberg/Desktop/hw09_local_temp/files/2048.txt"));
        try {
            // save string of the board
            br.write(sb.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(
                new FileReader("files/2048.txt"));
        try {
            // save string of the board
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] strArray;
                strArray = line.split(",");
                for (int i = 0; i < 4; i++) {
                    board[count][i] = Integer.parseInt(strArray[i]);
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    private int[] getColumn(int[][] board, int columnIndex) {
        int[] column = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            column[i] = board[i][columnIndex];
        }
        return column;
    }
    public void playTurn(char direction) {
        boolean boardShifted = false; // flag variable to check if board shifts
        if (direction == 'l') {
            for (int i = 0; i < board.length; i ++) {
                // shift non-zero elements to the left and merge same value tiles
                int[] newRow = new int[board[i].length];
                int index = 0;
                boolean merged = false;
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] != 0) {
                        if (index > 0 && newRow[index - 1] == board[i][j] && !merged) {
                            // merge the two adjacent cells
                            int mergedValue = board[i][j] * 2;
                            newRow[index - 1] = mergedValue;
                            merged = true;
                            score += mergedValue;
                        } else {
                            // add element to row
                            newRow[index++] = board[i][j];
                            merged = false;
                        }
                    }
                }
                // add zeros to end of row to keep length
                while (index < newRow.length) {
                    newRow[index++] = 0;
                }
                // update the row on the board if the board shifted
                if (!Arrays.equals(board[i], newRow)) {
                    board[i] = newRow;
                    boardShifted = true;
                }
            }
        } else if (direction == 'r') {
            for (int i = 0; i < board.length; i ++) {
                // shift non-zero elements to the right and merge same value tiles
                int[] newRow = new int[board[i].length];
                int index = newRow.length - 1;
                boolean merged = false;
                for (int j = board[i].length - 1; j >= 0; j--) {
                    if (board[i][j] != 0) {
                        if (index < newRow.length - 1 && newRow[index + 1] ==
                                board[i][j] && !merged) {
                            // merge adjacent tiles
                            int mergedValue = board[i][j] * 2;
                            newRow[index + 1] = mergedValue;
                            merged = true;
                            score += mergedValue;
                        } else {
                            // add element to row
                            newRow[index--] = board[i][j];
                            merged = false;
                        }
                    }
                }
                // add zeros to beginning of row to keep the length
                while (index >= 0) {
                    newRow[index--] = 0;
                }
                // update the row on the board if board was shifted
                if (!Arrays.equals(board[i], newRow)) {
                    board[i] = newRow;
                    boardShifted = true;
                }
            }
        } else if (direction == 'u') {
            // iterate through columns of 2d array board
            for (int j = 0; j < board[0].length; j++) {
                // create 1d array to represent each column
                int[] newCol = new int[board.length];
                int index = 0;
                boolean merged = false;
                // copy over the columns
                for (int i = 0; i < board.length; i++) {
                    if (board[i][j] != 0) {
                        if (index > 0 && newCol[index - 1] == board[i][j] && !merged) {
                            // merge adjacent cells
                            int mergedValue = board[i][j] * 2;
                            newCol[index - 1] = mergedValue;
                            merged = true;
                            score += mergedValue;
                        } else {
                            // add element to column
                            newCol[index++] = board[i][j];
                            merged = false;
                        }
                    }
                }
                // add zeros to the bottom of col to keep length
                while (index < newCol.length) {
                    newCol[index++] = 0;
                }
                // update the column on the board if board was shifted
                if (!Arrays.equals(getColumn(board, j), newCol)) {
                    for (int i = 0; i < board.length; i ++) {
                        board[i][j] = newCol[i];
                        boardShifted = true;
                    }
                }
            }
        } else if (direction == 'd') {
            for (int j = 0; j < board[0].length; j++) {
                // shift non-zero elements down
                int[] newCol = new int[board.length];
                int index = newCol.length - 1;
                boolean merged = false;
                for (int i = board.length - 1; i >= 0; i--) {
                    if (board[i][j] != 0) {
                        if (index < newCol.length - 1 && newCol[index + 1] ==
                                board[i][j] && !merged) {
                            // merge adjacent cells
                            int mergedValue = board[i][j] * 2;
                            newCol[index + 1] = mergedValue;
                            merged = true;
                            score += mergedValue;
                        } else {
                            // add  element to column
                            newCol[index--] = board[i][j];
                            merged = false;
                        }
                    }
                }
                // add zeros to beginning of row to keep length
                while (index >= 0) {
                    newCol[index--] = 0;
                }
                // update the column on the board if board shifted
                if (!Arrays.equals(getColumn(board, j), newCol)) {
                    for (int i = 0; i < board.length; i ++) {
                        board[i][j] = newCol[i];
                        boardShifted = true;
                    }
                }
            }
        }
        if (boardShifted) {
            boolean tileAdded = false;
            while (!tileAdded) {
                int row = (int) (Math.random() * 3) + 1;
                int col = (int) (Math.random() * 3) + 1;
                if (board[row][col] == 0) {
                    board[row][col] = 2;
                    tileAdded = true;
                    // add board to linked list
                }
            }
        }
        int[][] board2 = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board2[i][j] = board[i][j];
            }
        }
        moves.add(board2);
    }


    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public boolean checkWinner() {
        // Check horizontal win
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLoser() {
        // check if there are no valid moves left
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    // there is an empty tile -> there is a valid move
                    return false;
                } else if (i < board.length - 1 && board[i][j] == board[i + 1][j]) {
                    // there is an adjacent equal tile underneath
                    // current tile that can be combined ->
                    // there is a valid move
                    return false;
                } else if (j < board[i].length - 1 && board[i][j] == board[i][j + 1]) {
                    // there is an adjacent equal tile to the right of current tile that
                    // can be combined -> there is a valid move
                    return false;
                }
            }
        }
        // there are no valid moves left on the board
        return true;
    }

    /**
     * printGameState prints the current game state
     * for debugging.

    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            if (i < 2) {
                System.out.println("\n---------");
            }
        }
    }
     */


    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */

    public void addRandomTiles() {
        // add two random tiles
        for (int i = 0; i < 2; i++) {
            int row = (int)(Math.random() * 3) + 1;
            int col = (int)(Math.random() * 3) + 1;
            // check to make sure that space is not already taken
            while (board[row][col] != 0) {
                row = (int)(Math.random() * 3) + 1;
                col = (int)(Math.random() * 3) + 1;
            }

            int value = (Math.random() < 0.5) ? 2 : 4;
            board[row][col] = value;
        }
    }


    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public int getCell(int c, int r) {
        return board[r][c];
    }

    public int getScore() {
        return score;
    }

    public void setBoard(int[][] newBoard) {
        board = newBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {

    }
}
