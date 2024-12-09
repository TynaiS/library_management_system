package com.example.database_final_javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookItemController{

    @FXML
    private Label authorLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView image;

//    public BookItemController(String authorLabel, String descriptionLabel, String imgSource) {
//        this.authorLabel.setText(authorLabel);
//        this.descriptionLabel.setText(descriptionLabel);
//        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
//        this.image.setImage(image);
//    }

    public void setData(String authorLabel, String descriptionLabel) {
        this.authorLabel.setText(authorLabel);
        this.descriptionLabel.setText(descriptionLabel);
        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
        this.image.setImage(image);

        this.image.setFitWidth(150); // Adjust width as needed
        this.image.setPreserveRatio(true); // Maintain aspect ratio
        this.authorLabel.setWrapText(true); // Wrap text for long descriptions
        this.descriptionLabel.setWrapText(true);
    }

}
