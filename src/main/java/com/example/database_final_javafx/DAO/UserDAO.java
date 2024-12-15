package com.example.database_final_javafx.DAO;

import com.example.database_final_javafx.entity.User;
import com.example.database_final_javafx.utils.AccountType;
import com.example.database_final_javafx.utils.GenericDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends GenericDao<User> {
    public UserDAO(Connection connection) {
        super(connection);
    }

    public User findByUsernameAndPassword(String email, String password) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE email = ? AND password = ?";
        System.out.println("preparing query");
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            System.out.println("executing query");
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected String generateInsertSQL(User user) {
        return "INSERT INTO " + getTableName() + "() VALUES ()";
    }

    @Override
    protected String generateUpdateSQL(User user) {
        return "update users set where";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, User entity) throws SQLException {

    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, User entity) throws SQLException {

    }

    @Override
    protected User mapResultSetToEntity(ResultSet rs) throws SQLException {
        System.out.println(rs);
        User user = User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .accountType(AccountType.valueOf(rs.getString("account_type")))
                .build();
        return user;
    }
}
