# Biblioteca

Welcome to the Biblioteca repository! This is a Maven project that uses Spring Boot to create a library management system. The project is packaged as a WAR file and uses Java version 21. The project version is 0.0.1-SNAPSHOT, indicating it's a development version. The parent POM is from the Spring Boot project, version 3.2.0.

## :file_folder: Files Overview

### pom.xml :page_with_curl:

This is the Project Object Model (POM) file for our Maven project. It contains information about the project and configuration details used by Maven to build the project. It's not a code file, so it doesn't contain any functions or methods. However, it does specify several dependencies that our project needs, including various Spring Boot starters, MySQL JDBC driver, and Apache Log4j 2.

### BibliotecaApplication.java :rocket:

This is the main application file for our Spring Boot application. It's responsible for starting the application. The main method is the entry point of the application. It starts the Spring Boot application by calling the run method on the SpringApplication class, passing in BibliotecaApplication.class and the command-line arguments.

### ServletInitializer.java :gear:

This file is used to initialize the servlet for the Biblioteca application. It extends the SpringBootServletInitializer class, which is a support class for creating a servlet 3.0 configuration in order to deploy a Spring application.

### BookController.java :book:

This file is a controller for managing books in a library system. It provides endpoints for searching books by ID, ISBN, and author, saving a new book, and deleting a book by ISBN.

### GlobalExceptionHandler.java :warning:

This file is a global exception handler for our book library application. It handles exceptions that occur throughout the application and returns appropriate HTTP responses.

### ShelfController.java :clipboard:

This file appears to be a controller class for a 'Shelf' object in a library management system. However, as there are no methods or functions defined within the class, its specific functionality cannot be determined from the provided information.

### UserController.java :bust_in_silhouette:

This file appears to be a controller in a Java application, specifically for user-related operations. However, as there are no methods or functions defined in the file, its specific functionality cannot be determined.

### BookAlreadyExistsException.java :no_entry_sign:

This file defines a custom exception class named 'BookAlreadyExistsException'. This exception is thrown when an attempt is made to add a book that already exists in the system.

### BookNotFoundException.java :mag_right:

This file defines a custom exception class named 'BookNotFoundException'. This exception is thrown when a book is not found in the library.

### Book.java :open_book:

This file defines the Book entity in a library management system. It represents a book with properties such as id, isbn, title, author, pages, edition, shelfId, lent status, language code, and publisher.

### Shelf.java :bookmark_tabs:

This file defines the Shelf entity in a library management system. A Shelf has an ID and a location.

### User.java :busts_in_silhouette:

This file defines the User entity in the system. It represents a user with properties like ID, CURP (Unique Population Registry Code), name, maternal surname, paternal surname, and phone number.

### BookRepository.java :card_index_dividers:

This file serves as the repository interface for the Book model in the application. It provides methods for performing CRUD operations on the Book model.

### GetBook.java :inbox_tray:

This file defines a class, GetBook, which is used to represent a book in the system. It contains information about the book such as its ID, ISBN, and author.

### DetailFail.java :x:

This file is a part of the response package in the software. It is designed to handle and store error details in a list.

### DetailResponse.java :white_check_mark:

This file defines a class, DetailResponse, which is used to encapsulate response details, specifically a code and a business meaning.

### Error.java :exclamation:

This file defines the Error class which is used to encapsulate error information in a standardized format.

### BookService.java :books:

This file is a service class in a library management system. It provides methods for finding, saving, and deleting books in the system.

### ShelfService.java :clipboard:

The file does not contain any methods or functions, so it's not possible to determine its purpose from the provided content. However, based on the name, it can be inferred that it might be used to manage operations related to a 'Shelf' in a 'Biblioteca' (library) system.

### UserService.java :bust_in_silhouette:

The UserService.java file appears to be a placeholder or a stub for a service class in a Java application. As it is currently empty, it does not have any specific functionality.

### application.properties :wrench:

This file is used to configure the database connection and server port for a Spring Boot application. It specifies the database URL, username, password, driver class name, and the Hibernate dialect to be used.

### BibliotecaApplicationTests.java :test_tube:

This file is used to test the context loading of the Biblioteca Application. It is a part of the test suite for the Biblioteca Application.

## :rocket: Getting Started

To start the application, you would typically run the BibliotecaApplication.java file from your IDE or from the command line with a command like:
```
java -jar target/myproject-0.0.1-SNAPSHOT.jar
```
(Replace "myproject-0.0.1-SNAPSHOT.jar" with the actual name of your jar file.)

## :warning: Notes

Please note that the database password is currently blank ("spring.datasource.password="). This should be replaced with the actual password for the database before running the application in a production environment.

## :sparkles: Happy Coding! :sparkles:
