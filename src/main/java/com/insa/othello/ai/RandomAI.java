package com.insa.othello.ai;

import com.insa.othello.model.Board;
import com.insa.othello.model.Move;
import com.insa.othello.constant.Cell;
import java.util.List;
import java.util.Random;

/**
 * Implémentation IA basique qui joue des coups aléatoires
 * Utilisée pour les tests et comme base pour les stratégies plus avancées
 */
public class RandomAI implements AI {
    private Random random;

    public RandomAI() {
        this.random = new Random();
    }

    /**
     * Choisit un coup aléatoire parmi les coups valides disponibles
     */
    @Override
    public Move chooseMove(Board board, Cell player, List<Move> availableMoves) {
        if (availableMoves == null || availableMoves.isEmpty()) {
            throw new IllegalArgumentException("Aucun coup disponible");
        }

        // Choisir un coup aléatoire
        return availableMoves.get(random.nextInt(availableMoves.size()));
    }

    /**
     * Retourne le nom de la stratégie
     */
    @Override
    public String getStrategyName() {
        return "Random AI";
    }
}
