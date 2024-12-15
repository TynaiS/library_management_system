package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.DTO.AuthorDTO;
import com.example.database_final_javafx.DTO.BookSalesDTO;
import com.example.database_final_javafx.entity.Author;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    private List<AuthorDTO> getAuthorsBySales(int N, String orderDirection) throws SQLException {
        String sql = String.format("""
            SELECT
                a.id,
                a.name,
                COALESCE(SUM(o.quantity), 0) AS total_sales,
                COALESCE(SUM(o.total_amount), 0) AS total_revenue,
                COUNT(b.id) AS total_books,
                SUM(CASE WHEN b.is_available = TRUE THEN 1 ELSE 0 END) AS total_available_books
            FROM
                %s a
            LEFT JOIN
                books b ON a.id = b.author_id
            LEFT JOIN
                "orders" o ON b.id = o.book_id
            GROUP BY
                 a.id, a.name
            ORDER BY
                total_sales %s
            LIMIT ?;
        """, getTableName(),orderDirection);


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, N);
            ResultSet rs = statement.executeQuery();

            List<AuthorDTO> authors = new ArrayList<>();
            while (rs.next()) {
                AuthorDTO authorDTO = mapResultSetToDTO(rs);
                authors.add(authorDTO);

            }
            return authors;
        }
    }

    public List<AuthorDTO> getTopBestSellingAuthors(int N) throws Exception {
        return getAuthorsBySales(N, "DESC");
    }

    public List<AuthorDTO> getTopWorstSellingAuthors(int N) throws Exception {
        return getAuthorsBySales(N, "ASC");
    }

    private AuthorDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        AuthorDTO author = AuthorDTO.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .totalSales(rs.getInt("total_sales"))
                .totalRevenue(rs.getInt("total_revenue"))
                .totalBooks(rs.getInt("total_books"))
                .totalAvailableBooks(rs.getInt("total_available_books"))
                .build();
        return author;
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
