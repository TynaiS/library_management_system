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
    public List<BookSalesDTO> getTopSellingBooks(int topN) throws Exception {
        String sql = """
            SELECT 
                b.id ,
                b.author_id,
                b.title,
                b.description,
                b.price,
                b.stock_quantity,
                SUM(o.quantity) AS total_quantity_sold,
                SUM(o.total_amount) AS total_revenue
            FROM
                book b
            JOIN 
                "order" o ON b.id = o.book_id
            GROUP BY 
                b.id, b.title, b.description, b.price, b.stock_quantity
            ORDER BY 
                total_quantity_sold DESC
            LIMIT ?;
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, topN);
            ResultSet rs = statement.executeQuery();

            List<BookSalesDTO> books = new ArrayList<>();
            while (rs.next()) {
                BookSalesDTO bookSalesDTO = mapResultSetToDTO(rs);
                books.add(bookSalesDTO);

            }
            return books;
        }
    }

    @Override
    protected String getTableName() {
        return "book";
    }

    @Override
    protected String generateInsertSQL(Book entity) {
        return "INSERT INTO " + getTableName() + " (author_id, title, description, price, stock_quantity, is_available) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String generateUpdateSQL(Book entity) {
        return null;
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
                .totalQuantitySold(rs.getInt("total_quantity_sold"))
                .totalRevenue(rs.getInt("total_revenue"))
                .build();
        return book;
    }
}
