package com.example.database_final_javafx.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BooksOwnedByUserDTO {
    private Long userId;
    private int ownedBooksCount;
    private Long bookId;
}
