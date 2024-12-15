package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.DTO.BookSalesDTO;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends GenericDao<Book> {
    public BookDAO (Connection connection) {
        super(connection);
    }

    public List<BookSalesDTO> getBooksBySales(int N, String orderDirection) throws SQLException {
        String sql = String.format("""
            SELECT 
                b.id,
                b.author_id,
                b.title,
                b.description,
                b.price,
                b.stock_quantity,
                b.is_available,
                COALESCE(SUM(o.quantity), 0) AS total_quantity_sold,
                COALESCE(SUM(o.total_amount), 0) AS total_revenue
            FROM
                books b
            LEFT JOIN 
                "orders" o ON b.id = o.book_id
            GROUP BY 
                b.id, b.title, b.description, b.price, b.stock_quantity
            ORDER BY 
                total_quantity_sold %s
            LIMIT ?;
        """, orderDirection);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, N);
            ResultSet rs = statement.executeQuery();

            List<BookSalesDTO> books = new ArrayList<>();
            while (rs.next()) {
                BookSalesDTO bookSalesDTO = mapResultSetToDTO(rs);
                books.add(bookSalesDTO);

            }
            return books;
        }
    }
    public List<BookSalesDTO> getTopBestSellingBooks(int N) throws Exception {
        return getBooksBySales(N, "DESC");
    }

    public List<BookSalesDTO> getTopWorstSellingBooks(int N) throws Exception {
        return getBooksBySales(N, "ASC");
    }


    public Integer countAllBooks() {
        String sql = "SELECT COUNT(*) AS total_books FROM " + getTableName();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_books");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public Integer countAvailableBooks() {
        String sql = "SELECT COUNT(*) AS total_books FROM " + getTableName() + " WHERE is_available = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_books");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public List<Book> findBooksOwnedByUser(Long userId) throws Exception {
        String sql = "select distinct b.id, b.author_id, b.description, b.price, b.stock_quantity, b.title, b.is_available from books b inner join orders o on b.id = o.book_id where o.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = mapResultSetToEntity(rs);
                books.add(book);
            }
            return books;
        }
    }

    public void updateBooksQuantity(Long bookId, int newQuantity) throws SQLException {
        String sql = "update books set stock_quantity = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, newQuantity);
            statement.setLong(2, bookId);
            statement.executeUpdate();
        }
    }

    @Override
    protected String getTableName() {
        return "books";
    }

    @Override
    protected String generateInsertSQL(Book entity) {
        return "INSERT INTO " + getTableName() + "(author_id, title, description, price, stock_quantity, is_available) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String generateUpdateSQL(Book book) {
        return "UPDATE " + getTableName() + " SET author_id = ?, title = ?, description = ?, price = ?, stock_quantity = ?, is_available = ? WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Book entity) throws SQLException {
        stmt.setLong(1, entity.getAuthorId());
        stmt.setString(2, entity.getTitle());
        stmt.setString(3, entity.getDescription());
        stmt.setInt(4, entity.getPrice());
        stmt.setInt(5, entity.getStockQuantity());
        stmt.setBoolean(6, entity.getIsAvailable());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Book entity) throws SQLException {
        stmt.setLong(1, entity.getAuthorId());
        stmt.setString(2, entity.getTitle());
        stmt.setString(3, entity.getDescription());
        stmt.setInt(4, entity.getPrice());
        stmt.setInt(5, entity.getStockQuantity());
        stmt.setBoolean(6, entity.getIsAvailable());
        stmt.setLong(7, entity.getId());
    }

    @Override
    protected Book mapResultSetToEntity(ResultSet rs) throws SQLException {
        Book book = Book.builder()
                .id(rs.getLong("id"))
                .authorId(rs.getLong("author_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .isAvailable(rs.getBoolean("is_available"))
                .build();
        return book;
    }

    protected BookSalesDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        BookSalesDTO book = BookSalesDTO.builder()
                .id(rs.getLong("id"))
                .authorId(rs.getLong("author_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .price(rs.getInt("price"))
                .stockQuantity(rs.getInt("stock_quantity"))
                .isAvailable(rs.getBoolean("is_available"))
                .totalQuantitySold(rs.getInt("total_quantity_sold"))
                .totalRevenue(rs.getInt("total_revenue"))
                .build();
        return book;
    }

}
