package com.insa.othello.ai;

import com.insa.othello.model.Position;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class RandomAI extends Service<Position> implements AI {
    private final Random random;

    private Map<Position, List<Position>> mapMove;
    private Consumer<Position> callback;

    public RandomAI() {
        this.random = new Random();

        this.setOnFailed(event -> {
            Throwable exception = getException();
            System.err.println("Erreur dans l'IA: " + exception.getMessage());
            exception.printStackTrace();
        });
    }

    public void search(Map<Position, List<Position>> mapMove, Consumer<Position> callback) {
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
        final Map<Position, List<Position>> currentMapMove = this.mapMove;

        return new Task<>() {
            @Override
            protected Position call() throws Exception {
                if (isCancelled()) {
                    return null;
                }

                List<Position> lstMove = new ArrayList<>(currentMapMove.keySet());

                if (lstMove.isEmpty()) {
                    throw new IllegalStateException("Aucun coup possible");
                }

                Position move = lstMove.get(random.nextInt(lstMove.size()));

                // Simulation de réflexion
                Thread.sleep(random.nextInt(1000));

                return move;
            }
        };
    }

    private Position compute() {
        List<Position> lstMove = new ArrayList<>(this.mapMove.keySet());
        Position move = lstMove.get(this.random.nextInt(lstMove.size()));

        try {
            Thread.sleep(this.random.nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return move;
    }
}
