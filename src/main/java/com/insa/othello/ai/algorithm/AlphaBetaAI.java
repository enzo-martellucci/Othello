package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

public class AlphaBetaAI extends AbstractAI {
    private final EvaluationStrategy strategy;
    private final int limitMS;
    private final int maxDepth;
    private final Cell playerColor;
    private long startTime;

    public AlphaBetaAI(EvaluationStrategy strategy, int limitMS, int maxDepth, Cell playerColor) {
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
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Position move : validMoves) {
            if (isTimeUp())
                break;

            Board newBoard = board.copy();
            newBoard.play(move.r(), move.c(), color);
            Cell nextColor = color.opponent();
            newBoard.updatePossiblePlay(nextColor);

            int score = alphabeta(newBoard, nextColor, 1, alpha, beta, !isMaximizing);

            if (isMaximizing) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestScore);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, bestScore);
            }
        }

        return bestMove;
    }

    private int alphabeta(Board board, Cell color, int depth, int alpha, int beta, boolean isMaximizing) {
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
            return alphabeta(nextBoard, nextColor, depth, alpha, beta, !isMaximizing);
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (Position move : validMoves) {
                if (isTimeUp())
                    break;

                Board newBoard = board.copy();
                newBoard.play(move.r(), move.c(), color);
                Cell nextColor = color.opponent();
                newBoard.updatePossiblePlay(nextColor);

                int score = alphabeta(newBoard, nextColor, depth + 1, alpha, beta, false);
                maxScore = Math.max(maxScore, score);

                alpha = Math.max(alpha, score);
                if (beta <= alpha)
                    break; // Coupe Beta
            }

            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (Position move : validMoves) {
                if (isTimeUp())
                    break;

                Board newBoard = board.copy();
                newBoard.play(move.r(), move.c(), color);
                Cell nextColor = color.opponent();
                newBoard.updatePossiblePlay(nextColor);

                int score = alphabeta(newBoard, nextColor, depth + 1, alpha, beta, true);
                minScore = Math.min(minScore, score);

                beta = Math.min(beta, score);
                if (beta <= alpha)
                    break;
            }

            return minScore;
        }
    }

    private boolean isTimeUp() {
        return (System.currentTimeMillis() - this.startTime) >= this.limitMS;
    }
}
