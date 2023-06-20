package org.cis1200.tictactoe;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunTicTacToe implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("SnakesAndLadders");
        frame.setLocation(300, 300);
        JOptionPane.showMessageDialog(
                frame, "Hi Friend! Welcome to Snakes and Ladders. " +
                        "This game is meant for two players. \n" +
                        "You roll a dice and whoever reaches 100(exactly 100 on the board wins). " +
                        "\nThe snakes are noted by the snake picture and the "
                        +
                        "ladders are noted by the ladder picture. \n" +
                        " When you land on a snake, " +
                        "you arrive at its frowny face(matched by a black line) below and " +
                        "lose your position. \n When "
                        +
                        "you land on a ladder, you arrive at its happy face above" +
                        "(matched by a gray line) and improve your position. \n "
                        +
                        "Note that this game is customizable! You can add" +
                        " snakes and ladders wherever you want!"
                        +
                        "\n Just enter the coordinates of the" +
                        " two squares you want your snake and ladder to be.\n"
                        +
                        "Remember that sna" +
                        "ke goes from high to low " +
                        "and ladder goes from hig" +
                        "h to low(by defintion of snakes and ladders, duh)\n"
                        +
                        "NO DICE ROLLS ALL" +
                        "OWED AFTER A WINNER is announced. Because a person already won duh \n"
                        +
                        "Note that just like in the board game each sna" +
                        "ke and ladder MUST HAVE BOTH THEIR ENDPOINTS BE DISTINCT "
                        +
                        "from other sn" +
                        "akes or ladders.\n If you fail" +
                        " to do this," +
                        " the code will a" +
                        "utomatically r" +
                        "emove snakes or ladders that share endpoints. \n"
                        +
                        "Just like in the actual game," +
                        "this prevents visual confusion as each squ" +
                        "are only has one snake or ladder for visual clarity.\n"
        );

        int x = Integer.MIN_VALUE;
        LinkedList<Integer> tracker = new LinkedList<Integer>();

        while (!((x >= 1) && (x <= 20))) {
            String str1 = JOptionPane.showInputDialog(
                    frame, "How many s" +
                            "nakes do you want? No more than 20 allowed. \n" +
                            "I mean yeah we co" +
                            "uld have more than twenty, but then" +
                            " the board would be really cluttered.\n "
                            +
                            "You gotta have at least one " +
                            "tho, or else it wouldn't be" +
                            " called snakes and ladders"
            );

            try {
                x = Integer.parseInt(str1);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                continue;
            }

            if (!((x >= 1) && (x <= 20))) {
                JOptionPane.showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                continue;
            }
        }

        String snakes = "";
        for (int i = 0; i < x; i++) {

            int y = Integer.MIN_VALUE;

            while (!((y >= 1) && (y <= 99))) {
                String str1 = JOptionPane.showInputDialog(
                        frame, "Enter starting coordinate for Snake " + (i + 1) + "." +
                                "\n This is a 10x10 " +
                                "grid all sqaure are labeled 1 to 100, so your answ" +
                                "er must be between that!\n "
                                +
                                "Remember a Snake cannot be at 100 b/c th" +
                                "en the players would always hit a snake and never win!"
                                + "\n" +
                                " NO STRINGS ALLOWED. \n REMEMBER NO REPEAT C" +
                                "OORDINATES ALLOWED FOR SNAKES OR LADDER ENDPOINTS"
                );

                try {
                    y = Integer.parseInt(str1);
                } catch (Exception e) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (y == 100) {
                    JOptionPane.showMessageDialog(frame, "snake cannot be at 100");
                    y = Integer.MIN_VALUE;
                    continue;
                }

                if (!((y >= 1) && (y <= 99))) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (tracker.contains(y)) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! Looks that was already used a sqaure");
                    y = Integer.MIN_VALUE;
                    continue;
                }

            }

            snakes += y + " ";
            tracker.add(y);

            y = Integer.MIN_VALUE;

            while (!((y >= 1) &&
                    (y <= 99))) {
                String str2 = JOptionPane.showInputDialog(
                        frame, "Enter end" +
                                " coordinate for Snake " + (i + 1) + "." +
                                "This is a 10x10 " +
                                "grid all square are labeled " +
                                "1 to 100, so your answer" +
                                "must be between that!\n"
                                +
                                " NO STRINGS ALLOWED"
                );

                try {
                    y = Integer.parseInt(str2);
                } catch (Exception e) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (y == 100) {
                    JOptionPane.showMessageDialog(frame, "snake cannot be at 100");
                    y = Integer.MIN_VALUE;
                    continue;
                }

                if (!((y >= 1) && (y <= 99))) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (tracker.contains(y)) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! Looks that was already used a sqaure");
                    y = Integer.MIN_VALUE;
                    continue;
                }
            }

            snakes += y + " ";
            tracker.add(y);

        }

        int z = Integer.MIN_VALUE;

        while (!((z >= 1) && (z <= 20))) {
            String str1 = JOptionPane.showInputDialog(
                    frame, "How many ladders do you want? No more than 20 allowed. \n" +
                            "I mean yeah we could have more than twenty, but then the boa" +
                            "rd would be really cluttered.\n You gotta have at least one or "
                            +
                            "else the game wouldn't be called Snakes and Ladders"
            );

            try {
                z = Integer.parseInt(str1);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                continue;
            }

            if (!((z >= 1) && (z <= 20))) {
                JOptionPane.showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                continue;
            }
        }

        String ladders = "";
        for (int i = 0; i < z; i++) {

            int y = Integer.MIN_VALUE;

            while (!((y >= 1) && (y <= 100))) {
                String str1 = JOptionPane.showInputDialog(
                        frame, "Enter starting coordinate for ladder " + (i + 1) + "." +
                                "\n This is a 10x10 grid " +
                                "all sqaure" +
                                " are labeled 1 to 100, so your answer " +
                                "must be between that!\n"
                                +
                                " NO STRIN" +
                                "GS ALLOWED. \n R" +
                                "EMEMBER NO REPEAT COORDINATES ALLOWED FOR SNAKE" +
                                "S OR LADDER ENDPOINTS"
                );

                try {
                    y = Integer.parseInt(str1);
                } catch (Exception e) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (!((y >= 1) && (y <= 100))) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (tracker.contains(y)) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! Looks that was already used a square");
                    y = Integer.MIN_VALUE;
                    continue;
                }

            }

            ladders += y + " ";
            tracker.add(y);

            y = Integer.MIN_VALUE;

            while (!((y >= 1) && (y <= 100))) {
                String str2 = JOptionPane.showInputDialog(
                        frame, "Enter end coordinate for ladder " + (i + 1) + "." +
                                "This is a 10x10 grid all square are labeled 1 to 100, so your " +
                                "answer must be between that!\n"
                                +
                                " NO STRINGS ALLOWED"
                );

                try {
                    y = Integer.parseInt(str2);
                } catch (Exception e) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (!((y >= 1) && (y <= 100))) {
                    JOptionPane
                            .showMessageDialog(frame, "Whoa! That's not a valid input! Try Again");
                    continue;
                }

                if (tracker.contains(y)) {
                    JOptionPane.showMessageDialog(
                            frame, "Whoa! Looks that was already used as a sqaure"
                    );
                    y = Integer.MIN_VALUE;
                    continue;
                }

            }

            ladders += y + " ";
            tracker.add(y);

        }

        try {
            FileWriter file = new FileWriter("files/Objects");
            BufferedWriter buffer = new BufferedWriter(file);
            buffer.write(snakes.trim() + "\n");
            buffer.write(ladders.trim() + "\n");
            buffer.write("0 0");
            buffer.flush();
            buffer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board

        final JButton Turn = new JButton("Roll Dice");
        status_panel.add(Turn);

        final GameBoard board = new GameBoard(status, Turn);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}