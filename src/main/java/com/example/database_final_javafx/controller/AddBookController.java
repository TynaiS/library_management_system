package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.utils.InputFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @FXML
    private ComboBox<String> authorComboBox;
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private CheckBox availableCheckBox;

    @FXML
    private Label errorMessageLabelAddBook;

    private Connection connection;
    private AuthorDAO authorDAO;
    private BookDAO bookDAO;

    private MainApplication mainApplication;


    public AddBookController(Connection connection, MainApplication mainApplication) {
        this.connection = connection;
        this.authorDAO = new AuthorDAO(connection);
        this.bookDAO = new BookDAO(connection);
        this.mainApplication = mainApplication;

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        // Populate authorComboBox with author names from the database
        List<Author> authors = null;
        try {
            authors = authorDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        titleField.setTextFormatter(InputFormatter.createTextInputFormatter());
        descriptionField.setTextFormatter(InputFormatter.createTextInputFormatter());

        priceField.setTextFormatter(InputFormatter.createIntegerInputFormatter());
        stockField.setTextFormatter(InputFormatter.createIntegerInputFormatter());

        errorMessageLabelAddBook.setVisible(false);


        for (Author author : authors) {
            authorComboBox.getItems().add(author.getName());
        }
    }


    @FXML
    private void handleAddBook() throws SQLException, IOException {

        String titleFieldText = titleField.getText();
        String descriptionFieldText = descriptionField.getText();
        String priceFieldText = priceField.getText();
        String stockFieldText = stockField.getText();
        if (titleFieldText == null || titleFieldText.trim().isEmpty() ||
                descriptionFieldText == null || descriptionFieldText.trim().isEmpty() ||
                priceFieldText == null || priceFieldText.trim().isEmpty() ||
                stockFieldText == null || stockFieldText.trim().isEmpty() ) {
            errorMessageLabelAddBook.setText("Fill in all the fields!");
            errorMessageLabelAddBook.setVisible(true);
            return;
        } else {
            errorMessageLabelAddBook.setVisible(false);
        }

        // Get form values
        String selectedAuthorName = authorComboBox.getValue();
        String title = titleField.getText();
        String description = descriptionField.getText();
        int price = Integer.parseInt(priceField.getText());
        int stockQuantity = Integer.parseInt(stockField.getText());
        boolean isAvailable = availableCheckBox.isSelected();

        // Get the author_id from the selected author name
        Author selectedAuthor = authorDAO.findByName(selectedAuthorName);
        long authorId = selectedAuthor.getId();

        // Create a new Book object using Lombok's builder
        Book newBook = Book.builder()
                .authorId(authorId)
                .title(title)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .isAvailable(isAvailable)
                .build();

        System.out.println(newBook);

        bookDAO.save(newBook);

        clearForm();
        closeAddBookForm();

    }

    private void clearForm() {
        titleField.clear();
        descriptionField.clear();
        priceField.clear();
        stockField.clear();
        availableCheckBox.setSelected(false);
        authorComboBox.getSelectionModel().clearSelection();
    }

    public void closeAddBookForm() throws IOException {
        mainApplication.closeAddBookModal();
    }


}
