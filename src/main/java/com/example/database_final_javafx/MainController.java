package com.example.database_final_javafx;

import com.example.database_final_javafx.controller.AddBookController;
import com.example.database_final_javafx.controller.AdminPanelController;
import com.example.database_final_javafx.controller.UserLibraryController;
import com.example.database_final_javafx.controller.UserMainMenuController;
import com.example.database_final_javafx.utils.DatabaseUtil;
import com.example.database_final_javafx.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.Connection;

public class MainController {

    @FXML
    private Button connectButton;

    @FXML
    private StackPane contentPane;

    @FXML
    private Button logoutButton;

    private Connection connection;

    private MainApplication mainApplication;

    public MainController(Connection connection, MainApplication mainApplication) {
        this.connection = connection;
        this.mainApplication = mainApplication;
    }

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

    public void loadUserPage() throws IOException {
        logoutButton.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user-main.fxml"));
        loader.setControllerFactory(type -> new UserMainMenuController(connection, this, mainApplication));
        Pane bookListPage = loader.load();
        setContent(bookListPage);
    }

    public void loadAdminPage() throws IOException {
        logoutButton.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-panel.fxml"));
        loader.setControllerFactory(type -> new AdminPanelController(connection, mainApplication));
        Pane adminPage = loader.load();
        setContent(adminPage);
    }

    public void handleLogout() throws IOException {
        logoutButton.setVisible(false);
        UserSession.clearUser();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Pane loginPage = loader.load();
        setContent(loginPage);
    }

    public void loadUserLibrary() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user-library.fxml"));
        loader.setControllerFactory(type -> new UserLibraryController(connection, this));
        Pane bookListPage = loader.load();
        setContent(bookListPage);
    }

    public void setContent(Pane page) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(page);
    }
}
