package com.insa.othello.model;

/**
 * Représente le résultat final d'une partie
 */
public class GameResult {
    private String blackPlayerName;
    private String whitePlayerName;
    private int blackScore;
    private int whiteScore;
    private String winner;

    public GameResult(String blackPlayerName, String whitePlayerName,
                      int blackScore, int whiteScore, String winner) {
        this.blackPlayerName = blackPlayerName;
        this.whitePlayerName = whitePlayerName;
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
        this.winner = winner;
    }

    public String getBlackPlayerName() {
        return blackPlayerName;
    }

    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                blackPlayerName + "=" + blackScore + ", " +
                whitePlayerName + "=" + whiteScore + ", " +
                "winner=" + winner +
                '}';
    }
}
