package com.insa.othello.controller;

import com.insa.othello.model.Game;
import com.insa.othello.model.GameConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private Game game;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int r = 0; r < this.gridPane.getRowCount(); r++) {
            for (int c = 0; c < this.gridPane.getColumnCount(); c++) {
                final int row = r, col = c;
                Button btn = new Button();
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                btn.setStyle("-fx-border-color: black; -fx-border-width: 1;");
                btn.setOnAction(e -> System.out.printf("[%d:%d]", row, col));
                this.gridPane.add(btn, r, c);
            }
        }
    }

    public void start(GameConfiguration gameConfiguration) {
        this.game = new Game(gameConfiguration);
    }
}
