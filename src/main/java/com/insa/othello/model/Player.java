package com.insa.othello.model;

import com.insa.othello.ai.AI;
import com.insa.othello.ai.AIFactory;
import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.insa.othello.constant.PlayerType.HUMAN;

public class Player {
    private final Cell color;
    private final PlayerType type;
    private final AI ai;
    private int score;

    public Player(Cell color, PlayerType type, StrategyType strategy, int maxDepth, int score) {
        this.color = color;
        this.type = type;
        this.ai = AIFactory.createAI(type, strategy, maxDepth, color);
        this.score = score;
    }

    public Cell getColor() {
        return this.color;
    }

    public PlayerType getType() {
        return this.type;
    }

    public boolean isAI() {
        return this.ai != null;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void playAI(Board board, Map<Position, List<Position>> mapMove, Consumer<Position> callback) {
        this.ai.search(board, mapMove, callback);
    }
}
