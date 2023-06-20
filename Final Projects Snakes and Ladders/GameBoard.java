package org.cis1200.tictactoe;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
public class GameBoard extends JPanel {

    private TicTacToe ttt; // model for the game
    private JLabel status;
    private JButton turn;// current status text

    private JOptionPane instructions;

    // Game constants
    public static final int BOARD_WIDTH = 750;
    public static final int BOARD_HEIGHT = 750;

    /**
     * Initializes the game board.
     */

    public TicTacToe getGameBoard() {
        return ttt;
    }

    public GameBoard(JLabel statusInit, JButton t) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new TicTacToe(); // initializes model for the game
        status = statusInit;
        turn = t;// initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        turn.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {

                // updates the model given the coordinates of the mouseclick
                ttt.playTurn();
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt.reset();
        status.setText("Player 1's Turn");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    public void updateStatus() {
        if (ttt.getCurrentPlayer()) {
            status.setText(
                    "Player 1's Turn: Player 2 last rolled a " + ttt.getPlayer2().getDiceRoll()
                            + ". " + ttt.getPlayer2().getUserMessage()
            );
        } else {
            status.setText(
                    "Player 2's Turn: Player 1 last rolled a " + ttt.getPlayerUno().getDiceRoll()
                            + ". " + ttt.getPlayerUno().getUserMessage()
            );
        }

        int winner = ttt.checkWinner();
        if (winner == 1) {
            status.setText(
                    "Player 1 last rolled a " + ttt.getPlayerUno().getDiceRoll() + ". " +
                            ttt.getPlayerUno().getUserMessage() + " " + "Player 1 wins!"
            );
        } else if (winner == 2) {
            status.setText(
                    "Player 2 last rolled a " + ttt.getPlayer2().getDiceRoll() + ". " +
                            ttt.getPlayer2().getUserMessage() + " " + "Player 2 wins!"
            );

        } else if (winner == 3) {
            status.setText("It's a tie.");
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

        for (int i = 1; i <= 9; i++) {
            g.drawLine(i * 75, 0, i * 75, 750);
        }

        for (int i = 1; i <= 9; i++) {
            g.drawLine(0, i * 75, 750, i * 75);
        }

        int checker = 100;
        for (int i = 0; i < 10; i++) {
            for (int j = 9; j >= 0; j--) {

                Color c = Color.white;

                if (i % 4 == 0) {

                    if (checker % 4 == 0) {
                        c = Color.orange;
                    }

                    if (checker % 4 == 1) {
                        c = Color.white;
                    }

                    if (checker % 4 == 2) {
                        c = Color.blue;
                    }

                    if (checker % 4 == 3) {
                        c = Color.green;
                    }
                } else if (i % 4 == 1) {

                    if (checker % 4 == 0) {
                        c = Color.orange;
                    }

                    if (checker % 4 == 1) {
                        c = Color.blue;
                    }

                    if (checker % 4 == 2) {
                        c = Color.white;
                    }

                    if (checker % 4 == 3) {
                        c = Color.red;
                    }

                } else if (i % 4 == 2) {

                    if (checker % 4 == 0) {
                        c = Color.red;
                    }

                    if (checker % 4 == 1) {
                        c = Color.green;
                    }

                    if (checker % 4 == 2) {
                        c = Color.blue;
                    }

                    if (checker % 4 == 3) {
                        c = Color.cyan;
                    }

                } else if (i % 4 == 3) {
                    if (checker % 4 == 0) {
                        c = Color.yellow;
                    }

                    if (checker % 4 == 1) {
                        c = Color.pink;
                    }

                    if (checker % 4 == 2) {
                        c = Color.blue;
                    }

                    if (checker % 4 == 3) {
                        c = Color.orange;
                    }

                }

                g.setColor(c);

                int x = (i * 750) / 10;
                int y = (j * 750) / 10;
                int w = 750 / 10;
                int h = 750 / 10;

                g.fillRect(y, x, w, h);

                g.setColor(Color.black);
                g.drawString(String.valueOf(checker), (30 + 75 * j), (30 + 75 * i));

                checker--;

            }
        }
        g.setColor(Color.black);
        int col = 1;
        int row = 4;
        int col1 = 2;
        int row1 = 8;

        try {
            BufferedImage image = ImageIO.read(new File("files/snake.png"));
            BufferedImage image2 = ImageIO.read(new File("files/frownyFace.png"));
            BufferedImage image3 = ImageIO.read(new File("files/ladder.png"));
            BufferedImage image4 = ImageIO.read(new File("files/happy.png"));
            BufferedImage image5 = ImageIO.read(new File("files/tennisball.png"));
            BufferedImage image6 = ImageIO.read(new File("files/quarter.png"));

            g.drawImage(
                    image5, (75 * 1 / 2) + 75 * ttt.getRows(ttt.getPlayerUno().getPosition()) - 12,
                    (75 * 1 / 2) + 75 * ttt.getColumns(ttt.getPlayerUno().getPosition()) - 6,
                    75 / 4 * 1, 75 / 4 * 1, null
            );

            g.drawImage(
                    image6, (75 * 1 / 2) + 75 * ttt.getRows(ttt.getPlayer2().getPosition()) - 12,
                    (75 * 1 / 2) + 75 * ttt.getColumns(ttt.getPlayer2().getPosition()) - 6,
                    75 / 2 * 1, 75 / 2 * 1, null
            );

            for (BoardObject e : ttt.getGameObjects()) {
                int start = e.getStart();
                int end = e.getEnd();
                boolean isSnake = e.getIsSnake();

                if (isSnake) {
                    g.drawImage(
                            image, (75 * 1 / 2) + 75 * ttt.getRows(end) - 12,
                            (75 * 1 / 2) + 75 * ttt.getColumns(end) - 6,
                            75 / 2 * 1, 75 / 2 * 1, null
                    );

                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(Color.black);

                    g2.drawLine(
                            (75 * 1 / 2) + 75 * ttt.getRows(end),
                            (75 * 1 / 2) + 75 * ttt.getColumns(end),
                            (75 * 1 / 2) + 75 * ttt.getRows(start),
                            (75 * 1 / 2) + 75 * ttt.getColumns(start)
                    );

                    g.drawImage(
                            image2, (75 * 1 / 2) + 75 * ttt.getRows(start) - 12,
                            (75 * 1 / 2) + 75 * ttt.getColumns(start) + 5, 75 / 3 * 1, 75 / 3 * 1,
                            null
                    );

                } else {

                    g.drawImage(
                            image3, (75 * 1 / 2) + 75 * ttt.getRows(start) - 12,
                            (75 * 1 / 2) + 75 * ttt.getColumns(start) - 5, 75 / 2 * 1, 75 / 2 * 1,
                            null
                    );

                    Graphics2D g2 = (Graphics2D) g;

                    g2.setStroke(new BasicStroke(2));
                    g2.setColor(Color.lightGray);

                    g2.drawLine(
                            (75 * 1 / 2) + 75 * ttt.getRows(end),
                            (75 * 1 / 2) + 75 * ttt.getColumns(end),
                            (75 * 1 / 2) + 75 * ttt.getRows(start),
                            (75 * 1 / 2) + 75 * ttt.getColumns(start)
                    );

                    g.drawImage(
                            image4, (75 * 1 / 2) + 75 * ttt.getRows(end) - 12,
                            (75 * 1 / 2) + 75 * ttt.getColumns(end) + 5,
                            75 / 3 * 1, 75 / 3 * 1, null
                    );

                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
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
