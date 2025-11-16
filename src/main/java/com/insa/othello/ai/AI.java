package com.insa.othello.ai;

import com.insa.othello.constant.Cell;
import com.insa.othello.model.Board;
import com.insa.othello.model.Move;

import java.util.List;

public interface AI {
    Move chooseMove(Board board, Cell player, List<Move> availableMoves);
}
