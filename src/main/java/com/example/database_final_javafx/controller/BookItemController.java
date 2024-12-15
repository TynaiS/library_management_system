package com.example.database_final_javafx.controller;

import com.example.database_final_javafx.DAO.BookDAO;
import com.example.database_final_javafx.DAO.OrderDAO;
import com.example.database_final_javafx.MainApplication;
import com.example.database_final_javafx.entity.Book;
import com.example.database_final_javafx.utils.AlertShower;
import com.example.database_final_javafx.utils.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;

@Data
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

    @FXML
    private Label bookItemsAvailableLabel;

    private OrderDAO orderDAO;
    private BookDAO bookDAO;
    private Book book;

    private MainApplication mainApplication;

    public void setData(String author, Book book, int booksCount) {
        this.book = book;

        this.authorLabel.setText(author);
        this.authorLabel.setWrapText(true); // Wrap text for long descriptions

        this.descriptionLabel.setText(book.getDescription());
        this.descriptionLabel.setWrapText(true);

        this.bookItemsLabel.setText(String.valueOf(booksCount));

        this.bookItemsAvailableLabel.setText(String.valueOf(book.getStockQuantity()));

        this.buyButton.setText( "Buy " + book.getPrice() + "$");

        Image image = new Image(getClass().getResourceAsStream("/img/it.jpg"));
        this.image.setImage(image);
        this.image.setPreserveRatio(false);
        this.image.setFitWidth(150);
        this.image.setPreserveRatio(true);
    }


    @FXML
    public void handleBuy(ActionEvent event) {
        try {
            mainApplication.showBuyBookModal(book.getId(), book.getPrice());

            int quantity = orderDAO.getBookQuantityOwnedByUser(UserSession.getUser().getId(), book.getId());
            Book book = bookDAO.findById(this.book.getId());

            this.bookItemsLabel.setText( String.valueOf(quantity) );
            this.bookItemsAvailableLabel.setText( String.valueOf(book.getStockQuantity()) );

        } catch (Exception e) {
            AlertShower alertShower = AlertShower.builder()
                    .alertType(Alert.AlertType.ERROR)
                    .title("Application Error")
                    .header("Application Error")
                    .text("Something went wrong, please try again or wait")
                    .build();
            alertShower.showAlert();
        }
    }
}