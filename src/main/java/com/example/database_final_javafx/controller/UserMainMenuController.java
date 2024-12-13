package com.example.database_final_javafx.controller;
import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.entity.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    private BookController bookController;

    @FXML
    private GridPane bookGrid;

    public UserMainMenuController(Connection connection) {
        this.bookDAO = new BookDAO(connection);
        this.authorDAO = new AuthorDAO(connection);
        this.bookController = new BookController(connection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Book> books = loadBooks();

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

                BookItemController itemController = fxmlLoader.getController();
                itemController.setData(bookController.getBooksAuthor(books.get(i).getAuthorId()), books.get(i).getDescription());

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

    private List<Book> loadBooks() {
        try {
            return bookDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
