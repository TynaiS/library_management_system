package com.example.database_final_javafx.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<T> {

    protected Connection connection;

    // Constructor to initialize database connection
    public GenericDao(Connection connection) {
        this.connection = connection;
    }

    // Save an entity to the database
    public void save(T entity) throws SQLException {
        String sql = generateInsertSQL(entity);  // Implement this method in child classes
        System.out.println(sql);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setInsertParameters(stmt, entity);
            System.out.println(stmt);
            stmt.executeUpdate();
        }
    }

    // Update an entity in the database
    public void update(T entity) throws SQLException {
        String sql = generateUpdateSQL(entity);  // Implement this method in child classes
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setUpdateParameters(stmt, entity);  // Implement this in child classes
            stmt.executeUpdate();
        }
    }

    // Delete an entity from the database
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // Find an entity by its ID
    public T findById(Long id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);  // Implement this method in child classes
            }
            return null;
        }
    }

    // Find all entities in the table
    public List<T> findAll() throws SQLException {
        List<T> result = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(mapResultSetToEntity(rs));  // Implement this method in child classes
            }
        }
        return result;
    }

    // Abstract methods to be implemented by child classes
    protected abstract String getTableName();  // To get the table name (Book, User, etc.)
    protected abstract String generateInsertSQL(T entity);  // To generate the insert SQL
    protected abstract String generateUpdateSQL(T entity);  // To generate the update SQL
    protected abstract void setInsertParameters(PreparedStatement stmt, T entity) throws SQLException;  // Set parameters for insert
    protected abstract void setUpdateParameters(PreparedStatement stmt, T entity) throws SQLException;  // Set parameters for update
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;  // Map the ResultSet to the entity
}
