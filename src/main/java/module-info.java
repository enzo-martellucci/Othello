module com.insa.othello {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.insa.othello;
    opens com.insa.othello.ai;
    opens com.insa.othello.constant;
    opens com.insa.othello.controller;
    opens com.insa.othello.model;
    exports com.insa.othello;
}