package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

public class MinMaxAI extends AbstractAI
{
    private final EvaluationStrategy strategy;
    private final int limitMS;
    private final int maxDepth;
    private final Cell playerColor;

    public MinMaxAI(EvaluationStrategy strategy, int limitMS, int maxDepth, Cell playerColor)
    {
        this.strategy = strategy;
        this.limitMS = limitMS;
        this.maxDepth = maxDepth;
        this.playerColor = playerColor;
    }

    @Override
    protected Position selectMove(Board board, Cell color)
    {
        return null;
    }
}
