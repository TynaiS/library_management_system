package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.DTO.BookSalesDTO;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.utils.InputFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

@NoArgsConstructor
public class AdminPanelController implements Initializable{
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private BookController bookController;

    private MainApplication mainApplication;

    @FXML
    private TextField topNInput;

    @FXML
    private GridPane bookGrid;

    @FXML
    private ScrollPane bookTopSalesScroll;

    @FXML
    private Label errorMessageLabel;
    public AdminPanelController(Connection connection, MainApplication mainApplication) {
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.bookController = new BookController(connection);
        this.mainApplication = mainApplication;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topNInput.setTextFormatter(InputFormatter.createIntegerInputFormatter());

        errorMessageLabel.setVisible(false);

        bookTopSalesScroll.setVisible(false);
    }

    @FXML
    public void getTopSellingBooks() throws Exception {
        String input = topNInput.getText();
        if (input == null || input.trim().isEmpty()) {
            errorMessageLabel.setText("Input cannot be empty!");
            errorMessageLabel.setVisible(true);
            return;
        } else {
            errorMessageLabel.setVisible(false);
        }

        bookGrid.getChildren().clear();
        bookTopSalesScroll.setVisible(true);
        List<BookSalesDTO> books = bookDAO.getTopSellingBooks(Integer.parseInt(topNInput.getText()));

        int column = 0;
        int row = 1;

        GridPane gridPane = bookGrid;
        gridPane.getColumnConstraints().clear(); // Clear existing constraints

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        try {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/database_final_javafx/book-sales-item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                BookSalesItemController itemController = fxmlLoader.getController();
                itemController.setData(
                        bookController.getBooksAuthor(books.get(i).getAuthorId()),
                        books.get(i).getDescription(),
                        books.get(i).getTitle(),
                        books.get(i).getTotalQuantitySold(),
                        books.get(i).getTotalRevenue()
                );

                if (column == 3) {
                    column = 0;
                    row++;
                }

                bookGrid.add(anchorPane, column++, row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeTopSellingBooks() {
        bookTopSalesScroll.setVisible(false);
    }

    public void openAddBookForm() throws IOException {
        mainApplication.showAddBookModal();
    }
}
