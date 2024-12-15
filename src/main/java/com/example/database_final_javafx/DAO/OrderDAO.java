package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.DTO.BooksOwnedByUserDTO;
import com.example.database_final_javafx.entity.Order;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends GenericDao<Order> {

    public OrderDAO(Connection connection) {
        super(connection);
    }


    public List<BooksOwnedByUserDTO> findBookIDsOwnedByUser (Long userId) {
        List<BooksOwnedByUserDTO> result = new ArrayList<>();
        String sql = "select book_id, user_id, count(*) as owned_books_count from " + getTableName() + " where user_id = ? group by book_id, user_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(BooksOwnedByUserDTO.builder()
                        .userId(userId)
                        .ownedBooksCount(rs.getInt("owned_books_count"))
                        .bookId(rs.getLong("book_id"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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

    public Integer countTotalBooksRevenue() {
        String sql = "SELECT COALESCE(SUM(o.total_amount), 0) AS total_revenue FROM " + getTableName() + " o";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_revenue");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }



    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected String generateInsertSQL(Order entity) {
        return "INSERT INTO " + getTableName() + "(user_id, book_id, order_date, quantity, total_amount) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String generateUpdateSQL(Order entity) {
        return null;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Order entity) throws SQLException {
        stmt.setLong(1, entity.getUserId());
        stmt.setLong(2, entity.getBookId());
        stmt.setTimestamp(3, Timestamp.valueOf(entity.getOrderDate()));
        stmt.setInt(4, entity.getQuantity());
        stmt.setInt(5, entity.getTotalAmount());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Order entity) throws SQLException {

    }

    @Override
    protected Order mapResultSetToEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long userId = rs.getLong("user_id");
        long bookId = rs.getLong("book_id");
        LocalDateTime orderDate = rs.getTimestamp("order_date").toLocalDateTime();
        int quantity = rs.getInt("quantity");
        int totalAmount = rs.getInt("total_amount");
        return new Order(id, userId, bookId, orderDate, quantity, totalAmount);
    }
}