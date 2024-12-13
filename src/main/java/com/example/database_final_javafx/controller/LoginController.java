package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.dao.UserDAO;
import com.example.database_final_javafx.entity.User;
import com.example.database_final_javafx.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class LoginController {

    private UserDAO userDAO;
    private MainApplication mainApplication;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;
    public LoginController(Connection connection, MainApplication mainApplication) {
        this.userDAO = new UserDAO(connection);
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please enter both username and password.");
            return;
        }


        User user = userDAO.findByUsernameAndPassword(username, password);
        System.out.println("test");



        if (user != null) {
            UserSession.setUser(user);
            mainApplication.showBookListPage();
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials. Please try again.");
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        navigateToView("register.fxml");
    }

    private void navigateToView(String fxmlFile) {
        try {
            // Close the current login window
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

            // Load the next view (admin or user dashboard)
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/example/database_final_javafx/" + fxmlFile));
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(fxmlFile.contains("admin") ? "Admin Dashboard" : "User Dashboard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
