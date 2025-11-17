package com.insa.othello.ai;

import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Position;

import java.util.function.Consumer;

public interface AI {
    void search(Board board, Cell color, Consumer<Position> callback);
}
