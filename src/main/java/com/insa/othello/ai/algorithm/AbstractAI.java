package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.AI;
import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;

import static com.insa.othello.constant.PlayerType.HUMAN;

public abstract class AbstractAI extends Service<Position> implements AI
{
    public static final Cell MAXIMIZER = Cell.BLACK;
    public static final Cell MINIMIZER = Cell.WHITE;

    protected Board board;
    protected Cell color;

    public AbstractAI() {
        this.setOnFailed(_ -> {
            Throwable exception = AbstractAI.this.getException();
            System.err.println("AI error : " + exception.getMessage());
            exception.printStackTrace();
        });
    }

    public static AI createAI(PlayerType playerType, StrategyType strategyType, int limitMS, int maxDepth, Cell playerColor) {
        EvaluationStrategy strategy = playerType == HUMAN ? null : EvaluationStrategy.create(strategyType);
        return switch (playerType) {
            case MIN_MAX -> new MinMaxAI(strategy, limitMS, maxDepth, playerColor);
            case MIN_MAX_ALPHA_BETA -> new MinMaxAI(strategy, limitMS, maxDepth, playerColor); // TODO: Implémenter AlphaBeta
            case NEGA_MAX -> new MinMaxAI(strategy, limitMS, maxDepth, playerColor); // TODO: Implémenter NegaMax
            case HUMAN -> null;
        };
    }

    @Override
    public void search(Board board, Cell color, Consumer<Position> callback) {
        this.board = board;
        this.color = color;
        this.setOnSucceeded(_ -> callback.accept(this.getValue()));

        if (this.getState() == State.READY)
            this.start();
        else
            this.restart();
    }

    @Override
    protected Task<Position> createTask() {
        final Board currentBoard = this.board;
        final Cell currentColor = this.color;

        return new Task<>() {
            @Override
            protected Position call() {
                if (isCancelled()) {
                    return null;
                }

                return AbstractAI.this.selectMove(currentBoard, currentColor);
            }
        };
    }

    protected abstract Position selectMove(Board board, Cell color);
}