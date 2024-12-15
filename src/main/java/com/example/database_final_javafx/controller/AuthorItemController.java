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

    public void setData(MainApplication mainApplication, String name, Integer totalSales,  Integer totalRevenue, Integer totalBooks, Integer totalAvailableBooks) {
        this.mainApplication = mainApplication;
        this.nameLabel.setText(name);
        this.totalSalesLabel.setText("Total sales: " + totalSales);
        this.totalRevenueLabel.setText("Total revenue: $" + totalRevenue);
        this.totalBooksLabel.setText("Total num of books" + totalBooks);
        this.totalAvailableBooksLabel.setText("Total num of available books: " + totalAvailableBooks);

    }
}
