package com.insa.othello.ai;

import com.insa.othello.constant.Configuration;
import com.insa.othello.model.Position;

import java.util.List;

/**
 * Stratégie positionnelle pour Othello.
 * Évalue la qualité d'un coup basée sur la position sur le plateau.
 *
 * Les coins sont très précieux (+ valeur importante)
 * Les bords proches des coins sont dangereux (- valeur)
 * Le centre est moins intéressant
 */
public class PositionalStrategy implements EvaluationStrategy {
    // Table de valeurs positionnelles (8x8)
    // Les coins valent 100, les positions à éviter près des coins valent -20, etc.
    private static final int[][] BOARD_VALUE = {
            {100, -20, 10,  5,  5, 10, -20, 100},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            { 10,  -2,  1,  1,  1,  1,  -2,  10},
            {  5,  -2,  1,  1,  1,  1,  -2,   5},
            {  5,  -2,  1,  1,  1,  1,  -2,   5},
            { 10,  -2,  1,  1,  1,  1,  -2,  10},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {100, -20, 10,  5,  5, 10, -20, 100}
    };

    @Override
    public int evaluate(Position position, List<Position> flippedPieces) {
        int score = 0;

        // Score de la position du coup
        score += BOARD_VALUE[position.r()][position.c()];

        // Score basé sur les pions retournés
        for (Position flipped : flippedPieces) {
            score += BOARD_VALUE[flipped.r()][flipped.c()];
        }

        return score;
    }
}