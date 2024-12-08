package com.example.database_final_javafx;

import com.example.database_final_javafx.controller.AuthorController;
import com.example.database_final_javafx.controller.LoginController;
import com.example.database_final_javafx.dao.AuthorDAO;
import com.example.database_final_javafx.utils.CreateTables;
import com.example.database_final_javafx.utils.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainApplication extends Application {
    private Connection connection;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            connection = DatabaseUtil.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // If connection fails, return early and don't proceed
        }

        Map<Class<?>, Supplier<Object>> controllerFactories = new HashMap<>();
        controllerFactories.put(AuthorController.class, () -> new AuthorController(connection));
        controllerFactories.put(LoginController.class, () -> new LoginController(connection));

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        fxmlLoader.setControllerFactory(controllerFactories::get);

        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}