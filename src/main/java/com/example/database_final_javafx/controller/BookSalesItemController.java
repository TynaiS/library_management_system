package com.example.database_final_javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookSalesItemController {

    @FXML
    private Label authorLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label totalQuantitySoldLabel;

    @FXML
    private Label totalRevenueLabel;

    @FXML
    private ImageView image;

    public void setData(String authorLabel, String descriptionLabel, String titleLabel, Integer totalQuantitySoldLabel, Integer totalRevenueLabel) {
        this.authorLabel.setText(authorLabel);
        this.descriptionLabel.setText(descriptionLabel);
        this.titleLabel.setText(titleLabel);
        this.totalQuantitySoldLabel.setText("Total quantity sold: " + totalQuantitySoldLabel);
        this.totalRevenueLabel.setText("Total revenue: $" + totalRevenueLabel);
        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
        this.image.setImage(image);

        this.image.setFitWidth(150); // Adjust width as needed
        this.image.setPreserveRatio(true); // Maintain aspect ratio
        this.authorLabel.setWrapText(true); // Wrap text for long descriptions
        this.descriptionLabel.setWrapText(true);
    }
}
