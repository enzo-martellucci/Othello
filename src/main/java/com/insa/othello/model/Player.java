package com.insa.othello.model;

import com.insa.othello.ai.AI;
import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;

public class Player {
    private final Cell color;
    private final PlayerType type;
    private final AI ai;
    private int score;

    public Player(Cell color, PlayerType type, StrategyType strategy, int score) {
        this.color = color;
        this.type = type;
        this.ai = null;
        this.score = score;
    }

    public Cell getColor() {
        return color;
    }

    public PlayerType getType() {
        return type;
    }

    public AI getAi() {
        return ai;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
