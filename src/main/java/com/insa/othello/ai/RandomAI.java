package com.insa.othello.ai;

import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * IA simple qui sélectionne un coup aléatoire parmi les coups valides.
 */
public class RandomAI extends AbstractAI {
    private final Random random;

    public RandomAI() {
        this.random = new Random();
    }

    @Override
    protected Position selectMove(Board board, Map<Position, List<Position>> mapMove) {
        List<Position> lstMove = new ArrayList<>(mapMove.keySet());

        if (lstMove.isEmpty()) {
            throw new IllegalStateException("Aucun coup possible");
        }

        Position move = lstMove.get(random.nextInt(lstMove.size()));

        // Simulation de réflexion
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return move;
    }
}
