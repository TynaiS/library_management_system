package com.example.database_final_javafx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

        private Long id;
        private Long userId;
        private Long bookId;
        private LocalDateTime orderDate;
        private Integer quantity;
        private Integer totalAmount;

}
