package com.insa.othello.ai.algorithm;

import com.insa.othello.ai.AbstractAI;
import com.insa.othello.ai.strategy.EvaluationStrategy;
import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

import java.util.List;
import java.util.Map;

/**
 * Implémentation de l'algorithme MinMax pour Othello.
 * Explore l'arbre de jeu jusqu'à une profondeur donnée et retourne le meilleur coup.
 */
public class MinMaxAI extends AbstractAI {
    private final EvaluationStrategy strategy;
    private final int maxDepth;
    private final Cell playerColor;

    public MinMaxAI(EvaluationStrategy strategy, int maxDepth, Cell playerColor) {
        this.strategy = strategy;
        this.maxDepth = maxDepth;
        this.playerColor = playerColor;
    }

    @Override
    protected Position selectMove(Board board, Map<Position, List<Position>> mapMove) {
        if (mapMove.isEmpty()) {
            throw new IllegalStateException("Aucun coup possible");
        }

        // Trouver le meilleur coup en utilisant MinMax
        Position bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (Map.Entry<Position, List<Position>> move : mapMove.entrySet()) {
            Position pos = move.getKey();

            // Créer un clone du board et faire le coup
            Board clonedBoard = cloneBoard(board);
            clonedBoard.play(pos.r(), pos.c(), playerColor);

            // Évaluer les mouvements suivants de l'adversaire
            int score = minmax(clonedBoard, playerColor.opponent(), 1, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = pos;
            }
        }

        return bestMove;
    }

    /**
     * Algorithme MinMax récursif.
     *
     * @param board État du plateau
     * @param currentColor Couleur du joueur actuel
     * @param depth Profondeur actuelle
     * @param isMaximizing True si on maximise, false si on minimise
     * @return Score MinMax
     */
    private int minmax(Board board, Cell currentColor, int depth, boolean isMaximizing) {
        // Cas de base : profondeur maximale atteinte
        if (depth >= maxDepth) {
            return evaluateBoard(board);
        }

        // Mettre à jour les coups possibles pour la couleur actuelle
        board.updatePossiblePlay(currentColor);
        Map<Position, List<Position>> possibleMoves = board.getMapMove();

        // Si aucun coup possible, l'adversaire rejoue ou c'est la fin
        if (possibleMoves.isEmpty()) {
            // Essayer avec la couleur opposée
            Cell otherColor = currentColor.opponent();
            board.updatePossiblePlay(otherColor);
            if (board.getMapMove().isEmpty()) {
                // Fin du jeu
                return evaluateBoard(board);
            }
            // L'autre joueur a des coups
            return minmax(board, otherColor, depth, isMaximizing);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Map.Entry<Position, List<Position>> move : possibleMoves.entrySet()) {
                Board cloned = cloneBoard(board);
                cloned.play(move.getKey().r(), move.getKey().c(), currentColor);
                int eval = minmax(cloned, currentColor.opponent(), depth + 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Map.Entry<Position, List<Position>> move : possibleMoves.entrySet()) {
                Board cloned = cloneBoard(board);
                cloned.play(move.getKey().r(), move.getKey().c(), currentColor);
                int eval = minmax(cloned, currentColor.opponent(), depth + 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    /**
     * Évalue le plateau pour le joueur de cette IA.
     */
    private int evaluateBoard(Board board) {
        int score = 0;
        board.updatePossiblePlay(playerColor);

        for (Map.Entry<Position, List<Position>> move : board.getMapMove().entrySet()) {
            score += strategy.evaluate(move.getKey(), move.getValue());
        }

        return score;
    }

    /**
     * Clone un board donné.
     */
    private Board cloneBoard(Board original) {
        Board cloned = new Board();
        Cell[][] originalGrid = original.getGrid();
        Cell[][] clonedGrid = cloned.getGrid();

        for (int i = 0; i < originalGrid.length; i++) {
            System.arraycopy(originalGrid[i], 0, clonedGrid[i], 0, originalGrid[i].length);
        }

        // Copier les coups possibles
        Map<Position, List<Position>> originalMoves = original.getMapMove();
        Map<Position, List<Position>> clonedMoves = cloned.getMapMove();
        for (Map.Entry<Position, List<Position>> entry : originalMoves.entrySet()) {
            clonedMoves.put(entry.getKey(), List.copyOf(entry.getValue()));
        }

        return cloned;
    }
}
