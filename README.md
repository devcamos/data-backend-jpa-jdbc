# Data Backend Compare

## Overview

This project demonstrates and compares different data access approaches in Java Spring Boot applications. It provides a comprehensive comparison between **JDBC** and **JPA** implementations, with full CRUD operations, custom queries, and comprehensive test coverage.

## Features

- **Multi-module Maven project** with clear separation of concerns
- **JDBC Implementation** with custom repository pattern
- **JPA Implementation** with Spring Data JPA repositories
- **Comprehensive Test Suite** with 6/6 tests passing ✅
- **H2 In-Memory Database** for testing
- **Spring Boot 3.2.0** with modern Java 17
- **Conditional Bean Creation** with property-based configuration
- **Advanced Query Methods** with case-insensitive search support

## Project Structure

The project is organized into four main modules:

- **common** - Shared entities and utilities
- **jdbc-module** - JDBC implementation with custom repositories
- **jpa-module** - JPA implementation with Spring Data repositories
- **main-module** - Main application with comprehensive tests

The main application includes:
- Data models with BaseEntity and EnhancedEntity
- Repository implementations for both JDBC and JPA
- Utility classes for test data generation
- Comprehensive test suite covering all CRUD operations

## Data Models

### BaseEntity
A simple entity with basic fields designed for JDBC comparison. It includes standard fields like id, field1, field2, and field3 to demonstrate basic CRUD operations.

### EnhancedEntity
A complex entity with advanced features for JPA comparison. It includes comprehensive fields like name, email, description, priority, status flags, categories, external IDs, and timestamp tracking for created and updated dates.

## Data Access Implementations

### JDBC Implementation
- **Custom Repository Pattern** with manual SQL queries
- **RowMapper** for object mapping
- **GeneratedKeyHolder** for proper ID handling
- **Conditional Bean Creation** with property-based configuration
- **Transaction Management** with proper rollback handling

### JPA Implementation
- **Spring Data JPA** repositories
- **Custom Query Methods** with method name conventions
- **@Query Annotations** for complex queries
- **Case-insensitive Search** with proper HQL functions
- **Advanced Query Methods** with aggregation support

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/devcamos/data-backend-compare.git
   cd data-backend-compare
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run tests:**
   ```bash
   mvn test -pl main-module
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run -pl main-module
   ```

## Configuration

The application uses H2 in-memory database for testing. Configuration can be found in:
- `main-module/src/main/resources/application.properties`
- `main-module/src/main/resources/schema.sql`

### Key Properties
- **Database Configuration** - H2 in-memory database with automatic schema generation
- **JPA Settings** - Hibernate with SQL logging enabled
- **Server Configuration** - Running on port 8081
- **Feature Toggles** - Enable/disable JDBC and JPA implementations

## Testing

The project includes comprehensive tests covering:

- ✅ **JDBC CRUD Operations** - Create, Read, Update, Delete
- ✅ **JPA CRUD Operations** - Full entity lifecycle management
- ✅ **Custom Queries** - Complex search and filtering
- ✅ **Repository Injection** - Proper Spring context loading
- ✅ **Model Validation** - Getter/setter functionality
- ✅ **Application Context** - Spring Boot startup

### Test Results
```
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Running Tests
```bash
# Run all tests
mvn test -pl main-module

# Run specific test class
mvn test -pl main-module -Dtest=CrudOperationsTest

# Run with detailed output
mvn test -pl main-module -Dtest=CrudOperationsTest -X
```

## Key Features Demonstrated

### JDBC Features
- Manual SQL query construction
- Result set mapping with RowMapper
- Transaction management
- Generated key retrieval
- Custom repository pattern
- Conditional bean creation

### JPA Features
- Automatic query generation
- Method name conventions
- Custom @Query annotations
- Entity lifecycle management
- Advanced query methods
- Case-insensitive search

### Spring Boot Features
- Multi-module Maven project
- Conditional bean creation
- Property-based configuration
- Comprehensive test support
- H2 in-memory database
- Spring Boot 3.2.0 with Java 17

## Recent Updates

- ✅ **All tests passing** - 6/6 tests successful
- ✅ **Enhanced JDBC repositories** - Proper ID generation and return
- ✅ **Fixed JPA queries** - Case-insensitive search support
- ✅ **Updated dependencies** - Spring Boot 3.2.0 with modern Java
- ✅ **Comprehensive documentation** - Updated README with current features
- ✅ **Improved test coverage** - Enhanced CRUD operations testing
- ✅ **Better error handling** - Proper transaction management

## Performance Considerations

### JDBC Advantages
- **Direct SQL control** - Fine-grained query optimization
- **Minimal overhead** - No ORM layer
- **Memory efficient** - Direct result set mapping
- **Predictable performance** - Explicit query execution

### JPA Advantages
- **Rapid development** - Automatic query generation
- **Type safety** - Compile-time query validation
- **Database agnostic** - Hibernate dialect support
- **Advanced features** - Caching, lazy loading, etc.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is intended for educational purposes and learning data access patterns in Java Spring Boot applications.

## Repository

- **GitHub:** https://github.com/devcamos/data-backend-compare
- **Branch:** `main` (primary development branch)
- **Status:** All tests passing ✅
- **Last Updated:** August 2024
