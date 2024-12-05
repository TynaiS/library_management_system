package com.example.database_final_javafx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;
    private Long author_id;
    private String title;
    private String description;
    private Double price;
    private Integer stock_quantity;
}
