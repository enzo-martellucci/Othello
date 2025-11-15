package com.insa.othello.model;

import com.insa.othello.constant.Cell;

import java.util.ArrayList;
import java.util.List;

import static com.insa.othello.constant.Cell.BLACK;
import static com.insa.othello.constant.Cell.WHITE;
import static com.insa.othello.constant.Configuration.SIZE;

public class Game {
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    private final Board board;
    private final List<GameObserver> observers;
    private final List<Move> gameHistory;
    private Player activePlayer;
    private Player passivePlayer;
    private Cell currentPlayerColor;
    private boolean isGameOver;

    public Game(GameConfiguration configuration) {
        this.board = new Board();

        this.activePlayer = new Player(BLACK, configuration.blackPlayer(), configuration.blackStrategy());
        this.passivePlayer = new Player(WHITE, configuration.whitePlayer(), configuration.whiteStrategy());

        this.observers = new ArrayList<>();
        this.gameHistory = new ArrayList<>();
    }

    public Game(Player activePlayer, Player passivePlayer) {
        this.board = new Board();

        this.activePlayer = activePlayer;
        this.passivePlayer = passivePlayer;

        this.currentPlayerColor = BLACK;
        this.isGameOver = false;
        this.gameHistory = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void init() {
        this.board.init();
        if (this.activePlayer.getColor() == WHITE)
            this.changePlayer();
        this.isGameOver = false;
        this.gameHistory.clear();
        this.notifyObservers();
    }

    private void changePlayer() {
        Player tmp = this.activePlayer;
        this.activePlayer = this.passivePlayer;
        this.passivePlayer = tmp;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onGameStateChanged();
        }
    }

    public List<Move> getValidMoves() {
        List<Move> validMoves = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.isEmpty(row, col)) {
                    List<int[]> flipped = getFlippedPieces(row, col, currentPlayerColor);
                    if (!flipped.isEmpty()) {
                        validMoves.add(new Move(row, col, flipped));
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * Retourne les disques qui seraient retournés si on joue un coup à (row, col)
     */
    private List<int[]> getFlippedPieces(int row, int col, Cell player) {
        List<int[]> flipped = new ArrayList<>();
        Cell opponent = player.opponent();

        // Pour chaque direction
        for (int[] direction : DIRECTIONS) {
            List<int[]> temp = new ArrayList<>();
            int r = row + direction[0];
            int c = col + direction[1];

            // Parcourir la direction jusqu'à trouver un disque allié ou vide
            while (board.getCell(r, c) == opponent) {
                temp.add(new int[]{r, c});
                r += direction[0];
                c += direction[1];
            }

            // Si on a trouvé un disque allié à la fin, on retourne tous les disques adverses
            if (board.getCell(r, c) == player) {
                flipped.addAll(temp);
            }
        }

        return flipped;
    }

    /**
     * Joue un coup pour le joueur courant
     */
    public void playMove(Move move) {
        if (!isValidMove(move)) {
            throw new IllegalArgumentException("Invalid move: " + move);
        }

        // Placer le disque du joueur
        board.play(move.getRow(), move.getCol(), currentPlayerColor);

        // Retourner les disques adverses
        if (move.getFlippedPieces() != null) {
            for (int[] pos : move.getFlippedPieces()) {
                board.play(pos[0], pos[1], currentPlayerColor);
            }
        }

        // Ajouter le coup à l'historique
        gameHistory.add(move);

        // Changer de joueur
        switchPlayer();

        // Vérifier la fin du jeu
        checkGameOver();

        // Notifier les observateurs
        notifyObservers();
    }

    /**
     * Vérifie si un coup est valide
     */
    public boolean isValidMove(Move move) {
        List<Move> validMoves = getValidMoves();
        return validMoves.contains(move);
    }

    /**
     * Change le joueur courant
     */
    private void switchPlayer() {
        currentPlayerColor = currentPlayerColor.opponent();
    }

    /**
     * Vérifie si le jeu est terminé
     */
    private void checkGameOver() {
        // Le jeu est terminé si les deux joueurs n'ont pas de coups valides
        List<Move> movesCurrentPlayer = getValidMoves();

        if (!movesCurrentPlayer.isEmpty()) {
            isGameOver = false;
            return;
        }

        // Passer au joueur suivant
        switchPlayer();
        List<Move> movesNextPlayer = getValidMoves();

        if (movesNextPlayer.isEmpty()) {
            // Les deux joueurs sont bloqués
            isGameOver = true;
        } else {
            // Le joueur courant peut jouer à nouveau
            isGameOver = false;
        }
    }

    /**
     * Retourne le joueur qui peut jouer actuellement
     * Retourne null si personne ne peut jouer (jeu terminé)
     */
    public Player getCurrentPlayer() {
        if (currentPlayerColor == BLACK) {
            return activePlayer;
        } else if (currentPlayerColor == Cell.WHITE) {
            return passivePlayer;
        }
        return null;
    }

    /**
     * Retourne la couleur du joueur courant
     */
    public Cell getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    /**
     * Vérifie si le jeu est terminé
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Retourne le plateau
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retourne l'historique des coups
     */
    public List<Move> getGameHistory() {
        return new ArrayList<>(gameHistory);
    }

    /**
     * Retourne les scores finaux
     */
    public GameResult getGameResult() {
        int blackScore = board.countPieces(BLACK);
        int whiteScore = board.countPieces(Cell.WHITE);

        String winner;
//        if (blackScore > whiteScore) {
//            winner = activePlayer.getName();
//        } else if (whiteScore > blackScore) {
//            winner = passivePlayer.getName();
//        } else {
//            winner = "Draw";
//        }

//        return new GameResult(activePlayer.getName(), passivePlayer.getName(), blackScore, whiteScore, winner);
        return null;
    }
}
