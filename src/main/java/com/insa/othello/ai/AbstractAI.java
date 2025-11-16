package com.insa.othello.ai;

import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Classe abstraite pour les implémentations d'IA.
 * Gère le threading et l'async via JavaFX Service.
 * Les sous-classes doivent implémenter selectMove() pour la logique spécifique.
 */
public abstract class AbstractAI extends Service<Position> implements AI {
    protected Board board;
    protected Map<Position, List<Position>> mapMove;

    public AbstractAI() {
        this.setOnFailed(event -> {
            Throwable exception = getException();
            System.err.println("Erreur dans l'IA: " + exception.getMessage());
            exception.printStackTrace();
        });
    }

    @Override
    public void search(Board board, Map<Position, List<Position>> mapMove, Consumer<Position> callback) {
        this.board = board;
        this.mapMove = mapMove;
        this.setOnSucceeded(_ -> callback.accept(this.getValue()));

        if (this.getState() == State.READY) {
            this.start();
        } else {
            this.restart();
        }
    }

    @Override
    protected Task<Position> createTask() {
        final Board currentBoard = this.board;
        final Map<Position, List<Position>> currentMapMove = this.mapMove;

        return new Task<>() {
            @Override
            protected Position call() throws Exception {
                if (isCancelled()) {
                    return null;
                }

                return selectMove(currentBoard, currentMapMove);
            }
        };
    }

    /**
     * Sélectionne le meilleur coup pour la position actuelle.
     * À implémenter par les sous-classes.
     *
     * @param board Le plateau actuel
     * @param mapMove Map des coups valides (position -> pions à retourner)
     * @return La position du coup sélectionné
     */
    protected abstract Position selectMove(Board board, Map<Position, List<Position>> mapMove);

    /**
     * Crée une instance d'IA appropriée selon le type et la stratégie sélectionnés.
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