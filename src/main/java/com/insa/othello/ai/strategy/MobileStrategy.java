package com.insa.othello.ai.strategy;

import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

public class MobileStrategy implements EvaluationStrategy {
    private static final Position[] CORNER = new Position[]{new Position(0, 0), new Position(0, 7), new Position(7, 0), new Position(7, 7)};

    @Override
    public int evaluate(Board board) {
        int blackMoves = board.getMapMove().size();
        board.updatePossiblePlay(Cell.WHITE);
        int whiteMoves = board.getMapMove().size();
        board.updatePossiblePlay(Cell.BLACK);
        int mobilityScore = blackMoves - whiteMoves;

        Cell[][] grid = board.getGrid();
        int cornerScore = 0;
        for (Position p : CORNER)
            if (grid[p.r()][p.c()] == Cell.BLACK)
                cornerScore += 50;
            else if (grid[p.r()][p.c()] == Cell.WHITE)
                cornerScore -= 50;

        return mobilityScore * 8 + cornerScore;
    }
}
