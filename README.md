# Booking Cleaner API

## Overview
The **Booking Cleaner API** is a Spring Boot-based application designed to manage the booking of cleaners for specific time slots. It provides a robust and scalable solution for handling cleaner availability, booking management, and data conversion between DTOs and models. The project follows best practices such as the Strategy Pattern for availability checking and the Adapter Pattern for DTO-Model conversion, ensuring clean and maintainable code.

---

## Features
- **Cleaner Availability Management**: Check the availability of cleaners for specific time slots.
- **Booking Management**: Create and manage bookings for cleaners.
- **DTO-Model Conversion**: Use the Adapter Pattern to ensure separation of concerns between data transfer objects (DTOs) and models.
- **Customizable Availability Strategies**: Implement the Strategy Pattern to handle different availability-checking strategies.
- **OpenAPI Integration**: Automatically generated API documentation using OpenAPI/Swagger.

---

## Technologies Used
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **Gradle** (Build Tool)
- **Lombok** (Simplifies boilerplate code)
- **OpenAPI/Swagger** (API Documentation)

---

## Project Structure
- **`dto`**: Contains Data Transfer Objects (DTOs) for API communication.
- **`model`**: Contains JPA entities representing the database structure.
- **`repository`**: Interfaces for database operations using Spring Data JPA.
- **`service`**: Business logic layer for handling application operations.
- **`helper`**: Utility classes for specific operations (e.g., availability checking).
- **`strategy`**: Implements the Strategy Pattern for availability-checking logic.
- **`adapter`**: Implements the Adapter Pattern for DTO-Model conversion via ModelMapper.
- **`configuration`**: Contains configuration files for JPA, OpenAPI, etc.

---

## Configuration
### OpenAPI Configuration
The OpenAPI documentation is configured in `src/main/java/com/example/justlifetest/configuration/OpenApiConfiguration.java`. You can customize the API title, version, and contact information.

### Database Configuration
The application uses an postgresql ORM database running on Docker for development. You can configure the database in `src/main/resources/application.properties`.

---

## Design Patterns Used
1. **Strategy Pattern**: Applied in `CheckAvailabilityHelper` to handle different availability-checking strategies.
2. **Adapter Pattern**: Used to convert data between DTOs and Models, ensuring separation of concerns.
3. **Factory Pattern**: Used to centralize object creations.
4. **Builder Pattern**: Used to simplify object creation.
5. **Repository Pattern**: Used to abstract database operations cleanly for entities

---

## Swagger and OpenAPI Documentation
  The API documentation is automatically generated using OpenAPI/Swagger. You can access it at the following URLs:  
 - Swagger UI: http://localhost:8080/swagger-ui.html
 - OpenAPI Specification: http://localhost:8080/v3/api-docs

Ensure the application is running on http://localhost:8080 or adjust the URL based on your configured port.

---

## How to Run the Project
### Prerequisites:  
 - Ensure you have Java and Gradle installed.
 - Set up a database if required by the application.
 - Build the Project:  
   Run the following command to build the project:
    ./gradlew build
 - Run the Application:  
   Start the Spring Boot application:
   ./gradlew bootRun
   - Access the Endpoints:  
   The application will be available at http://localhost:8080 (or your configured port).
   Use tools like curl, Postman, or a browser to interact with the APIs.
   - Example Endpoint:
   To invoke the generateVehiclesAndCleaners endpoint:
   curl -X POST http://localhost:8080/api/booking/generateVehiclesAndCleaners
   - 
   
        This endpoint generates 5 vehicles and assigns 5 cleaners to each vehicle for testing purposes.

---

## License
This project is licensed under the **MIT License**. See the `LICENSE` file for details.

---

## Contact
- **Author**: Yahya Ceylani  
- **Email**: [yahya.ceylani@gmail.com](mailto:yahya.ceylani@gmail.com)  
- **GitHub**: [yhycyln](https://github.com/yhycyln)
