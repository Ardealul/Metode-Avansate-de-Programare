package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class EntityAlert {
    static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    static void showErrorMessage(Stage owner, String text){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.initOwner(owner);
        error.setTitle("Eroare");
        error.setContentText(text);
        error.showAndWait();
    }
}
