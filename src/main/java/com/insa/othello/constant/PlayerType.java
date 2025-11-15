package com.insa.othello.constant;

public enum PlayerType {
    HUMAN("Human player"),
    MIN_MAX("MinMax"),
    MIN_MAX_ALPHA_BETA("MinMax (α–β)"),
    NEGA_MAX("NegaMax");

    private final String value;

    PlayerType(String value) {
        this.value = value;
    }

    public static PlayerType fromValue(String value) {
        for (PlayerType type : PlayerType.values())
            if (type.value.equals(value))
                return type;
        throw new IllegalArgumentException("Unknown PlayerType value: " + value);
    }

    public String getValue() {
        return value;
    }
}
