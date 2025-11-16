package com.insa.othello.constant;

public enum Message {
    BLACK_TURN("It's black's turn."),
    WHITE_TURN("It's white's turn."),
    DRAW("It's a draw !"),
    BLACK_WIN("The black wins !"),
    WHITE_WIN("The white wins !");

    private final String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
