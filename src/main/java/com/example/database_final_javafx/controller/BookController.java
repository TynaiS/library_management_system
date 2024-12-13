package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.AuthorDAO;
import com.example.database_final_javafx.DAO.BookDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class BookController {
    private AuthorDAO authorDAO;

    public BookController(Connection connection) {
        this.authorDAO = new AuthorDAO(connection);
    }

    public String getBooksAuthor(Long authorId) throws SQLException {
        return authorDAO.findById(authorId).getName();
    }


}
