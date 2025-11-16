package com.insa.othello.ai.strategy;

import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;

public class PositionalStrategy implements EvaluationStrategy {
    private static final int[][] BOARD_VALUE = {
            {100, -20, 10, 5, 5, 10, -20, 100},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {10, -2, 1, 1, 1, 1, -2, 10},
            {5, -2, 1, 1, 1, 1, -2, 5},
            {5, -2, 1, 1, 1, 1, -2, 5},
            {10, -2, 1, 1, 1, 1, -2, 10},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {100, -20, 10, 5, 5, 10, -20, 100}
    };

    @Override
    public int evaluate(Board board) {
        Cell[][] grid = board.getGrid();
        int score = 0;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (grid[i][j] == Cell.BLACK)
                    score += BOARD_VALUE[i][j];
                else if (grid[i][j] == Cell.WHITE)
                    score -= BOARD_VALUE[i][j];

        return score;
    }
}
