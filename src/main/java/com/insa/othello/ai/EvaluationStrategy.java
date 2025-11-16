package com.insa.othello.ai;

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
}