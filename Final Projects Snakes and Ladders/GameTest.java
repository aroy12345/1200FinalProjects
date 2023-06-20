package org.cis1200.tictactoe;

import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {
    // we use this method to prevent randomDiceRolls and test results of set dice
    // values. Method copied from diceRoll method
    // in TicTacToe x is diceRoll
    private static void diceRoll(User u, TreeSet<BoardObject> gameObjects, int x) {
        int diceRoll = x;
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
            u.setUserMessage("They would go beyond 100! NOT ALLOWED. Exactly 100 for win!");
        }

    }

    @Test
    public void testCreateFourObjects() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 97" + "\n");
            buffer.write("4 98 3 96" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        Assertions.assertEquals(tester.getGameObjects().size(), 4);

    }

    @Test
    public void testCreateObjectRemoveDuplicate() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        Assertions.assertEquals(tester.getGameObjects().size(), 2);

    }

    @Test
    public void testCreateRollDiceSetValuePlayer1() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        diceRoll(tester.getPlayerUno(), tester.getGameObjects(), 5);
        Assertions.assertEquals(tester.getPlayerUno().getPosition(), 5);

    }

    @Test
    public void testCreateRollDiceSetValuePlayer2() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        diceRoll(tester.getPlayer2(), tester.getGameObjects(), 5);
        Assertions.assertEquals(tester.getPlayer2().getPosition(), 5);

    }

    @Test
    public void testCreateRollDiceHitSnake() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        diceRoll(tester.getPlayer2(), tester.getGameObjects(), 99);
        Assertions.assertEquals(tester.getPlayer2().getPosition(), 1);

    }

    @Test
    public void testCreateRollDiceHitLadder() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        diceRoll(tester.getPlayerUno(), tester.getGameObjects(), 4);
        Assertions.assertEquals(tester.getPlayerUno().getPosition(), 98);

    }

    @Test
    public void testCheckWinner() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        Assertions.assertEquals(tester.checkWinner(), 0);
        diceRoll(tester.getPlayerUno(), tester.getGameObjects(), 100);
        Assertions.assertEquals(tester.checkWinner(), 1);

    }

    @Test
    public void testDiceRollIsNotAllowedForOver100AndNoWinner() {
        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write("1 99 2 99" + "\n");
            buffer.write("4 98 3 99" + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TicTacToe tester = new TicTacToe();
        Assertions.assertEquals(tester.checkWinner(), 0);
        diceRoll(tester.getPlayerUno(), tester.getGameObjects(), 101);
        Assertions.assertEquals(tester.checkWinner(), 0);
        Assertions.assertEquals(tester.getPlayerUno().getPosition(), 0);

    }

}
