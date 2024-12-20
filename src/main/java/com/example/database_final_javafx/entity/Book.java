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
    private Long authorId;
    private String title;
    private String description;
    private Integer price;
    private Integer stockQuantity;
    private Boolean isAvailable;
}
