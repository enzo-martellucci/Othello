module com.insa.othello {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.insa.othello to javafx.fxml;
    opens com.insa.othello.controller to javafx.fxml;
    exports com.insa.othello;
}