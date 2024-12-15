package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.*;

public class AuthorDAO extends GenericDao<Author> {
    public AuthorDAO(Connection connection) {super(connection);}

    public Integer countAllAuthors() {
        String sql = "SELECT COUNT(*) AS total_authors FROM " + getTableName();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_authors");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    @Override
    protected String getTableName() {
        return "authors";
    }

    @Override
    protected String generateInsertSQL(Author author) {
        return "INSERT INTO " + getTableName() + "(name) VALUES (?)";
    }

    @Override
    protected String generateUpdateSQL(Author entity) {
        return null;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Author entity) throws SQLException {
        stmt.setString(1, entity.getName());
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
        String sql = "SELECT * FROM " + getTableName() + " WHERE name = ?";
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
