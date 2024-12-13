package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    private Connection conn;

    public AuthorDAO(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void addAuthor(Author author) {
        String query = "INSERT INTO author(name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, author.getName());
            ps.executeUpdate();
        } catch (SQLException e) {

            System.out.println("Oh NOO");
            e.printStackTrace();

        }
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM Author";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setName(rs.getString("name"));
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author getAuthorById(Long id) {
        String query = "SELECT * FROM Author WHERE id = ?";
        Author author = null;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    author = new Author();
                    author.setId(rs.getLong("id"));
                    author.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    public void deleteAuthor(Long id) {
        String query = "DELETE FROM Author WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
