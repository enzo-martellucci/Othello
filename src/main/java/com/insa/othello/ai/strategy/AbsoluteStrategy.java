package com.insa.othello.ai.strategy;

import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;

public class AbsoluteStrategy implements EvaluationStrategy {

    @Override
    public int evaluate(Board board) {
        Cell[][] grid = board.getGrid();
        int score = 0;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (grid[i][j] == Cell.BLACK)
                    score++;
                else if (grid[i][j] == Cell.WHITE)
                    score--;

        return score;
    }
}
 