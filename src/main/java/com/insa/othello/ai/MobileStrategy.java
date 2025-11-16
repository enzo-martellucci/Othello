package com.insa.othello.ai;

import com.insa.othello.model.Position;

import java.util.List;

/**
 * Stratégie de mobilité pour Othello.
 * Évalue un coup par son impact sur la flexibilité du joueur.
 *
 * Favorise les coups qui retournent peu de pions (limite le nombre de pions adverses)
 * et qui jouent sur les bords plutôt que le centre.
 */
public class MobileStrategy implements EvaluationStrategy {

    @Override
    public int evaluate(Position position, List<Position> flippedPieces) {
        // Score inverse basé sur les pions retournés
        // Moins on retourne, mieux c'est (1 point par pion NON retourné)
        // Max flipped = environ 8-10 en début de partie
        int score = 100 - flippedPieces.size() * 10;

        // Bonus pour jouer sur les bords (mais pas les coins, c'est les coins)
        int row = position.r();
        int col = position.c();
        boolean isEdge = (row == 0 || row == 7 || col == 0 || col == 7);
        boolean isCorner = (row == 0 || row == 7) && (col == 0 || col == 7);

        if (isCorner) {
            score += 50; // Les coins sont toujours bons
        } else if (isEdge) {
            score += 10; // Les bords sans danger sont bons
        }

        return Math.max(score, 1); // Minimum 1 pour éviter les valeurs négatives
    }
}