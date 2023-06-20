package org.cis1200.tictactoe;

public class User {
    private int position;
    private int diceRoll;

    private String userMessage;

    public User(int p) {
        position = 0;
        diceRoll = 0;
        userMessage = "";
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String word) {
        userMessage = word;
    }

    public void setPosition(int po) {
        String message = "They went from " + position + " to " + po + ". ";
        if (po < position) {
            message += "This is b/c there's a snake at " + (position + diceRoll);
        }

        if (po > position + diceRoll) {
            message += "This is b/c there's ladder at " + (position + diceRoll);
        }

        position = po;
        userMessage = message;
    }

    public int getPosition() {
        return position;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
    }
}
