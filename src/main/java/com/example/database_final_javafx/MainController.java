package com.example.database_final_javafx;

import com.example.database_final_javafx.utils.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.Connection;

public class MainController {

    @FXML
    private Button connectButton;

    @FXML
    private void handleConnectButton() {
        try (Connection conn = DatabaseUtil.connect()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database Connection");
            alert.setHeaderText("Success");
            alert.setContentText("Connected to the database successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection");
            alert.setHeaderText("Failure");
            alert.setContentText("Failed to connect to the database: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
