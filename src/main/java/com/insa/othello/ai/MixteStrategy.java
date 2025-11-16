package com.insa.othello.ai;

import com.insa.othello.model.Position;

import java.util.List;

/**
 * Stratégie mixte pour Othello.
 * Combine les avantages des stratégies Positional, Absolute et Mobile.
 *
 * - 50% Positional (importance de la position sur le plateau)
 * - 30% Absolute (nombre de pions captés)
 * - 20% Mobile (impact sur la flexibilité)
 */
public class MixteStrategy implements EvaluationStrategy {
    private final PositionalStrategy positional = new PositionalStrategy();
    private final AbsoluteStrategy absolute = new AbsoluteStrategy();
    private final MobileStrategy mobile = new MobileStrategy();

    @Override
    public int evaluate(Position position, List<Position> flippedPieces) {
        int positionalScore = positional.evaluate(position, flippedPieces);
        int absoluteScore = absolute.evaluate(position, flippedPieces);
        int mobileScore = mobile.evaluate(position, flippedPieces);

        // Pondération : 50% position, 30% absolu, 20% mobilité
        return (int) (positionalScore * 0.5 + absoluteScore * 0.3 + mobileScore * 0.2);
    }
}
