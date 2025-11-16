package com.insa.othello.ai.strategy;

import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.Position;

import java.util.List;

/**
 * Interface pour les stratégies d'évaluation de coups.
 * Chaque stratégie évalue la qualité d'un coup possible.
 */
public interface EvaluationStrategy {
    /**
     * Évalue un coup possible.
     *
     * @param position La position où jouer
     * @param flippedPieces Les pions qui seraient retournés par ce coup
     * @return Score d'évaluation du coup
     */
    int evaluate(Position position, List<Position> flippedPieces);

    /**
     * Crée une stratégie d'évaluation selon le type sélectionné.
     *
     * @param type Le type de stratégie à créer
     * @return Une instance de la stratégie demandée
     */
    static EvaluationStrategy create(StrategyType type) {
        return switch (type) {
            case POSITIONAL -> new PositionalStrategy();
            case ABSOLUTE -> new AbsoluteStrategy();
            case MOBILE -> new MobileStrategy();
            case MIXTE -> new MixteStrategy();
        };
    }
}
