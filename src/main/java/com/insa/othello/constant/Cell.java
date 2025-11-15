package com.insa.othello.constant;

public enum Cell {
    EMPTY,
    BLACK,
    WHITE,
    PLAYABLE;

    public Cell opponent() {
        return switch (this) {
            case BLACK -> WHITE;
            case WHITE -> BLACK;
            case EMPTY -> EMPTY;
            case PLAYABLE -> PLAYABLE;
        };
    }
}
