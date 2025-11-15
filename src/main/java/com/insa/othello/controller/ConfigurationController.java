package com.insa.othello.controller;

import com.insa.othello.constant.PlayerType;
import com.insa.othello.constant.StrategyType;
import com.insa.othello.model.GameConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Labeled;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {
    @FXML
    private VBox boxBlackStrategy;
    @FXML
    private VBox boxBlackLimit;
    @FXML
    private ToggleGroup tgBlackPlayer;
    @FXML
    private ToggleGroup tgBlackStrategy;
    @FXML
    private Spinner<Integer> spBlackLimitMS;
    @FXML
    private Spinner<Integer> spBlackLimitStep;

    @FXML
    private VBox boxWhiteStrategy;
    @FXML
    private VBox boxWhiteLimit;
    @FXML
    private ToggleGroup tgWhitePlayer;
    @FXML
    private ToggleGroup tgWhiteStrategy;
    @FXML
    private Spinner<Integer> spWhiteLimitMS;
    @FXML
    private Spinner<Integer> spWhiteLimitStep;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setupSpinner(spBlackLimitMS, 1, 10000, 1000, 100);
        this.setupSpinner(spBlackLimitStep, 1, 100, 5, 1);
        this.setupPlayerListener(tgBlackPlayer, this.boxBlackStrategy, this.boxBlackLimit);

        this.setupSpinner(spWhiteLimitMS, 1, 10000, 1000, 100);
        this.setupSpinner(spWhiteLimitStep, 1, 100, 5, 1);
        this.setupPlayerListener(tgWhitePlayer, this.boxWhiteStrategy, this.boxWhiteLimit);
    }

    private void setupSpinner(Spinner<Integer> spinner, int min, int max, int defaultValue, int step) {
        spinner.getEditor().setAlignment(Pos.CENTER);
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, defaultValue, step));
    }

    private void setupPlayerListener(ToggleGroup toggleGroup, VBox... box) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            String player = ((Labeled) newToggle).getText();
            boolean isHuman = player.equals("Human player");
            Arrays.stream(box).forEach(b -> b.setVisible(!isHuman));
        });
    }

    public void handleStart() {
        PlayerType blackPlayer = PlayerType.fromValue(((Labeled) this.tgBlackPlayer.getSelectedToggle()).getText());
        PlayerType whitePlayer = PlayerType.fromValue(((Labeled) this.tgWhitePlayer.getSelectedToggle()).getText());
        StrategyType blackStrategy = null, whiteStrategy = null;
        int blackLimitMS = -1, whiteLimitMS = -1;
        int blackLimitStep = -1, whiteLimitStep = -1;

        if (blackPlayer != PlayerType.HUMAN) {
            blackStrategy = StrategyType.fromValue(((Labeled) this.tgBlackStrategy.getSelectedToggle()).getText());
            blackLimitMS = this.spBlackLimitMS.getValue();
            blackLimitStep = this.spBlackLimitStep.getValue();
        }
        if (whitePlayer != PlayerType.HUMAN) {
            whiteStrategy = StrategyType.fromValue(((Labeled) this.tgWhiteStrategy.getSelectedToggle()).getText());
            whiteLimitMS = this.spWhiteLimitMS.getValue();
            whiteLimitStep = this.spWhiteLimitStep.getValue();
        }

        GameConfiguration gameConfiguration = new GameConfiguration(blackPlayer,
                blackStrategy,
                blackLimitMS,
                blackLimitStep,
                whitePlayer,
                whiteStrategy,
                whiteLimitMS,
                whiteLimitStep);

        SceneController.getInstance().play(gameConfiguration);
    }
}
