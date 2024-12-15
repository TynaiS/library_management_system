package com.example.database_final_javafx;

import com.example.database_final_javafx.controller.*;
import com.example.database_final_javafx.utils.CreateTables;
import com.example.database_final_javafx.utils.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApplication extends Application {
    private Connection connection;
    private Stage stage;
    private Stage addBookModalStage;

    private Stage buyBookModalStage;
    private Stage editBookModalStage;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            connection = DatabaseUtil.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // If connection fails, return early and don't proceed
        }

        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        loader.setControllerFactory(type -> new MainController(connection, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Library Management System");
        stage.setScene(scene);

        MainController mainController = loader.getController();
        FXMLLoader loginLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        loginLoader.setControllerFactory(type -> new LoginController(connection, mainController));
        System.out.println("set login controller");
        Pane loginPage = loginLoader.load();

        mainController.setContent(loginPage);

        stage.show();
    }


    public void showAddBookModal() throws IOException {
        addBookModalStage = new Stage();
        addBookModalStage.initModality(Modality.WINDOW_MODAL);
        addBookModalStage.initOwner(stage);

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-book.fxml"));
        loader.setControllerFactory(type -> new AddBookController(connection, this));
        Pane modalPane = loader.load();
        Scene modalScene = new Scene(modalPane, 600, 400);

        addBookModalStage.setScene(modalScene);
        addBookModalStage.showAndWait();
    }

    public void closeAddBookModal() {
        addBookModalStage.close();
    }

    public void showBuyBookModal(Long bookId, int amount) throws IOException {
        buyBookModalStage = new Stage();
        buyBookModalStage.initModality(Modality.WINDOW_MODAL);
        buyBookModalStage.initOwner(stage);

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("user-buy-book.fxml"));
        loader.setControllerFactory(type -> new UserBuyBookController(connection, bookId, amount));
        Pane modalPane = loader.load();
        Scene modalScene = new Scene(modalPane, 400, 175);

        buyBookModalStage.setScene(modalScene);
        buyBookModalStage.showAndWait();
    }

    public void closeBuyBookModal() {
        buyBookModalStage.close();
    }

    public static void main(String[] args) {
        launch();
    }

    public void showEditBookModal(Long bookId) throws IOException {

        editBookModalStage = new Stage();
        editBookModalStage.initModality(Modality.WINDOW_MODAL);
        editBookModalStage.initOwner(stage);
        System.out.println(this);

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("edit-book.fxml"));
        loader.setControllerFactory(type -> new EditBookController(connection, this, bookId));
        Pane modalPane = loader.load();
        Scene modalScene = new Scene(modalPane, 600, 400);

        editBookModalStage.setScene(modalScene);
        editBookModalStage.showAndWait();
    }

    public void closeEditBookModal() {
        editBookModalStage.close();
    }
}