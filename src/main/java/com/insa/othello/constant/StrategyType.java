package com.insa.othello.constant;

public enum StrategyType {
    POSITIONAL("Positional"),
    ABSOLUTE("Absolute"),
    MOBILE("Mobile"),
    MIXTE("Mixte");

    private final String value;

    StrategyType(String value) {
        this.value = value;
    }

    public static StrategyType fromValue(String value) {
        for (StrategyType type : StrategyType.values())
            if (type.value.equals(value))
                return type;
        throw new IllegalArgumentException("Unknown StrategyType value: " + value);
    }

    public String getValue() {
        return value;
    }
}
