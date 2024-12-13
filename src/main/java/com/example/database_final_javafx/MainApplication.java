package com.example.database_final_javafx;

import com.example.database_final_javafx.controller.LoginController;
import com.example.database_final_javafx.controller.UserMainMenuController;
import com.example.database_final_javafx.utils.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApplication extends Application {
    private Connection connection;
    private Stage stage;


    @Override
    public void start(Stage stage) throws IOException {

        try {
            connection = DatabaseUtil.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // If connection fails, return early and don't proceed
        }

        this.stage = stage;

        showLoginPage();
    }

    private void showLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        loader.setControllerFactory(type -> new LoginController(connection, this));  // Pass the reference to MainApplication
        Pane root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void showBookListPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("user-main.fxml"));
        loader.setControllerFactory(type -> new UserMainMenuController(connection));
        Pane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}