package com.insa.othello.ai;

import com.insa.othello.model.Board;
import com.insa.othello.model.Move;
import com.insa.othello.constant.Cell;

import java.util.List;

/**
 * Interface pour les stratégies IA
 * Toute stratégie IA doit implémenter cette interface
 * Cela permet de substituer facilement différentes IA (MinMax, AlphaBeta, NegaMax, etc.)
 */
public interface AI {
    /**
     * Choisit le meilleur coup pour le joueur actuel
     * @param board l'état actuel du plateau
     * @param player la couleur du joueur courant
     * @param availableMoves la liste des coups disponibles
     * @return le meilleur coup à jouer
     */
    Move chooseMove(Board board, Cell player, List<Move> availableMoves);

    /**
     * Retourne le nom de la stratégie
     */
    String getStrategyName();
}
