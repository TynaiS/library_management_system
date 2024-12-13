package com.example.database_final_javafx.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSalesDTO {
    private Long id;
    private Long authorId;
    private String title;
    private String description;
    private Integer price;
    private Integer stockQuantity;
    private Boolean is_available;
    private Integer totalQuantitySold;
    private Integer totalRevenue;
}
