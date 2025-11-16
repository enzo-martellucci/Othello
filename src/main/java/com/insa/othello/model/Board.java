package com.insa.othello.model;

import com.insa.othello.constant.Cell;
import com.insa.othello.constant.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.insa.othello.constant.Cell.EMPTY;
import static com.insa.othello.constant.Configuration.SIZE;

public class Board {
    private final Cell[][] grid;
    private final Map<Position, List<Position>> mapMove;

    public Board() {
        this.grid = new Cell[SIZE][SIZE];
        this.mapMove = new HashMap<>();
    }

    public void init(Cell color) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                grid[i][j] = EMPTY;

        grid[3][3] = Cell.WHITE;
        grid[3][4] = Cell.BLACK;
        grid[4][3] = Cell.BLACK;
        grid[4][4] = Cell.WHITE;

        this.mapMove.clear();
    }

    public Cell[][] getGrid() {
        return this.grid;
    }

    public Cell getCell(int row, int col) {
        return this.grid[row][col];
    }

    public boolean isPlayable() {
        return !this.mapMove.isEmpty();
    }

    public void updatePossiblePlay(Cell color) {
        for (Position p : this.mapMove.keySet())
            this.grid[p.r()][p.c()] = EMPTY;
        this.mapMove.clear();

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] != EMPTY)
                    continue;

                Position position = new Position(r, c);
                List<Position> flippedPieces = this.getFlippedPieces(position, color);
                if (!flippedPieces.isEmpty()) {
                    this.grid[r][c] = Cell.PLAYABLE;
                    this.mapMove.put(position, flippedPieces);
                }
            }
        }
    }

    private List<Position> getFlippedPieces(Position position, Cell color) {
        List<Position> lstFlipped = new ArrayList<>();
        Cell opponent = color.opponent();

        int row = position.r(), col = position.c();
        for (Direction d : Direction.values()) {
            List<Position> lstFlippedDirection = new ArrayList<>();

            int r = row + d.getOffsetR();
            int c = col + d.getOffsetC();
            while (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                Cell current = grid[r][c];
                if (current == EMPTY || current == Cell.PLAYABLE) {
                    break;
                } else if (current == opponent) {
                    lstFlippedDirection.add(new Position(r, c));
                } else if (current == color) {
                    lstFlipped.addAll(lstFlippedDirection);
                    break;
                }

                r += d.getOffsetR();
                c += d.getOffsetC();
            }
        }

        return lstFlipped;
    }

    public int play(int row, int col, Cell color) {
        this.grid[row][col] = color;

        Position position = new Position(row, col);
        int scored = this.mapMove.get(position).size();
        this.mapMove.get(position).forEach(p -> this.grid[p.r()][p.c()] = color);
        this.mapMove.remove(position);

        return scored;
    }
}
