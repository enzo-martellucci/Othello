package com.insa.othello.ai;

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
}