package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.entity.Author;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.util.List;

public class AuthorController {

    private final AuthorDAO authorDAO;

    // FXML UI components
    @FXML private TextField authorNameField;
    @FXML private Button addButton;
    @FXML private TableView<Author> authorTable;
    @FXML private TableColumn<Author, Long> idColumn;
    @FXML private TableColumn<Author, String> nameColumn;

    // Constructor for dependency injection of Connection object
    public AuthorController(Connection connection) {
        this.authorDAO = new AuthorDAO(connection);
    }

    @FXML
    public void initialize() throws SQLException {
        // Set up the table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Load authors from the database when the controller is initialized
        loadAuthors();
    }

    // Handle the "Add Author" button click
    @FXML
    public void handleAddAuthor() throws SQLException {
        String name = authorNameField.getText();

        if (name.isEmpty()) {
            showAlert("Error", "Name cannot be empty", AlertType.ERROR);
            return;
        }

        Author author = Author.builder()
                .name(name)
                .build();
        authorDAO.save(author);

        // Clear the input field
        authorNameField.clear();

        // Reload authors to update the list in the table
        loadAuthors();

        // Show success message
        showAlert("Success", "Author added successfully", AlertType.INFORMATION);
    }

    // Load the authors from the database and display them in the table
    private void loadAuthors() throws SQLException {
        List<Author> authors = authorDAO.findAll();
        authorTable.getItems().setAll(authors);
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}