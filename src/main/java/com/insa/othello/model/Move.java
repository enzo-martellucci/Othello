package com.insa.othello.model;

import java.util.List;

public record Move(Position position, List<Position> flippedPieces) {
}
