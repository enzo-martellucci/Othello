package com.insa.othello;

import com.insa.othello.controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        SceneController sceneController = SceneController.getInstance();
        sceneController.init(stage);
    }
}