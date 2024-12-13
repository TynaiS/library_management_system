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
                is_available BOOLEAN NOT NULL DEFAULT TRUE,
                FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
            );
            """;

        String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS "users" (
                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL UNIQUE,
                account_type VARCHAR(10) CHECK (account_type IN ('ADMIN', 'CUSTOMER')) NOT NULL
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
                FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE,
                FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
            );
            """;

        String insertAuthorsSQL = """
            INSERT INTO author (name) VALUES
                   ('J.K. Rowling'),
                   ('George Orwell'),
                   ('F. Scott Fitzgerald'),
                   ('Jane Austen'),
                   ('Mark Twain'),
                   ('Charles Dickens'),
                   ('William Shakespeare'),
                   ('Emily Dickinson'),
                   ('Haruki Murakami'),
                   ('Gabriel García Márquez'),
                   ('Leo Tolstoy'),
                   ('Franz Kafka'),
                   ('Agatha Christie'),
                   ('Ernest Hemingway'),
                   ('Virginia Woolf'),
                   ('J.R.R. Tolkien'),
                   ('Toni Morrison'),
                   ('C.S. Lewis'),
                   ('Homer'),
                   ('Sylvia Plath'),
                   ('Ian McEwan'),
                   ('Kurt Vonnegut'),
                   ('Margaret Atwood'),
                   ('John Steinbeck'),
                   ('Maya Angelou')
                """;

        String insertBooksSQL = """
                INSERT INTO book (author_id, title, description, price, stock_quantity)
                VALUES
                    (1, 'Harry Potter and the Philosophers Stone', 'A young wizard discovers his magical destiny.', 20, 100),
                    (1, 'Harry Potter and the Chamber of Secrets', 'Harry returns for his second year at Hogwarts and faces new dangers.', 20, 100),
                    (1, 'Harry Potter and the Prisoner of Azkaban', 'Harry faces his past and the escape of a notorious prisoner.', 20, 100),
                    (1, 'Harry Potter and the Goblet of Fire', 'Harry competes in a magical tournament and uncovers dark secrets.', 20, 100),
                    (1, 'Harry Potter and the Order of the Phoenix', 'Harry leads the fight against dark forces at Hogwarts.', 20, 100),
                    (1, 'Harry Potter and the Half-Blood Prince', 'Harry learns about Voldemorts past and the secrets to his defeat.', 20, 100),
                    (1, 'Harry Potter and the Deathly Hallows', 'Harrys final battle against Voldemort unfolds.', 20, 100),

                    (2, '1984', 'A dystopian novel about totalitarian government surveillance and control.', 15, 120),
                    (2, 'Animal Farm', 'A satire on the Russian Revolution and the rise of totalitarianism.', 15, 120),
                    (2, 'Homage to Catalonia', 'Orwells account of his experiences during the Spanish Civil War.', 15, 120),
                   
                    (3, 'The Great Gatsby', 'A story of love, wealth, and social expectations in 1920s America.', 18, 150),
                    (3, 'Tender is the Night', 'A tragic story of a glamorous but destructive marriage.', 18, 150),
                    (3, 'This Side of Paradise', 'A coming-of-age novel about a young man’s romantic and social struggles.', 18, 150),
                   
                    (4, 'Pride and Prejudice', 'A classic novel about love and social class in Regency England.', 12, 200),
                    (4, 'Emma', 'A story about a young woman who plays matchmaker with her friends.', 12, 200),
                    (4, 'Sense and Sensibility', 'A novel about two sisters and their contrasting approaches to love and life.', 12, 200),
                   
                    (5, 'Adventures of Huckleberry Finn', 'A boy and a runaway slave travel down the Mississippi River.', 14, 180),
                    (5, 'The Adventures of Tom Sawyer', 'A young boy’s mischievous adventures in the Mississippi River town.', 14, 180),
                    (5, 'The Prince and the Pauper', 'Two boys, one a prince and one a pauper, switch lives for a time.', 14, 180),
                   
                    (6, 'A Tale of Two Cities', 'A story of sacrifice and redemption set during the French Revolution.', 16, 160),
                    (6, 'Great Expectations', 'A young orphan grows up and learns valuable life lessons in Victorian England.', 16, 160),
                    (6, 'David Copperfield', 'The life story of an orphan who rises from hardship to success.', 16, 160),
                    (7, 'Hamlet', 'A tragedy about revenge, madness, and betrayal in the royal court of Denmark.', 10, 250),
                    (7, 'Macbeth', 'A tragedy about a Scottish noblemans descent into madness and murder.', 10, 250),
                    (7, 'Romeo and Juliet', 'The classic love story between two young lovers from feuding families.', 10, 250),
                   
                    (8, 'Selected Poems', 'A collection of Emily Dickinsons best-known poetry.', 8, 300),
                    (8, 'The Poems of Emily Dickinson', 'A comprehensive collection of her poems covering themes of death, nature, and immortality.', 8, 300),
                   
                    (9, 'Norwegian Wood', 'A novel about love, loss, and coming of age in 1960s Japan.', 18, 130),
                    (9, 'Kafka on the Shore', 'A surreal story blending fantasy, mystery, and coming-of-age themes.', 18, 130),
                    (9, '1Q84', 'A parallel world of mystery, romance, and psychological tension in a Tokyo of the near future.', 18, 130),
                   
                    (10, 'One Hundred Years of Solitude', 'A multi-generational story of a family in the fictional town of Macondo.', 22, 110),
                    (10, 'Love in the Time of Cholera', 'A passionate and complex love story set against the backdrop of aging and illness.', 22, 110),
                    (10, 'Chronicle of a Death Foretold', 'A story of an honor killing and the community’s response to it.', 22, 110),
                   
                    (11, 'War and Peace', 'A historical novel set during the Napoleonic Wars, exploring Russian society.', 20, 140),
                    (11, 'Anna Karenina', 'A tragic story of love, infidelity, and the complexities of Russian aristocracy.', 20, 140),
                    (11, 'The Death of Ivan Ilyich', 'A novella about a man confronting the meaning of life and death.', 20, 140),
                   
                    (12, 'The Trial', 'A surreal, nightmarish novel about a man caught in an inexplicable judicial system.', 14, 170),
                    (12, 'The Metamorphosis', 'A short novel about a man who wakes up as a giant insect and experiences societal rejection.', 14, 170),
                   
                    (13, 'Murder on the Orient Express', 'A detective novel about a murder aboard a luxurious train.', 16, 150),
                    (13, 'The Murder of Roger Ackroyd', 'A classic mystery featuring Hercule Poirot as the detective.', 16, 150),
                    (13, 'The ABC Murders', 'Hercule Poirot investigates a series of murders in alphabetical order.', 16, 150),
                   
                    (14, 'The Old Man and the Sea', 'A novella about an old fisherman struggling to catch a giant marlin.', 12, 180),
                    (14, 'For Whom the Bell Tolls', 'A story about love and loss during the Spanish Civil War.', 12, 180),
                    (14, 'A Farewell to Arms', 'A novel about a romantic relationship amidst the horrors of World War I.', 12, 180),
                   
                    (15, 'Mrs. Dalloway', 'A novel that follows Clarissa Dalloway as she prepares for a party in post-WWI London.', 14, 140),
                    (15, 'To the Lighthouse', 'A modernist novel that explores a family’s experiences over a summer vacation.', 14, 140),
                    (15, 'Orlando', 'A biographical novel about an English nobleman who changes sex and lives through centuries.', 14, 140),
                   
                    (16, 'The Hobbit', 'A young hobbit embarks on an adventure to recover treasure guarded by a dragon.', 18, 120),
                    (16, 'The Fellowship of the Ring', 'The first book in The Lord of the Rings series, following a group on a quest to destroy a dangerous ring.', 18, 120),
                    (16, 'The Two Towers', 'The second book in The Lord of the Rings series, following the ongoing battle against evil.', 18, 120),
                    (16, 'The Return of the King', 'The conclusion to The Lord of the Rings trilogy, where the final battle takes place.', 18, 120),
                   
                    (17, 'Beloved', 'A novel about a former slave who is haunted by the ghost of her daughter.', 22, 130),
                    (17, 'Song of Solomon', 'A coming-of-age novel about an African American man seeking his heritage.', 22, 130),
                    (17, 'Sula', 'A novel about the complex friendship between two African American women.', 22, 130),
                   
                    (18, 'The Chronicles of Narnia', 'A fantasy series following the adventures of children in a magical land.', 25, 110),
                    (18, 'The Lion, the Witch, and the Wardrobe', 'Four children discover a magical world and fight the White Witch.', 25, 110),
                    (18, 'The Horse and His Boy', 'Two children escape from a corrupt regime in a fantastical world.', 25, 110),
                   
                    (19, 'The Iliad', 'An epic poem about the Trojan War and the Greek hero Achilles.', 10, 200),
                    (19, 'The Odyssey', 'An epic poem about Odysseus’ journey home from the Trojan War.', 10, 200),
                   
                    (20, 'The Bell Jar', 'A semi-autobiographical novel about a young woman’s descent into mental illness.', 12, 220),
                    (20, 'Ariel', 'A collection of Sylvia Plath’s poems, dealing with themes of death and rebirth.', 12, 220),
                    (21, 'Amsterdam', 'A novel about two friends who become entangled in a murder plot.', 14, 140),
                    (21, 'Atonement', 'A story about love, guilt, and redemption across several decades.', 14, 140),
                    (21, 'Enduring Love', 'A psychological thriller about obsession and a tragic event.', 14, 140),
                   
                    (22, 'Slaughterhouse-Five', 'A novel about a soldier who becomes "unstuck in time" after surviving a bombing in World War II.', 16, 130),
                    (22, 'Cats Cradle', 'A satirical novel about the arms race and the absurdity of life.', 16, 130),
                    (22, 'Breakfast of Champions', 'A darkly comedic novel about a man who writes a story that brings his characters to life.', 16, 130),
                   
                    (23, 'The Handmaids Tale', 'A dystopian novel about a theocratic society where women are subjugated.', 18, 120),
                    (23, 'Oryx and Crake', 'A speculative novel about the end of humanity and genetic engineering.', 18, 120),
                    (23, 'The Blind Assassin', 'A tale of love and betrayal spanning several generations of a family.', 18, 120),
                   
                    (24, 'The Grapes of Wrath', 'A family struggles to survive during the Great Depression while traveling to California.', 20, 150),
                    (24, 'East of Eden', 'A multi-generational story about good and evil in California’s Salinas Valley.', 20, 150),
                    (24, 'Of Mice and Men', 'A novella about two displaced migrant workers trying to find work during the Great Depression.', 20, 150),
                   
                    (25, 'I Know Why the Caged Bird Sings', 'An autobiography that explores the hardships of race, identity, and childhood.', 18, 180),
                    (25, 'Gather Together in My Name', 'Maya Angelou reflects on her teenage years and her journey toward adulthood.', 18, 180),
                    (25, 'The Heart of a Woman', 'An exploration of Angelou’s struggles as a woman navigating life and motherhood.', 18, 180)
                """;

        String insertUsersSQL = """
      
                INSERT INTO "users" (name, email, password, account_type)
              VALUES
                  ('Alice Johnson', 'alice.johnson@example.com', 'password123A', 'ADMIN'),
                  ('Bob Smith', 'bob.smith@example.com', 'password123B', 'ADMIN'),
                  ('Charlie Brown', 'charlie.brown@example.com', 'password123C', 'CUSTOMER'),
                  ('David White', 'david.white@example.com', 'password123D', 'CUSTOMER'),
                  ('Eve Davis', 'eve.davis@example.com', 'password123E', 'ADMIN'),
                  ('Frank Miller', 'frank.miller@example.com', 'password123F', 'CUSTOMER'),
                  ('Grace Lee', 'grace.lee@example.com', 'password123G', 'CUSTOMER'),
                  ('Hannah Moore', 'hannah.moore@example.com', 'password123H', 'CUSTOMER'),
                  ('Isaac Taylor', 'isaac.taylor@example.com', 'password123I', 'ADMIN'),
                  ('Jack Harris', 'jack.harris@example.com', 'password123J', 'CUSTOMER'),
                  ('Katherine Clark', 'katherine.clark@example.com', 'password123K', 'CUSTOMER'),
                  ('Liam Lewis', 'liam.lewis@example.com', 'password123L', 'ADMIN'),
                  ('Megan Walker', 'megan.walker@example.com', 'password123M', 'CUSTOMER'),
                  ('Nathan Hall', 'nathan.hall@example.com', 'password123N', 'CUSTOMER'),
                  ('Olivia Allen', 'olivia.allen@example.com', 'password123O', 'ADMIN'),
                  ('Paul King', 'paul.king@example.com', 'password123P', 'CUSTOMER'),
                  ('Quinn Scott', 'quinn.scott@example.com', 'password123Q', 'CUSTOMER'),
                  ('Rachel Adams', 'rachel.adams@example.com', 'password123R', 'CUSTOMER'),
                  ('Sam Roberts', 'sam.roberts@example.com', 'password123S', 'CUSTOMER'),
                  ('Tom Wilson', 'tom.wilson@example.com', 'password123T', 'CUSTOMER')
              """;

        String insertOrdersSQL = """
                INSERT INTO "order" (user_id, book_id, order_date, quantity, total_amount) VALUES
                  (10, 16, '2024-11-14 10:32:10', 1, 18),
                  (1, 10, '2024-11-28 07:15:49', 3, 66),
                  (17, 12, '2024-12-07 05:40:30', 2, 28),
                  (3, 15, '2024-11-19 09:30:22', 2, 28),
                  (8, 22, '2024-11-06 11:29:01', 4, 64),
                  (5, 6, '2024-11-18 13:54:35', 5, 80),
                  (12, 8, '2024-11-01 08:55:48', 1, 8),
                  (15, 17, '2024-12-04 15:40:11', 3, 66),
                  (14, 23, '2024-12-01 14:08:05', 5, 60),
                  (4, 10, '2024-11-18 14:04:12', 2, 44),
                  (6, 1, '2024-11-30 04:47:34', 3, 60),
                  (3, 13, '2024-12-06 17:22:16', 4, 64),
                  (20, 7, '2024-12-05 10:14:19', 3, 30),
                  (16, 14, '2024-11-17 16:40:07', 2, 24),
                  (12, 5, '2024-11-26 13:32:54', 1, 14),
                  (13, 21, '2024-11-29 03:46:20', 5, 70),
                  (18, 3, '2024-11-25 04:40:35', 1, 18),
                  (9, 9, '2024-11-23 12:44:44', 2, 36),
                  (20, 24, '2024-11-20 12:03:19', 4, 80),
                  (17, 4, '2024-12-03 07:17:56', 3, 60),
                  (1, 8, '2024-11-29 10:34:55', 3, 24),
                  (18, 22, '2024-11-22 15:34:32', 5, 80),
                  (4, 25, '2024-11-27 18:22:08', 3, 54),
                  (6, 20, '2024-12-02 19:25:37', 2, 24),
                  (7, 11, '2024-11-04 19:31:58', 1, 18),
                  (8, 17, '2024-11-25 09:44:50', 2, 44),
                  (9, 23, '2024-11-11 03:37:40', 4, 72),
                  (5, 15, '2024-11-08 08:19:34', 2, 28),
                  (7, 24, '2024-12-03 03:23:02', 1, 20),
                  (10, 13, '2024-11-06 15:55:44', 4, 64),
                  (9, 16, '2024-11-05 11:11:05', 2, 36),
                  (1, 22, '2024-11-30 19:48:51', 5, 80),
                  (6, 9, '2024-11-20 07:10:41', 1, 18),
                  (18, 19, '2024-11-26 15:34:02', 1, 10),
                  (4, 20, '2024-11-07 09:47:12', 2, 24),
                  (12, 3, '2024-11-30 18:42:11', 5, 90),
                  (14, 5, '2024-11-23 12:08:11', 1, 14),
                  (7, 8, '2024-12-01 17:19:41', 3, 24),
                  (17, 25, '2024-11-26 17:16:24', 4, 72),
                  (3, 12, '2024-11-10 04:02:32', 3, 42),
                  (8, 2, '2024-11-28 06:40:10', 2, 30),
                  (11, 14, '2024-11-18 12:30:53', 2, 24),
                  (19, 15, '2024-12-05 04:40:44', 4, 56),
                  (6, 4, '2024-11-30 11:10:30', 1, 12),
                  (13, 13, '2024-11-03 03:15:23', 1, 16),
                  (8, 10, '2024-12-04 12:21:19', 2, 40),
                  (5, 16, '2024-11-24 11:44:56', 4, 72),
                  (14, 25, '2024-12-02 10:19:03', 2, 36),
                  (11, 21, '2024-11-21 02:15:28', 3, 42),
                  (18, 14, '2024-11-14 08:36:15', 4, 48),
                  (9, 2, '2024-11-27 04:23:10', 5, 75),
                  (12, 5, '2024-11-21 06:30:23', 1, 14),
                  (17, 13, '2024-12-01 12:29:37', 4, 64),
                  (4, 4, '2024-11-12 13:39:34', 5, 60),
                  (3, 15, '2024-11-17 09:15:50', 2, 28)
              """;


        try {
            System.out.println("Connected to the database.");

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createAuthorTableSQL);
                stmt.executeUpdate(createBookTableSQL);
                stmt.executeUpdate(createUserTableSQL);
                stmt.executeUpdate(createOrderTableSQL);


                stmt.executeUpdate(insertAuthorsSQL);
                stmt.executeUpdate(insertBooksSQL);
                stmt.executeUpdate(insertUsersSQL);
                stmt.executeUpdate(insertOrdersSQL);


                System.out.println("Tables created successfully.");
            } catch (SQLException e) {
                System.out.println("Error executing SQL: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


