package com.insa.othello.ai;

import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface AI {
    void search(Board board, Map<Position, List<Position>> mapMove, Consumer<Position> callback);
}
