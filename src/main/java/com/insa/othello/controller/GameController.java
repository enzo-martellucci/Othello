package com.insa.othello.controller;

import com.insa.othello.constant.Cell;
import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.Game;
import com.insa.othello.model.GameConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

import static com.insa.othello.constant.Configuration.SIZE;

public class GameController implements Initializable {
    private Game game;
    private GameConfiguration gameConfiguration;

    @FXML
    private Label lblMessage;
    @FXML
    private Label lblScoreBlack;
    @FXML
    private Label lblNameBlack;
    @FXML
    private Label lblScoreWhite;
    @FXML
    private Label lblNameWhite;
    @FXML
    private GridPane gridPane;
    private Button[][] buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttons = new Button[SIZE][SIZE];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                final int row = r, col = c;
                Button btn = new Button();
                btn.getStyleClass().add("game-button");
                btn.setOnAction(_ -> this.play(row, col));

                this.buttons[r][c] = btn;
                this.gridPane.add(btn, c, r);
            }
        }
    }

    public void start(GameConfiguration gameConfiguration) {
        this.game = new Game(gameConfiguration);
        this.gameConfiguration = gameConfiguration;

        this.updateUI();
        PlayerType blackPlayer = gameConfiguration.blackPlayer();
        PlayerType whitePlayer = gameConfiguration.whitePlayer();
        StrategyType blackStrategy = gameConfiguration.blackStrategy();
        StrategyType whiteStrategy = gameConfiguration.whiteStrategy();
        this.lblNameBlack.setText(blackStrategy == null ? blackPlayer.getValue() : "%s - %s".formatted(blackPlayer.getValue(), blackStrategy.getValue()));
        this.lblNameWhite.setText(whiteStrategy == null ? whitePlayer.getValue() : "%s - %s".formatted(whitePlayer.getValue(), whiteStrategy.getValue()));
    }

    public void play(int row, int col) {
        this.game.play(row, col);
        this.updateUI();
    }

    public void restart() {
        this.game.init();
        this.updateUI();
    }

    public void newGame() {
        SceneController.getInstance().configure(this.gameConfiguration);
    }

    private void updateUI() {
        Cell[][] grid = this.game.getBoard().getGrid();

        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[r].length; c++) {
                switch (grid[r][c]) {
                    case WHITE -> this.buttons[r][c].setGraphic(new Circle(25, Color.WHITE));
                    case BLACK -> this.buttons[r][c].setGraphic(new Circle(25, Color.BLACK));
                    case PLAYABLE -> this.buttons[r][c].setGraphic(new Circle(5, Color.YELLOW));
                    case EMPTY -> this.buttons[r][c].setGraphic(null);
                }

                this.buttons[r][c].setDisable(grid[r][c] != Cell.PLAYABLE);
            }

        this.lblMessage.setText(this.game.getMessage().getValue());
        this.lblScoreBlack.setText("" + this.game.getBlackPlayer().getScore());
        this.lblScoreWhite.setText("" + this.game.getWhitePlayer().getScore());
    }
}
