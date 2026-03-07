# OIMS - Open Information Management System

A Java-based application built with Maven for managing information efficiently.

## Project Structure

```
src/
├── main/
│   ├── java/          # Main application source code
│   └── resources/     # Application resources and configuration
└── test/
    └── java/          # Unit tests
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Maven 3.6.0 or higher

## Getting Started

### Build the Project

```bash
mvn clean build
```

### Run the Application

```bash
mvn spring-boot:run
```

### Run Tests

```bash
mvn test
```

### Package the Application

```bash
mvn package
```

This generates an executable JAR file at `target/oims-0.0.1-SNAPSHOT.jar`

## Project Information

- **Version**: 0.0.1-SNAPSHOT
- **Build Tool**: Maven
- **Packaging**: JAR

## Configuration

Application configuration is managed through `src/main/resources/application.properties`

## Development

This project uses Maven Wrapper (`mvnw` and `mvnw.cmd`) for consistent builds across different environments. You can use the wrapper instead of system Maven:

```bash
./mvnw clean build
./mvnw test
```

## CI/CD

GitHub Actions workflows are configured in the `.github/` directory for automated building and testing.

## License

See [HELP.md](HELP.md) for additional information.