package com.insa.othello.ai;

import com.insa.othello.constant.StrategyType;

/**
 * Fabrique pour créer les stratégies d'évaluation selon le type sélectionné.
 */
public class StrategyFactory {
    public static EvaluationStrategy createStrategy(StrategyType type) {
        return switch (type) {
            case POSITIONAL -> new PositionalStrategy();
            case ABSOLUTE -> new AbsoluteStrategy();
            case MOBILE -> new MobileStrategy();
            case MIXTE -> new MixteStrategy();
        };
    }
}
