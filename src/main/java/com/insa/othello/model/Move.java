package com.insa.othello.model;

import java.util.List;

/**
 * Représente un coup dans le jeu Othello
 * Contient la position du coup et les disques qui seront retournés
 */
public class Move {
    private int row;
    private int col;
    private List<int[]> flippedPieces; // Liste des positions des disques retournés

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Move(int row, int col, List<int[]> flippedPieces) {
        this.row = row;
        this.col = col;
        this.flippedPieces = flippedPieces;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<int[]> getFlippedPieces() {
        return flippedPieces;
    }

    public void setFlippedPieces(List<int[]> flippedPieces) {
        this.flippedPieces = flippedPieces;
    }

    @Override
    public String toString() {
        return "Move{" +
                "row=" + row +
                ", col=" + col +
                ", flipped=" + (flippedPieces != null ? flippedPieces.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return row == move.row && col == move.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}
