package com.insa.othello.model;

import com.insa.othello.constant.Message;
import com.insa.othello.controller.GameController;
import javafx.application.Platform;

import static com.insa.othello.constant.Cell.BLACK;
import static com.insa.othello.constant.Cell.WHITE;
import static com.insa.othello.constant.Message.BLACK_TURN;
import static com.insa.othello.constant.Message.WHITE_TURN;

public class Game {
    private final GameController controller;

    private final Board board;

    private Player activePlayer;
    private Player passivePlayer;

    private Message message;
    private boolean isOver;

    private boolean isAIPlaying;

    public Game(GameConfiguration config, GameController controller) {
        this.controller = controller;
        this.board = new Board();

        this.activePlayer = new Player(BLACK, config.blackPlayer(), config.blackStrategy(), config.blackLimitMS(), config.blackLimitStep(), 2);
        this.passivePlayer = new Player(WHITE, config.whitePlayer(), config.whiteStrategy(), config.whiteLimitMS(), config.whiteLimitStep(), 2);

        this.init();
    }

    public void init() {
        if (this.activePlayer.getColor() == WHITE) {
            this.switchPlayer();
        }
        this.activePlayer.setScore(2);
        this.passivePlayer.setScore(2);

        this.board.init();
        this.board.updatePossiblePlay(this.activePlayer.getColor());
        this.message = BLACK_TURN;

        this.isAIPlaying = this.activePlayer.isAI();
        if (this.isAIPlaying)
            this.activePlayer.playAI(this.board, this::playAI);
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public Player getPassivePlayer() {
        return this.passivePlayer;
    }

    public Player getBlackPlayer() {
        return this.activePlayer.getColor() == BLACK ? this.activePlayer : this.passivePlayer;
    }

    public Player getWhitePlayer() {
        return this.activePlayer.getColor() == WHITE ? this.activePlayer : this.passivePlayer;
    }

    public Message getMessage() {
        return this.message;
    }

    public boolean isOver() {
        return this.isOver;
    }

    public void play(Position p) {
        if (this.isAIPlaying)
            return;

        int scored = this.board.play(p.r(), p.c(), this.activePlayer.getColor());
        this.activePlayer.setScore(this.activePlayer.getScore() + scored + 1);
        this.passivePlayer.setScore(this.passivePlayer.getScore() - scored);

        this.nextTurn();

        if (!this.isOver && this.activePlayer.isAI()) {
            this.isAIPlaying = true;
            this.activePlayer.playAI(this.board, this::playAI);
        }
    }

    public void playAI(Position p) {
        Platform.runLater(() -> {
            this.isAIPlaying = false;
            this.play(p);
            this.controller.updateUI();
        });
    }

    private void nextTurn() {
        this.switchPlayer();
        this.board.updatePossiblePlay(this.activePlayer.getColor());
        if (this.board.isPlayable())
            return;
        this.switchPlayer();
        this.board.updatePossiblePlay(this.activePlayer.getColor());
        if (this.board.isPlayable())
            return;

        this.isOver = true;
        if (this.getBlackPlayer().getScore() > this.getWhitePlayer().getScore())
            this.message = Message.BLACK_WIN;
        else if (this.getWhitePlayer().getScore() > this.getBlackPlayer().getScore())
            this.message = Message.WHITE_WIN;
        else
            this.message = Message.DRAW;
    }

    private void switchPlayer() {
        Player tmp = this.activePlayer;
        this.activePlayer = this.passivePlayer;
        this.passivePlayer = tmp;
        this.message = this.activePlayer.getColor() == BLACK ? BLACK_TURN : WHITE_TURN;
    }
}
