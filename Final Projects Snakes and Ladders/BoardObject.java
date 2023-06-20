package org.cis1200.tictactoe;

public class BoardObject implements Comparable<BoardObject> {
    private final boolean isSnake;
    private final int start;
    private final int end;

    public BoardObject(boolean x, int s, int e) {
        isSnake = x;
        start = s;
        end = e;

        if (!(start >= 1 && start <= 100) || !(end >= 1 && end <= 100)) {
            throw new IllegalArgumentException("Not Valid points");
        }

        if (isSnake && end == 100) {
            throw new IllegalArgumentException("Snake cannot start at 100");
        }

        if (!isSnake && start == 0) {
            throw new IllegalArgumentException("Ladder cannot start at 0");
        }

        if (start == end) {
            throw new IllegalArgumentException("start cannot equal end");
        }

    }

    public boolean equals(BoardObject e) {
        return (e.getEnd() == this.getEnd() || e.getStart() == this.getStart());
    }

    public int compareTo(BoardObject e) {
        if (e.getEnd() == this.getEnd() || e.getStart() == this.getStart()) {
            return 0;
        } else if (e.getEnd() > this.getEnd()) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean getIsSnake() {
        return isSnake;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

}
