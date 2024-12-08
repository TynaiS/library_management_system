package com.example.database_final_javafx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTables {

    private Connection conn;

    public CreateTables(Connection conn) {
        this.conn = conn;
    }
    public void create() {
        // SQL statement to create the Author table
        String createAuthorTableSQL = """
            CREATE TABLE IF NOT EXISTS author (
                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                name VARCHAR(255) NOT NULL
            );
            """;

        String createBookTableSQL = """
            CREATE TABLE IF NOT EXISTS book (
                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                author_id BIGINT NOT NULL,
                title VARCHAR(255) NOT NULL,
                description VARCHAR(255) NOT NULL,
                price INT NOT NULL,
                stock_quantity INT NOT NULL,
                FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
            );
            """;

        String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS "users" (
                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL UNIQUE,
                account_type VARCHAR(10) CHECK (account_type IN ('ADMIN', 'USER')) NOT NULL
            );
            """;

        String createOrderTableSQL = """
            CREATE TABLE IF NOT EXISTS "order" (
                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                user_id BIGINT NOT NULL,
                book_id BIGINT NOT NULL,
                order_date TIMESTAMP NOT NULL,
                quantity INT NOT NULL,
                total_amount INT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
                FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
            );
            """;



        String insertAuthorsSQL = """
                INSERT INTO author (name) VALUES
                    ('J.K. Rowling'),
                    ('George Orwell'),
                    ('J.R.R. Tolkien'),
                    ('Agatha Christie'),
                    ('Mark Twain');
                """;

        String insertBooksSQL = """
                INSERT INTO book (author_id, title, description, price, stock_quantity) VALUES
                    (1, 'Harry Potter and the Sorcerer''s Stone', 'The first book in the Harry Potter series.', 20, 100),
                    (1, 'Harry Potter and the Chamber of Secrets', 'The second book in the Harry Potter series.', 22, 80),
                    (2, '1984', 'A dystopian novel about totalitarian regime.', 15, 150),
                    (2, 'Animal Farm', 'A satirical novella about Soviet Communism.', 12, 200),
                    (3, 'The Hobbit', 'A fantasy novel about the adventures of Bilbo Baggins.', 18, 120),
                    (3, 'The Lord of the Rings', 'A fantasy trilogy set in Middle-earth.', 35, 50),
                    (4, 'Murder on the Orient Express', 'A detective novel featuring Hercule Poirot.', 25, 90),
                    (5, 'The Adventures of Tom Sawyer', 'A coming-of-age novel featuring Tom Sawyer.', 10, 180);
                """;

        String insertUsersSQL = """
                INSERT INTO "users" (name, email, password, account_type) VALUES
                    ('Admin User', 'admin@example.com', 'adminpass', 'ADMIN'),
                    ('John Doe', 'john@example.com', 'johnpass', 'USER'),
                    ('Jane Smith', 'jane@example.com', 'janepass', 'USER'),
                    ('Alice Brown', 'alice@example.com', 'alicepass', 'USER'),
                    ('Bob White', 'bob@example.com', 'bobpass', 'USER');
                """;

        String insertOrdersSQL = """
                INSERT INTO "order" (user_id, book_id, order_date, quantity, total_amount) VALUES
                    (2, 1, '2024-12-01 10:30:00', 1, 20),
                    (3, 2, '2024-12-02 14:00:00', 2, 44),
                    (4, 3, '2024-12-03 09:15:00', 1, 15),
                    (5, 8, '2024-12-04 16:45:00', 3, 30),
                    (2, 4, '2024-12-01 18:00:00', 1, 12),
                    (3, 7, '2024-12-02 11:30:00', 1, 25);
                """;


        try {
            System.out.println("Connected to the database.");

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createAuthorTableSQL);
                stmt.executeUpdate(createBookTableSQL);
                stmt.executeUpdate(createUserTableSQL);
                stmt.executeUpdate(createOrderTableSQL);
//
//
//                stmt.executeUpdate(insertAuthorsSQL);
//                stmt.executeUpdate(insertBooksSQL);
//                stmt.executeUpdate(insertUsersSQL);
//                stmt.executeUpdate(insertOrdersSQL);


                System.out.println("Tables created successfully.");
            } catch (SQLException e) {
                System.out.println("Error executing SQL: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


