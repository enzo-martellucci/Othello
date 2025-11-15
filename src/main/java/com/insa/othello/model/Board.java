package com.insa.othello.model;

import com.insa.othello.constant.Cell;

import static com.insa.othello.constant.Configuration.SIZE;

/**
 * Représente le plateau de jeu Othello (8x8)
 * Les disques sont représentés par l'énumération PieceColor
 */
public class Board {
    private final Cell[][] board;

    public Board() {
        this.board = new Cell[SIZE][SIZE];
        this.init();
    }

    public void init() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = Cell.EMPTY;

        board[3][3] = Cell.WHITE;
        board[3][4] = Cell.BLACK;
        board[4][3] = Cell.BLACK;
        board[4][4] = Cell.WHITE;
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public void play(int row, int col, Cell color) {
        board[row][col] = color;
    }

    public boolean isEmpty(int row, int col) {
        return board[row][col] == Cell.EMPTY;
    }

    public Cell[][] getBoardCopy() {
        Cell[][] copy = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    public int countPieces(Cell color) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == color) {
                    count++;
                }
            }
        }
        return count;
    }
}
