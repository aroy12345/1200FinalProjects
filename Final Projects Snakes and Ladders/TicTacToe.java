package org.cis1200.tictactoe;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeSet;
import java.io.FileReader;
import java.util.LinkedList;

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
public class TicTacToe {

    private int[][] board;
    private int numTurns;
    private boolean player1;
    private User playerUno;
    private User player2;

    private TreeSet<BoardObject> gameObjects = new TreeSet<BoardObject>();
    private boolean gameOver;

    public User getPlayerUno() {
        return playerUno;
    }

    public User getPlayer2() {
        return player2;
    }

    /**
     * Constructor sets up game state.
     */
    public TicTacToe() {
        reset();
    }

    public TreeSet<BoardObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * 
     */
    public boolean playTurn() {

        if (checkWinner() == 0) {
            if (player1) {
                diceRoll(playerUno);
            } else {
                diceRoll(player2);
            }
        }
        player1 = !player1;
        return true;
    }

    private void diceRoll(User u) {
        int diceRoll = (int) (Math.random() * 6) + 1;
        u.setDiceRoll(diceRoll);

        if (u.getPosition() + diceRoll <= 100) {
            boolean valid = true;

            for (BoardObject e : gameObjects) {
                if (e.getIsSnake() && e.getEnd() == u.getPosition() + diceRoll) {
                    u.setPosition(e.getStart());
                    valid = false;

                }
                if (!e.getIsSnake() && e.getStart() == u.getPosition() + diceRoll) {
                    u.setPosition(e.getEnd());
                    valid = false;
                }

            }
            if (valid) {
                u.setPosition(u.getPosition() + diceRoll);
            }
        } else {
            u.setUserMessage("They would go beyond 100! Exactly 100 for win!");
        }

    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if the game hits stalemate
     */
    public int checkWinner() {

        if (playerUno.getPosition() == 100) {
            return 1;
        }

        if (player2.getPosition() == 100) {
            return 2;
        }

        return 0;

    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println(playerUno.getPosition());
        System.out.println(player2.getPosition());

    }

    /**
     * reset (re-)sets the game state to start a new game.
     *
     * for (int i = 0; i < board.length; i++) {
     * for (int j = 0; j < board[i].length; j++) {
     * System.out.print(board[i][j]);
     * System.out.println(playerUno.getPosition());
     * if (j < 10) {
     * System.out.print(" | ");
     * }
     * }
     * if (i < 10) {
     * System.out.println("\n---------");
     * }
     * }
     */
    public void reset() {
        playerUno = new User(0);
        player2 = new User(0);

        try {
            BufferedReader reader = new BufferedReader(new FileReader("files/Objects"));
            String line;
            LinkedList<String> text = new LinkedList<String>();
            try {
                while ((line = reader.readLine()) != null) {
                    text.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String pos = text.remove(text.size() - 1);
            int checker = 0;
            for (String s : text) {
                String[] objects = s.split(" ");
                if (objects.length % 2 != 0) {
                    throw new IllegalArgumentException();
                }

                for (int i = 0; i < objects.length; i += 2) {
                    int starter = Math
                            .min(Integer.parseInt(objects[i]), Integer.parseInt(objects[i + 1]));
                    int end = Math
                            .max(Integer.parseInt(objects[i]), Integer.parseInt(objects[i + 1]));
                    if (checker == 0) {

                        boolean valid = false;
                        for (BoardObject e : gameObjects) {
                            if (e.equals(new BoardObject(true, starter, end))) {
                                valid = true;
                            }
                        }
                        if (valid) {
                            continue;
                        }

                        gameObjects.add(new BoardObject(true, starter, end));

                    } else {

                        boolean valid = false;
                        for (BoardObject e : gameObjects) {
                            if (e.equals(new BoardObject(false, starter, end))) {
                                valid = true;
                            }
                        }

                        if (valid) {
                            continue;
                        }

                        gameObjects.add(new BoardObject(false, starter, end));
                    }

                }
                checker++;
            }

            board = new int[10][10];
            checker = 100;
            for (int i = 0; i < 10; i++) {
                for (int j = 9; j >= 0; j--) {
                    board[j][i] = checker;
                    checker--;
                }
            }

            numTurns = 0;
            player1 = true;
            gameOver = false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     *
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    public boolean getCurrentPlayer() {
        return player1;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * 
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */

    public int getRows(int num) {
        if (!(num >= 0 && num <= 100)) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[0].length; k++) {
                if (board[i][k] == num) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getColumns(int num) {
        if (!(num >= 0 && num <= 100)) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < board.length; i++) {
            for (int k = 0; k < board[0].length; k++) {
                if (board[i][k] == num) {
                    return k;
                }
            }
        }
        return -1;

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
        TicTacToe t = new TicTacToe();

        System.out.println();
        System.out.println();
        System.out.println("Winner is: " + t.checkWinner());
    }
}
