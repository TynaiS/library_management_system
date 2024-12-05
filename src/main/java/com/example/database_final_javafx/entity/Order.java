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
        private Long user_id;
        private Long book_id;
        private LocalDateTime order_date;
        private Integer quantity;
        private Integer total_amount;

}
