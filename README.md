# Library-Secure-Coded

Library-Secure-Coded is a secure coding refactor of the previous Library Management System project.
The main purpose of this version is not only to make the application work, but also to improve how the application handles user input, database access, object persistence, and code separation.

This project focuses on applying secure coding principles to a Java Swing desktop application that uses JDBC, MySQL, DAO pattern, and MVC-style separation.

## Secure Coding Focus

This version improves the original library management project by applying several secure coding practices:

* SQL query protection using `PreparedStatement`
* Input validation before data is processed
* Safer database connection handling
* Separation of UI, controller, DAO, and model responsibilities
* Reduced direct database access from the UI layer
* Safer CRUD flow through controller and DAO layers
* Disabled risky actions before valid table selection
* Environment-based database credentials for safer configuration

## Secure Coding Improvements

### 1. SQL Injection Prevention

The original database operations were improved by using `PreparedStatement`.

Instead of directly combining user input into SQL queries, this version uses parameterized queries. This helps reduce the risk of SQL injection, especially in search, insert, update, and delete operations.

Example concept:

```java
String sql = "SELECT * FROM peminjam WHERE nama LIKE ?";
PreparedStatement ps = connection.prepareStatement(sql);
ps.setString(1, "%" + keyword + "%");
```

This approach separates SQL logic from user input, making database queries safer and more controlled.

### 2. Input Validation

User input from the Java Swing interface is validated before being sent to the controller and DAO layer.

Validation is applied to prevent invalid or empty data from being processed, such as:

* Empty borrower name
* Invalid phone number
* Empty book title
* Invalid publication year
* Empty borrowing data
* Invalid borrowing or return date

This helps ensure that only valid data can enter the application flow.

### 3. Safer Database Connection Handling

The database connection handling was improved to make the application easier to configure and safer to run in different environments.

The application connects to the default local database:

```text
jdbc:mysql://localhost:3306/op2_tipe_b
```

Database credentials can be configured using environment variables:

```text
LIBRARY_DB_USER
LIBRARY_DB_PASSWORD
```

If the environment variables are not available, the application falls back to local development credentials:

```text
username: root
password: 
```

This makes the project safer because database credentials do not need to be hardcoded permanently in the source code.

### 4. Try-With-Resources

Database operations use try-with-resources where possible to make sure resources such as `Connection`, `PreparedStatement`, and `ResultSet` are closed properly.

This helps reduce the risk of resource leaks and makes database access more reliable.

Example concept:

```java
try (
    Connection connection = dbConnection.makeConnection();
    PreparedStatement statement = connection.prepareStatement(sql)
) {
    statement.setString(1, keyword);
    statement.executeUpdate();
}
```

### 5. MVC-Style Separation

This project separates responsibilities into several layers:

```text
View / PanelView  → Handles Java Swing interface
Controller        → Handles application logic
DAO               → Handles database operations
Model             → Represents application data
```

This separation makes the code easier to maintain and reduces the risk of mixing UI logic directly with database logic.

The UI does not directly execute SQL queries. Instead, it sends data through the controller, and the controller communicates with the DAO layer.

### 6. Safer CRUD Flow

CRUD operations are handled more carefully in this version.

For example:

* Update and delete buttons are disabled until a table row is selected.
* Generated book IDs are not directly editable by the user.
* Search operations are handled through DAO methods using safer query execution.
* Insert and update operations validate the input first before saving data.

This helps prevent accidental invalid operations from the GUI.

### 7. Controlled Table Interaction

The application uses table selection to control update and delete behavior.

When no row is selected, update and delete actions are disabled. This prevents the user from accidentally modifying or deleting data without selecting a valid record first.

This improves both usability and safety.

### 8. Object Persistence with DAO Pattern

Object persistence is handled through DAO classes. Each DAO is responsible for communicating with the database and converting database rows into Java objects.

This keeps database logic in one layer instead of spreading SQL code across the application.

Main DAO responsibilities include:

* Insert data
* Update data
* Delete data
* Search data
* Retrieve data for table display
* Retrieve data for dropdown components

## Built With

* Java
* Java Swing
* MySQL
* JDBC
* MySQL Connector
* IntelliJ IDEA

## Main Concepts Applied

* Secure Coding
* Object-Oriented Programming
* Object Persistence
* Encapsulation
* Inheritance
* Polymorphism
* Relation
* Exception Handling
* Input Validation
* Prepared SQL Statements
* DAO Pattern
* Controller Layer
* MySQL Database Integration
* Custom Table Model using `AbstractTableModel`

## Project Structure

```text
src
├── Connection
│   └── DBConnection.java
│
├── Controller
│   ├── BukuController.java
│   ├── BukuControllerE.java
│   ├── KomikController.java
│   ├── NovelController.java
│   ├── PeminjamController.java
│   ├── PeminjamControllerE.java
│   └── PeminjamanController.java
│
├── Dao
│   ├── BukuDAO.java
│   ├── BukuDAOE.java
│   ├── KomikDAO.java
│   ├── NovelDAO.java
│   ├── PeminjamDAO.java
│   ├── PeminjamDAOE.java
│   └── PeminjamanDAO.java
│
├── Exception
│   └── Custom exception classes
│
├── Icon
│   └── Application icons
│
├── InterfaceDao
│   └── DAO interfaces
│
├── Model
│   ├── Buku.java
│   ├── BukuE.java
│   ├── Novel.java
│   ├── NovelE.java
│   ├── Komik.java
│   ├── KomikE.java
│   ├── Peminjam.java
│   ├── PeminjamE.java
│   └── Peminjaman.java
│
├── PanelView
│   ├── BukuMainPanel.java
│   ├── PeminjamMainPanel.java
│   └── PeminjamanMainPanel.java
│
├── Table
│   └── TablePeminjaman.java
│
└── View
    └── MainViewForm.java
```

## Database Configuration

Default database:

```text
op2_tipe_b
```

Default connection URL:

```text
jdbc:mysql://localhost:3306/op2_tipe_b
```

Optional environment variables:

```text
LIBRARY_DB_USER
LIBRARY_DB_PASSWORD
```

If the environment variables are not set, the application uses local development credentials.

## Notes

This repository is a secure-coded version of the previous Library Management System project.
The main focus of this version is improving code safety, database interaction, input handling, and application structure.

The project demonstrates how secure coding practices can be applied to a Java Swing desktop application using JDBC and MySQL.

## Author

Created by Beda Arya Wimala.

