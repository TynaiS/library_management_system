package com.example.database_final_javafx;

import com.example.database_final_javafx.controller.AuthorController;
import com.example.database_final_javafx.dao.AuthorDAO;
import com.example.database_final_javafx.utils.CreateTables;
import com.example.database_final_javafx.utils.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        fxmlLoader.setControllerFactory(type -> {
            if(type == AuthorController.class) {
                return new AuthorController(connection);
            }
            return null;
        });
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}