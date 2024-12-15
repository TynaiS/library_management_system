package com.example.database_final_javafx.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    private String name;
    private Integer totalSales;
    private Integer totalRevenue;
    private Integer totalBooks;
    private Integer totalAvailableBooks;
}
