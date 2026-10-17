# Subscription Billing System

A RESTful backend application built with Java and Spring Boot to manage customers, subscription plans, subscriptions, invoices, and payments.

## Overview

The Subscription Billing System demonstrates backend development using a layered architecture, RESTful APIs, relational database integration, request validation, business rules, and exception handling.

The project is designed to demonstrate core Java backend development skills, including API development, persistence with Spring Data JPA, and automated testing.

## Features

- Customer management
- Subscription plan management
- Subscription creation and tracking
- Invoice management
- Payment management
- RESTful CRUD APIs for the main resources
- PostgreSQL database integration
- Spring Data JPA and Hibernate ORM
- Request validation using Bean Validation
- Centralized exception handling
- Business-rule validation for subscription dates
- Service-layer and controller-layer unit tests
- Interactive API documentation using Swagger UI / OpenAPI

## Technology Stack

- **Language:** Java 11
- **Framework:** Spring Boot 2.7.18
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Testing:** JUnit, Mockito, Spring MockMvc
- **API Testing:** Postman, Swagger UI
- **API Documentation:** Springdoc OpenAPI (`springdoc-openapi-ui` 1.8.0)
- **Version Control:** Git and GitHub
- **IDE:** Eclipse

## Architecture

The application follows a layered architecture to separate request handling, business logic, database operations, and error handling.

- **Controller Layer:** Handles HTTP requests and responses.
- **Service Layer:** Contains business logic and validation rules.
- **Repository Layer:** Performs database operations using Spring Data JPA.
- **Entity Layer:** Represents database entities and their relationships.
- **Exception Handling:** Handles application errors using custom exceptions and centralized exception handling.

## Main Entities

The application manages the following entities:

- **Customer:** Stores customer information.
- **SubscriptionPlan:** Represents available subscription plans.
- **Subscription:** Tracks customer subscriptions and their dates and status.
- **Invoice:** Represents invoices associated with subscriptions.
- **Payment:** Stores payment information associated with invoices.

## API Endpoints

The application provides REST endpoints for managing the main resources.

| Resource | Base Endpoint |
|---|---|
| Customers | `/api/customers` |
| Subscription Plans | `/api/plans` |
| Subscriptions | `/api/subscriptions` |
| Invoices | `/api/invoices` |
| Payments | `/api/payments` |

The controllers provide CRUD operations for these resources. Refer to the application code or Swagger UI for the exact request formats, response schemas, and supported operations.

## Swagger API Documentation

The project uses Springdoc OpenAPI to provide interactive API documentation.

After starting the application, open:

**http://localhost:8081/swagger-ui.html**

Swagger UI allows you to:

- Explore the available REST endpoints.
- Inspect request and response schemas.
- Review API parameters.
- Execute API requests directly from your browser.

## Testing

Unit tests have been written for the service and controller layers using JUnit and Mockito, with Spring MockMvc for controller testing.

### Test Results

**85 tests passed** according to the completed test runs.

| Test Layer | Tests Passed |
|---|---:|
| Service Layer | 60 |
| Controller Layer | 25 |
| **Total** | **85** |

The tests cover scenarios such as retrieving resources, handling missing records, and checking controller behavior.

## Prerequisites

Before running the application, ensure you have:

- Java JDK 11
- Maven
- PostgreSQL
- Git
- Eclipse IDE or another Java-compatible IDE

## Database Configuration

1. Install and start PostgreSQL.
2. Create a database named `subscription_billing`, if that matches your local application configuration.
3. Configure the database connection in `src/main/resources/application.properties`.
4. Set your database credentials securely. Do not commit real passwords or other secrets to GitHub.

Example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_billing
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
server.port=8081
```

**Note:** Adjust the database name, username, port, and password variable to match your actual local configuration. Set the `DB_PASSWORD` environment variable only if your application uses that variable.

The `spring.jpa.hibernate.ddl-auto=update` setting allows Hibernate to update the database schema. For production deployments, use a controlled database migration strategy.

## Run the Application

### 1. Clone the Repository

```bash
git clone https://github.com/mayurimdeore/subscription-billing-system.git
```

### 2. Open the Project

Import the cloned repository into Eclipse as an existing Maven project.

### 3. Configure PostgreSQL

Ensure PostgreSQL is running, the database exists, and the connection properties match your local setup.

### 4. Update Maven Dependencies

In Eclipse, right-click the project and select **Maven → Update Project**.

### 5. Run the Application

Run `SubscriptionBillingApplication.java` as a Java application in Eclipse.

Alternatively, from the project directory, use:

```bash
mvn spring-boot:run
```

### 6. Open Swagger UI

After the application starts successfully, open:

http://localhost:8081/swagger-ui.html

## Future Enhancements

- Automated recurring billing
- Payment gateway integration
- Email notifications
- Authentication and role-based authorization
- Docker support
- Cloud deployment
- Integration testing with a real PostgreSQL database

## Author

**Mayuri Manohar Deore**

- GitHub: [mayurimdeore](https://github.com/mayurimdeore)
- Project Repository: [Subscription Billing System](https://github.com/mayurimdeore/subscription-billing-system)
