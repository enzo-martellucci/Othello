package com.insa.othello.model;

import com.insa.othello.ai.AI;
import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;

import static com.insa.othello.constant.PlayerType.HUMAN;

public class Player {
    private final Cell color;
    private final PlayerType type;
    private final AI ai;

    public Player(Cell color, PlayerType type, StrategyType strategy) {
        this.color = color;
        this.type = type;
        this.ai = null;
    }

    public Cell getColor() {
        return color;
    }

    public PlayerType getType() {
        return type;
    }

    public boolean isHuman() {
        return this.type == HUMAN;
    }

    public boolean isAI() {
        return !this.isHuman();
    }
}
