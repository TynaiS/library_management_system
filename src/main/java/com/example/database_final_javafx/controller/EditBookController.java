package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.utils.AlertUtils;
import com.example.database_final_javafx.utils.InputFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EditBookController implements Initializable {

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
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private MainApplication mainApplication;

    private Long bookId;


    public EditBookController(Connection connection, MainApplication mainApplication, Long bookId) {
        this.connection = connection;
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.mainApplication = mainApplication;
        this.bookId = bookId;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        String authorName;
        Book book;
        List<Author> authors;
        try {
            book = bookDAO.findById(bookId);
            authorName = authorDAO.findById(book.getAuthorId()).getName();
            authors = authorDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        priceField.setTextFormatter(InputFormatter.createIntegerInputFormatter());
        stockField.setTextFormatter(InputFormatter.createIntegerInputFormatter());

        authorComboBox.setValue(authorName);
        titleField.setText(book.getTitle());
        descriptionField.setText(book.getDescription());
        priceField.setText(Integer.toString(book.getPrice()));
        stockField.setText(Integer.toString(book.getStockQuantity()));
        availableCheckBox.setSelected(book.getIsAvailable());

        errorMessageLabelAddBook.setVisible(false);


        for (Author author : authors) {
            authorComboBox.getItems().add(author.getName());
        }
    }


    @FXML
    public void handleSaveEditedBook(ActionEvent actionEvent) throws SQLException, IOException {

        String titleFieldText = titleField.getText();
        String descriptionFieldText = descriptionField.getText();
        String priceFieldText = priceField.getText();
        String stockFieldText = stockField.getText();
        if (InputFormatter.areStringsEmpty(titleFieldText, descriptionFieldText, priceFieldText, stockFieldText)) {
            errorMessageLabelAddBook.setText("Fill in all the fields!");
            errorMessageLabelAddBook.setVisible(true);
            return;
        } else {
            errorMessageLabelAddBook.setVisible(false);
        }


        String selectedAuthorName = authorComboBox.getValue();
        String title = titleField.getText();
        String description = descriptionField.getText();
        int price = Integer.parseInt(priceField.getText());
        int stockQuantity = Integer.parseInt(stockField.getText());
        boolean isAvailable = availableCheckBox.isSelected();

        Author selectedAuthor = authorDAO.findByName(selectedAuthorName);
        long authorId = selectedAuthor.getId();

        Book updatedBook = Book.builder()
                .authorId(authorId)
                .title(title)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .isAvailable(isAvailable)
                .id(bookId)
                .build();

        bookDAO.update(updatedBook);

        clearForm();
        closeEditBookForm();

        AlertUtils.showAlert(Alert.AlertType.INFORMATION, "Updated!", "Book updated successfully");
    }

    private void clearForm() {
        titleField.clear();
        descriptionField.clear();
        priceField.clear();
        stockField.clear();
        availableCheckBox.setSelected(false);
        authorComboBox.getSelectionModel().clearSelection();
    }

    public void closeEditBookForm() throws IOException {
        mainApplication.closeEditBookModal();
    }

}
