package com.example.database_final_javafx.controller;
import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.DAO.OrderDAO;
import com.example.database_final_javafx.DTO.BooksOwnedByUserDTO;
import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.entity.User;
import com.example.database_final_javafx.utils.UserSession;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


@NoArgsConstructor
public class UserMainMenuController implements Initializable {
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;

    private OrderDAO orderDAO;
    private BookController bookController;

    @FXML
    private GridPane bookGrid;

    public UserMainMenuController(Connection connection) {
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.orderDAO = new OrderDAO(connection);
        this.bookController = new BookController(connection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Book> books = loadBooks();
        List<BooksOwnedByUserDTO> booksOwnedByUser = loadBooksOwnedByUser();

        int column = 0;
        int row = 1;

        GridPane gridPane = bookGrid;
        gridPane.getColumnConstraints().clear(); // Clear existing constraints

        StackPane.setAlignment(bookGrid, Pos.CENTER);

        gridPane.setHgap(10); // Horizontal gap between cells
        gridPane.setVgap(10); // Vertical gap between cells
        

        try {
            for (int i = 0; i < books.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/database_final_javafx/book-item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                BookItemController itemController = fxmlLoader.getController();
                itemController.setOrderDAO(orderDAO);

                itemController.setData(getBooksAuthor(books.get(i).getAuthorId()), books.get(i).getDescription(), books.get(i).getId(), getCountOfOwnedBook(booksOwnedByUser, books.get(i).getId()) );

                if (column == 4) {
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

    private List<Book> loadBooks() {
        try {
            return bookDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<BooksOwnedByUserDTO> loadBooksOwnedByUser() {
        return orderDAO.findBookIDsOwnedByUser(UserSession.getUser().getId());
    }

    private String getBooksAuthor(Long id) throws SQLException {
        return authorDAO.findById(id).getName();
    }

    private int getCountOfOwnedBook (List<BooksOwnedByUserDTO> booksOwnedByUserDTOs, Long bookId) {
        for (BooksOwnedByUserDTO item : booksOwnedByUserDTOs) {
            if (item.getBookId() == bookId) {
                return item.getOwnedBooksCount();
            }
        }
        return 0;
    }
}
