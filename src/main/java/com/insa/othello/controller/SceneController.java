package com.insa.othello.controller;

import com.insa.othello.model.GameConfiguration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static SceneController instance;

    private Stage stage;
    private Scene scene;

    private SceneController() {
    }

    public static SceneController getInstance() {
        if (instance == null)
            instance = new SceneController();
        return instance;
    }

    public void init(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(new StackPane());
        this.stage.setTitle("Othello");
        this.stage.setScene(this.scene);

        this.configure();

        this.stage.sizeToScene();
        this.stage.show();
    }

    public void configure() {
        this.switchTo("configuration");
    }

    public void play(GameConfiguration gameConfiguration) {
        GameController gameController = this.switchToAndGetController("game", GameController.class);
        gameController.start(gameConfiguration);
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void switchTo(String fxmlPath) {
        this.switchToAndGetController(fxmlPath, null);
    }

    public <T> T switchToAndGetController(String fxmlPath, Class<T> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/views/%s.fxml".formatted(fxmlPath)));
            scene.setRoot(loader.load());
            return loader.getController();
        } catch (IOException e) {
            System.err.println("Error charging the view : " + fxmlPath);
            e.printStackTrace();
        }
        return null;
    }
}