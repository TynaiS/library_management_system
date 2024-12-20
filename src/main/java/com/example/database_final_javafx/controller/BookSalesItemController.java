package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.NoArgsConstructor;

import java.io.IOException;


@NoArgsConstructor
public class BookSalesItemController {

    MainApplication mainApplication;

    private BookDAO bookDAO;

    @FXML
    private Label authorLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label totalQuantitySoldLabel;

    @FXML
    private Label totalRevenueLabel;

    @FXML
    private Label stockQuantityLabel;

    @FXML Label availabilityLabel;

    @FXML
    private ImageView image;

    private Long bookId;

    public void setData(MainApplication mainApplication, Long bookId, String author, String description, String title, Integer price, Integer totalQuantitySold, Integer totalRevenue, Integer stockQuantity, Boolean availability) {
        this.mainApplication = mainApplication;
        this.bookId = bookId;
        this.authorLabel.setText(author);
        this.descriptionLabel.setText(description);
        this.titleLabel.setText(title);
        this.priceLabel.setText("Price: $" + price);
        this.totalQuantitySoldLabel.setText("Total quantity sold: " + totalQuantitySold);
        this.totalRevenueLabel.setText("Total revenue: $" + totalRevenue);
        this.stockQuantityLabel.setText("Stock quantity: " + stockQuantity);
        this.availabilityLabel.setText("Is available: " + (availability ? "Yes" : "No"));
        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
        this.image.setImage(image);

        this.image.setFitWidth(150); // Adjust width as needed
        this.image.setPreserveRatio(true); // Maintain aspect ratio
        this.authorLabel.setWrapText(true); // Wrap text for long descriptions
        this.descriptionLabel.setWrapText(true);
    }

    public void openEditBookModal(ActionEvent actionEvent) throws IOException {
        mainApplication.showEditBookModal(bookId);
    }
}
