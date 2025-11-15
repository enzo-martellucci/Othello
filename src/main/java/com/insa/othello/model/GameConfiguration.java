package com.insa.othello.model;

import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;

public record GameConfiguration(
        PlayerType blackPlayer,
        StrategyType blackStrategy,
        int blackLimitMS,
        int blackLimitStep,
        PlayerType whitePlayer,
        StrategyType whiteStrategy,
        int whiteLimitMS,
        int whiteLimitStep
) {
}
