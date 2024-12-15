package com.example.database_final_javafx.utils;

import javafx.scene.control.Alert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class AlertShower {
    private Alert.AlertType alertType;
    private String title;
    private String header;
    private String text;

    public void showAlert () {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
