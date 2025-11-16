package com.insa.othello.model;

import com.insa.othello.constant.Message;

import static com.insa.othello.constant.Cell.BLACK;
import static com.insa.othello.constant.Cell.WHITE;
import static com.insa.othello.constant.Message.BLACK_TURN;
import static com.insa.othello.constant.Message.WHITE_TURN;

public class Game {
    private final Board board;
    private Player activePlayer;
    private Player passivePlayer;

    private Message message;
    private boolean isOver;

    public Game(GameConfiguration configuration) {
        this.board = new Board();

        this.activePlayer = new Player(BLACK, configuration.blackPlayer(), configuration.blackStrategy(), 2);
        this.passivePlayer = new Player(WHITE, configuration.whitePlayer(), configuration.whiteStrategy(), 2);

        this.init();
    }

    public void init() {
        if (this.activePlayer.getColor() == WHITE) {
            this.switchPlayer();
        }
        this.activePlayer.setScore(2);
        this.passivePlayer.setScore(2);

        this.board.init(this.activePlayer.getColor());
        this.board.updatePossiblePlay(this.activePlayer.getColor());
        this.message = BLACK_TURN;
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

    public void play(int r, int c) {
        int scored = this.board.play(r, c, this.activePlayer.getColor());
        this.activePlayer.setScore(this.activePlayer.getScore() + scored + 1);
        this.passivePlayer.setScore(this.passivePlayer.getScore() - scored);
        this.switchPlayer();
        this.board.updatePossiblePlay(this.activePlayer.getColor());

        if (!this.board.isPlayable()) {
            this.switchPlayer();
            this.board.updatePossiblePlay(this.activePlayer.getColor());
        }

        if (!this.board.isPlayable())
            this.isOver = true;
        else
            return;

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
