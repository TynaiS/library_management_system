package com.example.database_final_javafx;

import com.example.database_final_javafx.controller.AddBookController;
import com.example.database_final_javafx.controller.AdminPanelController;
import com.example.database_final_javafx.controller.LoginController;
import com.example.database_final_javafx.controller.UserMainMenuController;
import com.example.database_final_javafx.utils.CreateTables;
import com.example.database_final_javafx.utils.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApplication extends Application {
    private Connection connection;
    private Stage stage;
    private Stage addBookModalStage;



    @Override
    public void start(Stage stage) throws IOException {

        try {
            connection = DatabaseUtil.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // If connection fails, return early and don't proceed
        }

//        CreateTables createTables = new CreateTables(connection);
//        createTables.create();

        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        loader.setControllerFactory(type -> new MainController(connection, this));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Library Management System");
        stage.setScene(scene);

        MainController mainController = loader.getController();
        FXMLLoader loginLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        loginLoader.setControllerFactory(type -> new LoginController(connection, this));
        Pane loginPage = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setMainController(mainController);

        mainController.setContent(loginPage);

        stage.show();


    }

//    private void showLoginPage() throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
//        loader.setControllerFactory(type -> new LoginController(connection, this));
//        Pane root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setTitle("Library Management System");
//        stage.setScene(scene);
//        stage.show();
//    }

//    public void showBookListPage() throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("user-main.fxml"));
//        loader.setControllerFactory(type -> new UserMainMenuController(connection));
//        Pane root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//    }


//    public void showAdminPage() throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("admin-panel.fxml"));
//        loader.setControllerFactory(type -> new AdminPanelController(connection, this));
//        Pane root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//    }

//    public void showAddBookForm() throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("add-book.fxml"));
//        loader.setControllerFactory(type -> new AddBookController(connection));
//        Pane root = loader.load();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//    }

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

    public static void main(String[] args) {
        launch();
    }

}