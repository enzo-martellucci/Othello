package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

public class MinMaxAI extends AbstractAI {
    private final EvaluationStrategy strategy;
    private final int limitMS;
    private final int maxDepth;
    private final Cell playerColor;
    private long startTime;

    public MinMaxAI(EvaluationStrategy strategy, int limitMS, int maxDepth, Cell playerColor) {
        this.strategy = strategy;
        this.limitMS = limitMS;
        this.maxDepth = maxDepth;
        this.playerColor = playerColor;
    }

    @Override
    protected Position selectMove(Board board, Cell color) {
        this.startTime = System.currentTimeMillis();

        var validMoves = board.getMapMove().keySet();
        if (validMoves.isEmpty())
            return null;

        boolean isMaximizing = (color == MAXIMIZER);
        Position bestMove = null;
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Position move : validMoves) {
            if (isTimeUp())
                break;

            Board newBoard = board.copy();
            newBoard.play(move.r(), move.c(), color);
            Cell nextColor = color.opponent();
            newBoard.updatePossiblePlay(nextColor);

            int score = minmax(newBoard, nextColor, 1, !isMaximizing);
            if (isMaximizing) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }

    private int minmax(Board board, Cell color, int depth, boolean isMaximizing) {
        if (isTimeUp())
            return this.strategy.evaluate(board);

        if (depth >= this.maxDepth)
            return this.strategy.evaluate(board);

        var validMoves = board.getMapMove().keySet();
        if (validMoves.isEmpty()) {
            Cell nextColor = color.opponent();
            Board nextBoard = board.copy();
            nextBoard.updatePossiblePlay(nextColor);

            if (nextBoard.getMapMove().isEmpty())
                return this.strategy.evaluate(board);
            return minmax(nextBoard, nextColor, depth, !isMaximizing);
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Position move : validMoves) {
            if (isTimeUp())
                break;

            Board newBoard = board.copy();
            newBoard.play(move.r(), move.c(), color);
            Cell nextColor = color.opponent();
            newBoard.updatePossiblePlay(nextColor);

            int score = minmax(newBoard, nextColor, depth + 1, !isMaximizing);
            if (isMaximizing)
                bestScore = Math.max(bestScore, score);
            else
                bestScore = Math.min(bestScore, score);
        }

        return bestScore;
    }

    private boolean isTimeUp() {
        return (System.currentTimeMillis() - this.startTime) >= this.limitMS;
    }
}
