package com.example.database_final_javafx.controller;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.dao.AuthorDAO;
import com.example.database_final_javafx.dao.BookDAO;
import com.example.database_final_javafx.dao.OrderDAO;
import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.utils.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserMainMenuController implements Initializable {
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;

    private OrderDAO orderDAO;

    @FXML
    private GridPane bookGrid;

    public UserMainMenuController(Connection connection) {
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.orderDAO = new OrderDAO(connection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Book> books = loadBooks();
        List<Long> booksIDsOwnedByUser = loadBooksOwnedByUser();

        int column = 0;
        int row = 1;

        GridPane gridPane = bookGrid;
        gridPane.getColumnConstraints().clear(); // Clear existing constraints

        gridPane.setHgap(10); // Horizontal gap between cells
        gridPane.setVgap(10); // Vertical gap between cells

        try {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/database_final_javafx/book-item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                boolean isBookOwned = false;

                if (booksIDsOwnedByUser.contains( books.get(i).getId()) ) {
                    isBookOwned = true;
                }

                BookItemController itemController = fxmlLoader.getController();
                itemController.setOrderDAO(orderDAO);



                itemController.setData(getBooksAuthor(books.get(i).getAuthor_id()), books.get(i).getDescription(), books.get(i).getId(), isBookOwned );

                if (column == 3) {
                    column = 0;
                    row++;
                }

                bookGrid.add(anchorPane, column++, row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Book> loadBooks() {
        try {
            return bookDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Long> loadBooksOwnedByUser() {
        return orderDAO.findBookIDsOwnedByUser(UserSession.getUser().getId());
    }

    private String getBooksAuthor(Long id) {
        return authorDAO.getAuthorById(id).getName();
    }
}
