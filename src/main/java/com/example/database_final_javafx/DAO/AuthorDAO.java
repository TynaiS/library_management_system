package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.*;

public class AuthorDAO extends GenericDao<Author> {
    public AuthorDAO(Connection connection) {super(connection);}

    @Override
    protected String getTableName() {
        return "authors";
    }

    @Override
    protected String generateInsertSQL(Author author) {
        String query = "INSERT INTO author(name) VALUES (?)";
        String authorName = author.getName().replace("'", "''");
        return "INSERT INTO author(name) VALUES ('" + authorName + "')";
    }

    @Override
    protected String generateUpdateSQL(Author entity) {
        return null;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Author entity) throws SQLException {

    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Author entity) throws SQLException {

    }

    @Override
    protected Author mapResultSetToEntity(ResultSet rs) throws SQLException {
        Author author = Author.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
        return author;
    }

    public Author findByName(String name) throws SQLException {
        String sql = "SELECT * FROM author WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Author(resultSet.getLong("id"), resultSet.getString("name"));
            }
            return null;
        }
    }
}
