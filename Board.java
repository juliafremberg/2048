package org.cis1200.game2048;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class Board extends JPanel {
    private Game2048 tfe; // model for the game
    private JLabel status; // current status text

    private int[][] board;

    // Game constants
    public static final int BOARD_WIDTH = 350;
    public static final int BOARD_HEIGHT = 350;

    /**
     * Initializes the game board.
     */
    public Board(JLabel statusInit) {

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        tfe = new Game2048(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        board = new int[4][4]; // initialize board

        // key listener e is this then shift
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    tfe.playTurn('l');
                } else if (key == KeyEvent.VK_RIGHT) {
                    tfe.playTurn('r');
                } else if (key == KeyEvent.VK_UP) {
                    tfe.playTurn('u');
                } else if (key == KeyEvent.VK_DOWN) {
                    tfe.playTurn('d');
                }
                updateScore(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        /**addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                ttt.playTurn(p.x / 100, p.y / 100);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
         */
    }

    /**
     * (Re-)sets the game to its initial state.
     */

    public void reset() {
        tfe.reset();
        status.setText("Score: 0");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void undo() {
        tfe.undo();
        repaint();
        updateScore();
        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void save() throws IOException {
        tfe.save();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void load() throws FileNotFoundException {
        tfe.load();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }
    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    public void updateScore() {
        // update score
        int score = tfe.getScore();
        status.setText("Score: " + score);

        // check for winner
        boolean winner = tfe.checkWinner();
        if (winner) {
            // display winning message
            status.setText("YOU WON!!!");
        }
        boolean loser = tfe.checkLoser();
        if (loser) {
            status.setText("You Lost :(");
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        g.drawLine(BOARD_WIDTH / 4, 0, BOARD_WIDTH / 4, BOARD_HEIGHT);
        g.drawLine(BOARD_WIDTH * 2 / 4, 0, BOARD_WIDTH * 2 / 4, BOARD_HEIGHT);
        g.drawLine(BOARD_WIDTH * 3 / 4, 0, BOARD_WIDTH * 3 / 4, BOARD_HEIGHT);
        g.drawLine(0, BOARD_HEIGHT / 4, BOARD_WIDTH, BOARD_HEIGHT / 4);
        g.drawLine(0, BOARD_HEIGHT * 2 / 4, BOARD_WIDTH, BOARD_HEIGHT * 2 / 4);
        g.drawLine(0, BOARD_HEIGHT * 3 / 4, BOARD_WIDTH, BOARD_HEIGHT * 3 / 4);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int state = tfe.getCell(j, i);
                if (state != 0) {
                    g.drawString(Integer.toString(state), 30 + 100 * j, 30 + 100 * i);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
