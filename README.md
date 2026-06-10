# Library-Secure-Coded

Library-Secure-Coded is a Java Swing desktop application designed to manage library data, including books, borrowers, and borrowing records. This version applies secure coding improvements such as prepared SQL statements, input validation, safer database connection handling, and clearer MVC separation.

The application provides a simple interface for managing library data through CRUD operations, search features, and database integration using JDBC and the DAO pattern.

## Features

### Borrower Management

* Add new borrower data
* Update existing borrower data
* Delete book data
* Search borrower data
* Display borrower records in a table

### Book Management

* Add new book data
* Update existing book data
* Delete borrower data
* Search book data
* Manage different book types such as Novel and Comic

### Borrowing Management

* Add borrowing records
* Select borrower and book data
* Select borrowing and return dates with a calendar picker
* Select book genre using checkboxes
* Select book location using radio buttons
* Display borrowing records in a table

## Secure Coding Improvements

* Uses `PreparedStatement` to reduce SQL injection risk.
* Validates input before sending data to the model and DAO layer.
* Uses try-with-resources for database resources.
* Reads database credentials from environment variables when available.
* Separates UI, controller, DAO, and model responsibilities.
* Disables update and delete actions until a table row is selected.
* Prevents direct editing of generated book IDs.

## Built With

* Java
* Java Swing
* MySQL
* JDBC
* MySQL Connector
* IntelliJ IDEA

## Main Concepts Implemented

* Object-Oriented Programming
* Encapsulation
* Inheritance
* Polymorphism
* Relation
* Exception Handling
* Object Persistence
* CRUD Operations
* Search Feature
* DAO Pattern
* Controller Layer
* Java Swing GUI
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

## Database Design

The application uses a MySQL database to store and manage library data. The main tables include:

* `buku`
* `novel`
* `komik`
* `peminjam`
* `peminjaman`

The `peminjaman` table stores borrowing records and connects borrower data with book data. It contains information such as borrower ID, book ID, borrowing date, return date, book genre, and book location.

## Database Configuration

By default, the application connects to:

```text
jdbc:mysql://localhost:3306/op2_tipe_b
```

Database credentials can be configured with environment variables:

```text
LIBRARY_DB_USER
LIBRARY_DB_PASSWORD
```

If those variables are not set, the application falls back to `root` with an empty password for local development.

## Notes

This project focuses on implementing Object Persistence in a desktop-based Java application while applying secure coding practices. The data is displayed in the GUI and stored, retrieved, updated, and deleted from a MySQL database using JDBC and the DAO pattern.

## Author

Created by Beda Arya Wimala.
