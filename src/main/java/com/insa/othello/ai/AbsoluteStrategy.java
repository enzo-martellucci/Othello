package com.insa.othello.ai;

import com.insa.othello.model.Position;

import java.util.List;

/**
 * Stratégie absolue pour Othello.
 * Évalue un coup simplement par le nombre de pions retournés.
 *
 * C'est la stratégie la plus simple : maximiser le nombre de pions captés.
 */
public class AbsoluteStrategy implements EvaluationStrategy {

    @Override
    public int evaluate(Position position, List<Position> flippedPieces) {
        // Score = nombre de pions retournés + 1 pour le pion placé
        return flippedPieces.size() + 1;
    }
}