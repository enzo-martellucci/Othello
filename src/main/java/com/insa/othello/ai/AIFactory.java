package com.insa.othello.ai;

import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;

/**
 * Fabrique pour créer les instances d'IA selon le type et la stratégie sélectionnés.
 */
public class AIFactory {
    /**
     * Crée une instance d'IA appropriée.
     *
     * @param playerType Type de joueur (HUMAN, MIN_MAX, MIN_MAX_ALPHA_BETA, NEGA_MAX)
     * @param strategyType Type de stratégie d'évaluation
     * @param maxDepth Profondeur maximale de recherche
     * @param playerColor Couleur du joueur
     * @return Instance d'IA, ou null si c'est un joueur humain
     */
    public static AI createAI(PlayerType playerType, StrategyType strategyType, int maxDepth, Cell playerColor) {
        if (playerType == PlayerType.HUMAN) {
            return null;
        }

        EvaluationStrategy strategy = StrategyFactory.createStrategy(strategyType);

        return switch (playerType) {
            case MIN_MAX -> new MinMaxAI(strategy, maxDepth, playerColor);
            case MIN_MAX_ALPHA_BETA -> new AlphaBetaAI(strategy, maxDepth, playerColor);
            case NEGA_MAX -> new MinMaxAI(strategy, maxDepth, playerColor); // TODO: Implémenter NegaMax
            case HUMAN -> null;
        };
    }
}
