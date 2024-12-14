package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.OrderDAO;
import com.example.database_final_javafx.entity.Order;
import com.example.database_final_javafx.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Label bookItemsLabel;

    private OrderDAO orderDAO;
    private Long bookId;

    public void setData(String authorLabel, String descriptionLabel, Long id, int booksCount) {
        this.bookId = id;

        this.authorLabel.setText(authorLabel);
        this.descriptionLabel.setText(descriptionLabel);
        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
        this.image.setImage(image);

        this.image.setFitWidth(150); // Adjust width as needed
        this.image.setPreserveRatio(true); // Maintain aspect ratio
        this.authorLabel.setWrapText(true); // Wrap text for long descriptions
        this.descriptionLabel.setWrapText(true);
        this.bookItemsLabel.setText(String.valueOf(booksCount));
    }

    public void setOrderDAO (OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @FXML
    public void handleBuy(ActionEvent event) {
        try {
            Order order = Order.builder()
                    .bookId(bookId)
                    .userId(UserSession.getUser().getId())
                    .orderDate(LocalDateTime.now())
                    .quantity(1)
                    .totalAmount(150)
                    .build();
            orderDAO.save(order);

            this.bookItemsLabel.setText( String.valueOf(Integer.parseInt(this.bookItemsLabel.getText()) + 1) );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
