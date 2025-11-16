package com.insa.othello.ai.strategy;

import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;

public class MixteStrategy implements EvaluationStrategy {
    private final PositionalStrategy positional = new PositionalStrategy();
    private final AbsoluteStrategy absolute = new AbsoluteStrategy();
    private final MobileStrategy mobile = new MobileStrategy();

    @Override
    public int evaluate(Board board) {
        int nbMove = board.getNbMove();

        if (nbMove <= 25)
            return positional.evaluate(board);
        else if (nbMove >= 48)
            return absolute.evaluate(board);
        else
            return mobile.evaluate(board);
    }
}
