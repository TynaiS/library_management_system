package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.DAO.OrderDAO;
import com.example.database_final_javafx.entity.Order;
import com.example.database_final_javafx.utils.AlertShower;
import com.example.database_final_javafx.utils.UserSession;
import com.example.database_final_javafx.utils.exceptions.NotEnoughItemsException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UserBuyBookController {
    private OrderDAO orderDAO;
    private BookDAO bookDAO;
    private Long bookId;
    private int amount;
    public UserBuyBookController(Connection connection, Long bookId, int amount) {
        this.orderDAO = new OrderDAO(connection);
        this.bookDAO = new BookDAO(connection);
        this.bookId = bookId;
        this.amount = amount;
    }
    @FXML
    private TextField quantityField;

    @FXML
    private void handleBuy() throws SQLException {
        if (quantityField.getText().isEmpty()) {
            AlertShower alertShower = AlertShower.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title("Invalid data")
                    .header("Invalid data")
                    .text("Please enter a valid number for quantity")
                    .build();
            alertShower.showAlert();
        } else {
            boolean orderCreated = false;
            LocalDateTime orderCreatedTime = LocalDateTime.now();
            Integer quantity = 0;

            try {
                quantity = Integer.parseInt(this.quantityField.getText());
                Order order = Order.builder()
                        .bookId(bookId)
                        .userId(UserSession.getUser().getId())
                        .orderDate(orderCreatedTime)
                        .quantity(quantity)
                        .totalAmount(amount * quantity)
                        .build();

                int newQuantity = bookDAO.findById(bookId).getStockQuantity() - quantity;

                if (newQuantity < 0) throw new NotEnoughItemsException("Not enough items in DB, please lower quantity");

                orderDAO.save(order);
                orderCreated = true;
                bookDAO.updateBooksQuantity(bookId, newQuantity);
            } catch (NumberFormatException e) {
                AlertShower alertShower = AlertShower.builder()
                        .alertType(Alert.AlertType.ERROR)
                        .title("Invalid data")
                        .header("Invalid data")
                        .text("Please enter a valid number for quantity")
                        .build();
                alertShower.showAlert();
            } catch (SQLException e) {
                if (orderCreated) {
                    Order order = orderDAO.findOrderByDateAndUserAndBookIdAndQuantity(orderCreatedTime, UserSession.getUser().getId(), bookId, quantity);
                    orderDAO.delete(order.getId());
                }
                AlertShower alertShower = AlertShower.builder()
                        .alertType(Alert.AlertType.ERROR)
                        .title("Error")
                        .header("Error")
                        .text(e.getMessage())
                        .build();
                alertShower.showAlert();
            } catch (NotEnoughItemsException e) {
                AlertShower alertShower = AlertShower.builder()
                        .alertType(Alert.AlertType.ERROR)
                        .title("Not enough items ")
                        .header("Not enough items")
                        .text(e.getMessage())
                        .build();
                alertShower.showAlert();
            }
        }

        Stage stage = (Stage) quantityField.getScene().getWindow();
        stage.close();
    }
}
