package com.insa.othello.ai.strategy;

import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.Board;

public interface EvaluationStrategy {
    static EvaluationStrategy create(StrategyType type) {
        return switch (type) {
            case POSITIONAL -> new PositionalStrategy();
            case ABSOLUTE -> new AbsoluteStrategy();
            case MOBILE -> new MobileStrategy();
            case MIXTE -> new MixteStrategy();
        };
    }

    int evaluate(Board board);
}
