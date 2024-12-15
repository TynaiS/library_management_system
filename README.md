# Project Documentation: Library Management System

## 1. Project Title
**Library Management System**

---

## 2. Project Overview
This project implements an Library Management System using a relational database to manage books, authors, customers, and orders. The system consists of three main components:

- **Models**: Represent the database entities.
- **DAO (Data Access Object)**: Handle database interactions such as SELECT, INSERT, UPDATE, and DELETE queries.
- **Controllers**: Manage user interactions through JavaFX scenes.

---

## 3. Architecture Overview

### 3.1. Database
The database schema includes the following tables:

- **author**: Represents book authors.
- **book**: Contains details of books.
- **users**: Stores customer details.
- **order**: Tracks customer orders.

Refer to the SQL queries documentation for detailed table definitions.

### 3.2. Models
Each table in the database corresponds to a Java class in the Models package. These classes include fields matching the database columns, as well as getter and setter methods.

#### Example: Book Model
```java
public class Book {
    private long id;
    private long authorId;
    private String title;
    private String description;
    private int price;
    private int stockQuantity;

    // Getters and Setters
}
```

### 3.3. DAO (Data Access Object)
DAO classes handle all database operations. They utilize JDBC to interact with the PostgreSQL database. To not make the same queries for all tables Generic DAO class has been created and all DAO classes extend this class.

#### Generic DAO Class
```java
public abstract class GenericDao<T> {

    protected Connection connection;

    // Constructor to initialize database connection
    public GenericDao(Connection connection) {
        this.connection = connection;
    }

    // Save an entity to the database
    public void save(T entity) throws SQLException {
        String sql = generateInsertSQL(entity);  // Implement this method in child classes
        System.out.println(sql);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setInsertParameters(stmt, entity);  // Implement this in child classes
            stmt.executeUpdate();
        }
    }

    // Update an entity in the database
    public void update(T entity) throws SQLException {
        String sql = generateUpdateSQL(entity);  // Implement this method in child classes
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setUpdateParameters(stmt, entity);  // Implement this in child classes
            stmt.executeUpdate();
        }
    }

    // Delete an entity from the database
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Find an entity by its ID
    public T findById(int id) throws SQLException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);  // Implement this method in child classes
            }
            return null;
        }
    }

    // Find all entities in the table
    public List<T> findAll() throws SQLException {
        List<T> result = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(mapResultSetToEntity(rs));  // Implement this method in child classes
            }
        }
        return result;
    }

    // Abstract methods to be implemented by child classes
    protected abstract String getTableName();  // To get the table name (Book, User, etc.)
    protected abstract String generateInsertSQL(T entity);  // To generate the insert SQL
    protected abstract String generateUpdateSQL(T entity);  // To generate the update SQL
    protected abstract void setInsertParameters(PreparedStatement stmt, T entity) throws SQLException;  // Set parameters for insert
    protected abstract void setUpdateParameters(PreparedStatement stmt, T entity) throws SQLException;  // Set parameters for update
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;  // Map the ResultSet to the entity
}
```

#### Example: BookDAO
```java
public class BookDAO extends GenericDao<Book> {

    public BookDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "book";
    }

    @Override
    protected String generateInsertSQL(Book entity) {
        return "INSERT INTO book (id, author_id, title, description, price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String generateUpdateSQL(Book entity) {
        return "UPDATE book SET author_id = ?, title = ?, description = ?, price = ?, stock_quantity = ? WHERE id = ?";
    }

    @Override
    protected void setInsertParameters(PreparedStatement stmt, Book entity) throws SQLException {
        stmt.setLong(1, entity.getId());
        stmt.setLong(2, entity.getAuthorId());
        stmt.setString(3, entity.getTitle());
        stmt.setString(4, entity.getDescription());
        stmt.setInt(5, entity.getPrice());
        stmt.setInt(6, entity.getStockQuantity());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement stmt, Book entity) throws SQLException {
        stmt.setLong(1, entity.getAuthorId());
        stmt.setString(2, entity.getTitle());
        stmt.setString(3, entity.getDescription());
        stmt.setInt(4, entity.getPrice());
        stmt.setInt(5, entity.getStockQuantity());
        stmt.setLong(6, entity.getId());
    }

    @Override
    protected Book mapResultSetToEntity(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setAuthorId(rs.getLong("author_id"));
        book.setTitle(rs.getString("title"));
        book.setDescription(rs.getString("description"));
        book.setPrice(rs.getInt("price"));
        book.setStockQuantity(rs.getInt("stock_quantity"));
        return book;
    }
}
```

### 3.4. Controllers
Controllers manage interactions between the user interface and the backend. Each scene in the JavaFX application corresponds to a controller class.

#### Example: BookController
```java
public class BookController {
    @FXML
    private TableView<Book> bookTable;

    private BookDAO bookDAO = new BookDAO();

    public void loadBooks() {
        List<Book> books = bookDAO.findAll();
        bookTable.getItems().setAll(books);
    }

    public void addBook() {
        // Logic to add a new book via a form
    }

    public void deleteSelectedBook() {
        // Logic to delete selected book from the table
    }
}
```

---

## 4. Implementation Details

### 4.1. Tools and Technologies
- **Database**: PostgreSQL
- **Programming Language**: Java
- **UI Framework**: JavaFX
- **Build Tool**: Maven

### 4.2. Workflow
1. **Models** define the data structure.
2. **DAO** manages the communication with the database.
3. **Controllers** handle user actions and update the UI.

### 4.3. Key Features
- **Search Books**: Search books by title, author, or keywords.
- **Place Orders**: Select books, specify quantity, and place an order.
- **View Orders**: View all orders for a specific customer.
- **Update Stock**: Modify stock quantities for books.

---

## 5. Queries and Examples
Refer to the SQL Queries Documentation for sample SQL queries used in the DAO layer.

---

## 6. Future Enhancements
- Add adding user feature for admin.
- Enhance the UI with advanced filtering and sorting options.
- Enhance admin stattistics.

---

## 7. Conclusion
This system provides a scalable and robust framework for managing an online library. By separating the responsibilities into Models, DAO, and Controllers,


