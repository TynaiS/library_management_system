package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.DAO.OrderDAO;
import com.example.database_final_javafx.DTO.AuthorDTO;
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
    public TextField topNInputAuthors;
    @FXML
    public Label errorMessageLabelAuthors;
    @FXML
    public Label errorMessageLabelNewAuthor;

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
        topNInputAuthors.setTextFormatter(InputFormatter.createIntegerInputFormatter());
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

    private Boolean addAuthorInputChecker() {
        Boolean result = true;
        String input = newAuthorNameField.getText();
        if (InputFormatter.areStringsEmpty(input)) {
            errorMessageLabelNewAuthor.setText("Input cannot be empty!");
            errorMessageLabelNewAuthor.setVisible(true);
            result = false;
        } else {
            errorMessageLabelNewAuthor.setVisible(false);
        }

        return result;
    }

    private Boolean authorsInputChecker() {
        Boolean result = true;
        String input = topNInputAuthors.getText();
        if (InputFormatter.areStringsEmpty(input)) {
            errorMessageLabelAuthors.setText("Input cannot be empty!");
            errorMessageLabelAuthors.setVisible(true);
            result = false;
        } else {
            errorMessageLabelAuthors.setVisible(false);
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

    @FXML
    public void getTopBestSellingAuthors() throws Exception {
        if (authorsInputChecker()) {
            List<AuthorDTO> authors = authorDAO.getTopBestSellingAuthors(Integer.parseInt(topNInputAuthors.getText()));
            updateAuthorsGrid(authors);
        }
    }

    @FXML
    public void getTopWorstSellingAuthors() throws Exception {
        if (authorsInputChecker()) {
            List<AuthorDTO> authors = authorDAO.getTopWorstSellingAuthors(Integer.parseInt(topNInputAuthors.getText()));
            updateAuthorsGrid(authors);
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
        if(addAuthorInputChecker()){
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

    private void updateAuthorsGrid(List<AuthorDTO> authors) {
        authorsGrid.getChildren().clear();
        authorsScroll.setVisible(true);
        int column = 0;
        int row = 1;
            GridPane gridPane = authorsGrid;
        gridPane.getColumnConstraints().clear(); // Clear existing constraints

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        try {
            for (int i = 0; i < authors.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/database_final_javafx/author-item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                AuthorItemController itemController = fxmlLoader.getController();
                itemController.setData(
                        mainApplication,
                        authors.get(i).getName(),
                        authors.get(i).getTotalSales(),
                        authors.get(i).getTotalRevenue(),
                        authors.get(i).getTotalBooks(),
                        authors.get(i).getTotalAvailableBooks()
                );

                if (column == 3) {
                    column = 0;
                    row++;
                }

                authorsGrid.add(anchorPane, column++, row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeAuthorsList(ActionEvent actionEvent) {
        authorsScroll.setVisible(false);
    }


}
