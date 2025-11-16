package com.insa.othello.constant;

public enum Direction {
    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    LEFT(0, -1),
    RIGHT(0, 1),
    DOWN_LEFT(1, -1),
    DOWN(1, 0),
    DOWN_RIGHT(1, 1);

    private final int offsetR;
    private final int offsetC;

    Direction(int offsetR, int offsetC) {
        this.offsetR = offsetR;
        this.offsetC = offsetC;
    }

    public int getOffsetR() {
        return offsetR;
    }

    public int getOffsetC() {
        return offsetC;
    }
}
