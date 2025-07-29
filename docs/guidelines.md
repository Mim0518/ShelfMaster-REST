# Library Management System Development Guidelines

This document provides guidelines and instructions for developers working on the Library Management System project.

## Build and Configuration Instructions

### Prerequisites
- Java 21
- Maven 3.8+
- MySQL 8.0+

### Building the Project
To build the project, run:
```bash
mvn clean install
```

To skip tests during build:
```bash
mvn clean install -DskipTests
```

### Configuration
The application uses Spring profiles for different environments:

1. **Development Environment (default)**
   - Profile: `dev`
   - Database: Local MySQL instance (localhost:3306/biblioteca)
   - Server port: 1611

2. **QA Environment**
   - Profile: `qa`
   - Database: Configured via environment variables (RDS_HOSTNAME, RDS_PORT, RDS_DB_NAME, RDS_USERNAME, RDS_PASSWORD)
   - Server port: 5000

3. **Cloud Environment**
   - Profile: `cloud`
   - Database: AWS RDS instance
   - Server port: 5000

To switch between profiles, modify `application.properties` or use the command line parameter:
```bash
java -jar Biblioteca-0.0.1-SNAPSHOT.war --spring.profiles.active=qa
```

## Testing Information

### Running Tests
To run all tests:
```bash
mvn test
```

To run a specific test class:
```bash
mvn test -Dtest=BookTest
```

To run a specific test method:
```bash
mvn test -Dtest=BookTest#testBookProperties
```

### Adding New Tests

#### Unit Tests
1. Create a test class in the corresponding package under `src/test/java`
2. Use JUnit 5 annotations (@Test, @BeforeEach, etc.)
3. Follow the naming convention: `{ClassUnderTest}Test.java`

Example unit test for a model class:
```java
package com.afdevelopment.biblioteca.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void testBookProperties() {
        Book book = new Book();
        book.setTitle("Test Book");
        assertEquals("Test Book", book.getTitle());
    }
}
```

#### Integration Tests
For integration tests that require Spring context:
1. Add the `@SpringBootTest` annotation to the test class
2. Use `@Autowired` to inject dependencies

Example:
```java
package com.afdevelopment.biblioteca.service;

import com.afdevelopment.biblioteca.model.Book;
import com.afdevelopment.biblioteca.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Test
    void testFindBookById() {
        // Test implementation
    }
}
```

#### Security Tests
For testing secured endpoints:
```java
@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/api/books")
                .header("Authorization", "Bearer " + getValidToken()))
                .andExpect(status().isOk());
    }
}
```

## Additional Development Information

### Code Structure
- **Controllers**: Handle HTTP requests and responses
- **Services**: Contain business logic
- **Repositories**: Interface with the database
- **Models**: JPA entities representing database tables
- **DTOs**: Data transfer objects for API requests/responses
- **Security**: JWT authentication and authorization

### Database Relationships
- The application uses JPA for database operations
- Note that some relationships (e.g., Book-Shelf) currently use integer IDs instead of proper JPA relationships

### Security Implementation
- JWT-based authentication
- Token expiration set to 10 days (configurable)
- JWT secret is configured in application properties

### Known Issues and Improvement Areas
- Refer to `docs/tasks.md` for a comprehensive list of improvement tasks
- Security improvements needed (JWT secret handling, token expiration)
- Architecture improvements (proper JPA relationships, exception handling)
- Code quality improvements (standardize naming conventions, reduce duplication)

### Naming Conventions
- The codebase currently mixes Spanish and English terms
- New code should use English for consistency