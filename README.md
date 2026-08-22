# Order-Inventory-Management-System 📦

A robust Java-based application utilizing Spring Boot for managing orders and inventory efficiently.

## ✨ Features

This application provides a comprehensive set of features for managing a product catalog, user authentication, order processing, and inventory control.

- **User Authentication & Authorization**: Secure registration and login for users and administrators with JWT-based authentication. Role-based access control ensures that only authorized users can perform specific actions.
- **Product Management**: Full CRUD operations for products, including adding, updating, and deleting products. Admins can manage the entire product catalog.
- **Order Management**: Users can create orders, and administrators can view and update the status of all orders. Order history is maintained and accessible.
- **Inventory Control**: The system automatically reserves inventory when an order is placed, preventing overselling and ensuring accuracy.
- **API Documentation**: Integrated Swagger UI for easy exploration and testing of API endpoints.
- **Robust Error Handling**: Centralized exception handling provides informative error responses for various scenarios.
- **Containerization**: Docker and Docker Compose configurations are provided for streamlined deployment and development environments.
- **Caching**: Implements caching for product data to improve performance.

## 🚀 Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.2.1
- **Build Tool**: Maven
- **Database**: MySQL 8.4
- **Cache**: Redis 7-alpine
- **Security**: Spring Security, JWT
- **API Documentation**: Springdoc OpenAPI
- **Containerization**: Docker, Docker Compose
- **Other Libraries**: Lombok, Jakarta Validation

## Prerequisites

- Java Development Kit (JDK) 21
- Maven 3.6.0 or higher
- Docker and Docker Compose (for running with containers)

## 🛠️ Installation

### Local Setup (without Docker)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Prasanth-sakunala/Order-Inventory-Management-System.git
   cd Order-Inventory-Management-System
   ```

2. **Set up environment variables**:
   Ensure you have `jwt.secret` and `jwt.expiration` configured in your `src/main/resources/application.properties` or `src/main/resources/application-dev.properties` file.

   Example `application.properties`:
   ```properties
   jwt.secret=your_super_secret_key_that_is_long_enough_for_256
   jwt.expiration=3600000
   ```

3. **Run the application using Maven**:
   ```bash
   ./mvnw spring-boot:run
   ```

### Docker Setup

1. **Ensure Docker and Docker Compose are installed**.

2. **Navigate to the project root directory**.

3. **Build and run the application using Docker Compose**:
   ```bash
   docker-compose up --build
   ```

   This command will:
   - Build the Docker image for the application.
   - Start MySQL and Redis containers.
   - Start the application container, ensuring dependencies (MySQL and Redis) are ready.

## 📚 Usage

This application provides a RESTful API for managing orders and inventory. It supports distinct roles: `ADMIN` and `USER`.

### Authentication

Users and administrators can authenticate via the `/auth` endpoints:

- **Register a new user**: `POST /auth/register`
- **Register a new admin**: `POST /auth/register/admin` (requires admin privileges to execute)
- **Login**: `POST /auth/login`

Upon successful login, a JWT token will be returned, which should be included in the `Authorization` header for subsequent requests (e.g., `Authorization: Bearer <your_token>`).

### Product Management

- **View all products**: `GET /products`
- **View a specific product**: `GET /products/{productId}`

*Admin-only operations:*

- **Add a new product**: `POST /admin/products`
- **Update a product**: `PUT /admin/products/{productId}`
- **Delete a product**: `DELETE /admin/products/{productId}`

### Order Management

- **Create a new order**: `POST /orders` (requires authentication as `USER` or `ADMIN`)
  *Request Body Example:*
  ```json
  {
    "items": [
      { "productId": 1, "quantity": 2 },
      { "productId": 2, "quantity": 1 }
    ]
  }
  ```

- **View your orders**: `GET /orders/my` (requires authentication)
- **View a specific order by ID**: `GET /orders/{orderId}` (requires authentication and ownership of the order)

*Admin-only operations:*

- **View all orders**: `GET /admin/orders`
- **Update order status**: `PUT /admin/orders/{orderId}/status?status=<NEW_STATUS>` (e.g., `PROCESSING`, `COMPLETED`, `CANCELLED`)

### API Documentation

Access the Swagger UI at `http://localhost:8081/swagger-ui.html` (or the configured port) to explore all available API endpoints and their usage.

## 🏗️ Project Structure

```
Order-Inventory-Management-System/
├── Dockerfile
├── docker-compose.yml
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/prasanth/oims/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   ├── service/
│   │   │   ├── impl/
│   │   │   └── util/
│   │   └── resources/
│   │       ├── application-dev.properties
│   │       ├── application.properties
│   │       └── META-INF/
│   │           └── additional-spring-configuration-metadata.json
│   └── test/
│       └── java/com/prasanth/oims/
│           └── OimsApplicationTests.java
└── README.md
```

## 📋 API Reference

This application exposes a comprehensive RESTful API. Key endpoints include:

| Endpoint                       | Method | Description                                         |
|--------------------------------|--------|-----------------------------------------------------|
| `/auth/register`               | POST   | Register a new user.                                |
| `/auth/register/admin`         | POST   | Register a new admin (requires admin privileges).   |
| `/auth/login`                  | POST   | Authenticate and obtain JWT token.                  |
| `/products`                    | GET    | Get a list of all products (paginated).             |
| `/products/{productId}`        | GET    | Get details of a specific product.                  |
| `/admin/products`              | POST   | Add a new product (Admin only).                     |
| `/admin/products/{productId}`  | PUT    | Update an existing product (Admin only).            |
| `/admin/products/{productId}`  | DELETE | Delete a product (Admin only).                      |
| `/orders`                      | POST   | Create a new order.                                 |
| `/orders/my`                   | GET    | Get the current user's orders (paginated).          |
| `/orders/{orderId}`            | GET    | Get a specific order by ID (for the current user).  |
| `/admin/orders`                | GET    | Get all orders (Admin only, paginated).             |
| `/admin/orders/{orderId}/status`| PUT    | Update the status of an order (Admin only).         |
| `/greeting/`                   | GET    | General greeting endpoint.                          |
| `/actuator/health`             | GET    | Health check endpoint.                              |

*Note: Authentication is required for most endpoints after login.*

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork the repository**.
2. **Create a new branch** for your feature (`git checkout -b feature/your-feature-name`).
3. **Make your changes** and commit them (`git commit -m 'Add some feature'`).
4. **Push to the branch** (`git push origin feature/your-feature-name`).
5. **Open a Pull Request**.

Please ensure your code adheres to the project's coding standards and includes relevant tests.

## 📜 License

This project does not specify a license. Please refer to the repository for more details.

## 🔗 Important Links

- **Repository**: [Order-Inventory-Management-System](https://github.com/Prasanth-sakunala/Order-Inventory-Management-System)

## Footer

---

© 2024 Order-Inventory-Management-System. All rights reserved.

- **Repository**: [Order-Inventory-Management-System](https://github.com/Prasanth-sakunala/Order-Inventory-Management-System)
- **Author**: Prasanth Sakunala
- **Contact**: [prasanthsakunala411@gmail.com](mailto:prasanthsakunala411@gmail.com)

---
