package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.dao.BookDAO;
import com.example.database_final_javafx.dao.OrderDAO;
import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.entity.Order;
import com.example.database_final_javafx.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BookItemController{

    @FXML
    private Label authorLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView image;

    @FXML
    private Button buyButton;

    @FXML
    private Label bookItems;

    private OrderDAO orderDAO;
    private Long bookId;

    public void setData(String authorLabel, String descriptionLabel, Long id, boolean isOwned) {
        this.bookId = id;

        this.authorLabel.setText(authorLabel);
        this.descriptionLabel.setText(descriptionLabel);
        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
        this.image.setImage(image);

        this.image.setFitWidth(150); // Adjust width as needed
        this.image.setPreserveRatio(true); // Maintain aspect ratio
        this.authorLabel.setWrapText(true); // Wrap text for long descriptions
        this.descriptionLabel.setWrapText(true);

    }

    public void setOrderDAO (OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @FXML
    public void handleBuy(ActionEvent event) {
        try {
            Order order = Order.builder()
                    .book_id(bookId)
                    .user_id(UserSession.getUser().getId())
                    .order_date(LocalDateTime.now())
                    .quantity(150)
                    .total_amount(150)
                    .build();
            orderDAO.save(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
