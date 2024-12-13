package com.example.database_final_javafx.DAO;

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


    public List<Long> findBookIDsOwnedByUser (Long userId) {
        List<Long> result = new ArrayList<>();
//        String sql = "select book_id, user_id, count(*) as owned_books_count from " + getTableName() + " where user_id = ? group by book_id, user_id";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setLong(1, userId);
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                result.add(rs.getLong("book_id"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return result;
    }

    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected String generateInsertSQL(Order entity) {
        return "Insert into " + getTableName() + " (user_id, book_id, order_date, quantity, total_amount) values (?, ?, ?, ?, ?)";
    }

    @Override
    protected String generateUpdateSQL(Order entity) {
        return null;
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Order entity) throws SQLException {
        stmt.setLong(1, entity.getUser_id());
        stmt.setLong(2, entity.getBook_id());
        stmt.setTimestamp(3, Timestamp.valueOf(entity.getOrder_date()));
        stmt.setInt(4, entity.getQuantity());
        stmt.setInt(5, entity.getTotal_amount());
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
