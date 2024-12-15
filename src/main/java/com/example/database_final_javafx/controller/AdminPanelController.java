package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.DAO.OrderDAO;
import com.example.database_final_javafx.DTO.BookSalesDTO;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.utils.AlertUtils;
import com.example.database_final_javafx.utils.InputFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    @FXML
    public Label totalBooksNumLabel;

    @FXML
    public Button refreshBooksButton;

    @FXML
    public Label availableBooksNumLabel;

    @FXML
    public Label totalBooksRevenueLabel;
    @FXML
    public Label errorMessageLabelAuthor;

    @FXML
    public TextField newAuthorNameField;
    @FXML
    public VBox authorsSection;

    @FXML
    public Button refreshAuthorsButton;

    @FXML
    public Label totalAuthorsNumLabel;

    @FXML
    public ScrollPane authorsScroll;

    @FXML
    public GridPane authorsGrid;

    @FXML
    private VBox adminPanelVBox;
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;

    private OrderDAO orderDAO;
    private BookController bookController;

    private MainApplication mainApplication;

    @FXML
    private TextField topNInput;

    @FXML
    private GridPane bookGrid;

    @FXML
    private ScrollPane bookTopSalesScroll;

    @FXML
    private Label errorMessageLabelBooks;

    public AdminPanelController(Connection connection, MainApplication mainApplication) {
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.orderDAO = new OrderDAO(connection);
        this.bookController = new BookController(connection);
        this.mainApplication = mainApplication;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshBooksNum();
        refreshAuthorsNum();

        topNInput.setTextFormatter(InputFormatter.createIntegerInputFormatter());

        errorMessageLabelBooks.setVisible(false);
        errorMessageLabelAuthor.setVisible(false);

        bookTopSalesScroll.setVisible(false);
        authorsScroll.setVisible(false);
    }

    private Boolean booksInputChecker() {
        Boolean result = true;
        String input = topNInput.getText();
        if (InputFormatter.areStringsEmpty(input)) {
            errorMessageLabelBooks.setText("Input cannot be empty!");
            errorMessageLabelBooks.setVisible(true);
            result = false;
        } else {
            errorMessageLabelBooks.setVisible(false);
        }

        return result;
    }

    private Boolean authorsInputChecker() {
        Boolean result = true;
        String input = newAuthorNameField.getText();
        if (InputFormatter.areStringsEmpty(input)) {
            errorMessageLabelAuthor.setText("Input cannot be empty!");
            errorMessageLabelAuthor.setVisible(true);
            result = false;
        } else {
            errorMessageLabelAuthor.setVisible(false);
        }

        return result;
    }

    private void updateBookGrid(List<BookSalesDTO> books) {
        bookGrid.getChildren().clear();
        bookTopSalesScroll.setVisible(true);
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

                System.out.println(books.get(i).getIsAvailable());

                BookSalesItemController itemController = fxmlLoader.getController();
                itemController.setData(
                        mainApplication,
                        books.get(i).getId(),
                        bookController.getBooksAuthor(books.get(i).getAuthorId()),
                        books.get(i).getDescription(),
                        books.get(i).getTitle(),
                        books.get(i).getPrice(),
                        books.get(i).getTotalQuantitySold(),
                        books.get(i).getTotalRevenue(),
                        books.get(i).getStockQuantity(),
                        books.get(i).getIsAvailable()
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

    @FXML
    public void getTopBestSellingBooks() throws Exception {
        if (booksInputChecker()) {
            List<BookSalesDTO> books = bookDAO.getTopBestSellingBooks(Integer.parseInt(topNInput.getText()));
            updateBookGrid(books);
        }
    }

    @FXML
    public void getTopWorstSellingBooks() throws Exception {
        if (booksInputChecker()) {
            List<BookSalesDTO> books = bookDAO.getTopWorstSellingBooks(Integer.parseInt(topNInput.getText()));
            updateBookGrid(books);
        }
    }
    public void closeBooksList() {
        bookTopSalesScroll.setVisible(false);
    }

    public void openAddBookForm() throws IOException {
        mainApplication.showAddBookModal();
    }

    public void refreshBooksNum() {
        totalBooksNumLabel.setText("Total number of books: " + bookDAO.countAllBooks());
        availableBooksNumLabel.setText("Total number of available books: " + bookDAO.countAvailableBooks());
        totalBooksRevenueLabel.setText("Total books revenue: $" + orderDAO.countTotalBooksRevenue());
    }

    public void addAuthor() throws SQLException {
        if(authorsInputChecker()){
            Author author = Author.builder()
                    .name(newAuthorNameField.getText())
                    .build();

            authorDAO.save(author);
            newAuthorNameField.clear();

            AlertUtils.showAlert(Alert.AlertType.INFORMATION, "Created!", "Author added successfully");
        }

    }

    public void refreshAuthorsNum() {
        totalAuthorsNumLabel.setText("Total number of authors: " + authorDAO.countAllAuthors());
    }

    private void updateAuthorsGrid(List<BookSalesDTO> books) {
        authorsGrid.getChildren().clear();
        authorsScroll.setVisible(true);
        int column = 0;
        int row = 1;
        GridPane gridPane = authorsGrid;
        gridPane.getColumnConstraints().clear(); // Clear existing constraints

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        try {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/database_final_javafx/book-sales-item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                System.out.println(books.get(i).getIsAvailable());

                BookSalesItemController itemController = fxmlLoader.getController();
                itemController.setData(
                        mainApplication,
                        books.get(i).getId(),
                        bookController.getBooksAuthor(books.get(i).getAuthorId()),
                        books.get(i).getDescription(),
                        books.get(i).getTitle(),
                        books.get(i).getPrice(),
                        books.get(i).getTotalQuantitySold(),
                        books.get(i).getTotalRevenue(),
                        books.get(i).getStockQuantity(),
                        books.get(i).getIsAvailable()
                );

                if (column == 3) {
                    column = 0;
                    row++;
                }

                authorsGrid.add(anchorPane, column++, row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
