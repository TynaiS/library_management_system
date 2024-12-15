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
public class AuthorItemController{

    @FXML
    public Label nameLabel;
    @FXML
    public Label totalSalesLabel;
    @FXML
    public Label totalRevenueLabel;
    @FXML
    public Label totalBooksLabel;
    @FXML
    public Label totalAvailableBooksLabel;
    MainApplication mainApplication;

    private BookDAO bookDAO;

    public void setData(MainApplication mainApplication, String name, Integer author, String description, String title, Integer price, Integer totalQuantitySold, Integer totalRevenue, Integer stockQuantity, Boolean availability) {

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
