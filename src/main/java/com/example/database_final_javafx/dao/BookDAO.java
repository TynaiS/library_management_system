package com.example.database_final_javafx.dao;

import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDAO extends GenericDao<Book> {
    public BookDAO (Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "book";
    }

    @Override
    protected String generateInsertSQL(Book entity) {
        return null;
    }

    @Override
    protected String generateUpdateSQL(Book entity) {
        return null;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Book entity) throws SQLException {

    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Book entity) throws SQLException {

    }

    @Override
    protected Book mapResultSetToEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long author_id = rs.getLong("author_id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        int stock_quantity = rs.getInt("stock_quantity");
        return new Book(id, author_id, title, description, price, stock_quantity);
    }
}
