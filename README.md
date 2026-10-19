# Subscription Billing System

A RESTful backend application built with Java and Spring Boot to manage customers, subscription plans, subscriptions, invoices, and payments.

## Overview

The Subscription Billing System demonstrates backend development using a layered architecture, RESTful APIs, relational database integration, request validation, business rules, and exception handling.

The project showcases Java backend development skills, including API development, persistence using Spring Data JPA, PostgreSQL integration, and automated testing.

## Features

- Customer management
- Subscription plan management
- Subscription creation and tracking
- Invoice management
- Payment management
- REST APIs for the main resources
- PostgreSQL database integration
- Spring Data JPA and Hibernate ORM
- Request validation using Bean Validation
- Centralized exception handling
- Business-rule validation for subscription dates
- Service-layer and controller-layer unit tests
- Interactive API documentation using Swagger UI and OpenAPI

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java 11 |
| Framework | Spring Boot 2.7.18 |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |
| Testing | JUnit, Mockito, Spring MockMvc |
| API Testing | Swagger UI, Postman |
| API Documentation | Springdoc OpenAPI 1.8.0 |
| Version Control | Git, GitHub |
| IDE | Eclipse |

## Architecture

The application follows a layered architecture that separates request handling, business logic, database operations, and error handling.

- **Controller Layer:** Handles HTTP requests and responses.
- **Service Layer:** Contains business logic and validation rules.
- **Repository Layer:** Performs database operations using Spring Data JPA.
- **Entity Layer:** Represents database entities and their relationships.
- **Exception Handling:** Handles application errors through custom exceptions and centralized exception handling.

## Main Entities

- **Customer:** Stores customer information.
- **SubscriptionPlan:** Represents available subscription plans.
- **Subscription:** Tracks customer subscriptions, dates, and status.
- **Invoice:** Represents invoices associated with subscriptions.
- **Payment:** Stores payment information associated with invoices.

## API Endpoints

| Resource | Base Endpoint |
|---|---|
| Customers | `/api/customers` |
| Subscription Plans | `/api/plans` |
| Subscriptions | `/api/subscriptions` |
| Invoices | `/api/invoices` |
| Payments | `/api/payments` |

Refer to the application controllers and Swagger UI for the exact request formats, response schemas, and supported operations.

## Swagger API Documentation

The project uses Springdoc OpenAPI to provide interactive API documentation.

After starting the application, open:

[Swagger UI](http://localhost:8081/swagger-ui.html)

Swagger UI allows you to:

- Explore available REST endpoints.
- Inspect request and response schemas.
- Review API parameters.
- Execute API requests directly from your browser.

## Testing

Unit tests have been written for the service and controller layers using JUnit and Mockito, with Spring MockMvc for controller testing.

### Automated Test Results

The project documentation records 85 passing tests. Confirm these counts against the latest successful test run before relying on them.

| Test Layer | Tests Passed |
|---|---:|
| Service Layer | 60 |
| Controller Layer | 25 |
| **Total** | **85** |

These tests cover scenarios such as retrieving resources, handling missing records, and checking controller behavior.

### Manual API Testing Progress

The following customer API operations were manually tested through Swagger UI against the running Spring Boot application connected to PostgreSQL.

| Operation | Endpoint | Result |
|---|---|---|
| Retrieve customers | `GET /api/customers` | Passed — HTTP 200 |
| Create customer | `POST /api/customers` | Passed — HTTP 200 |
| Update customer | `PUT /api/customers/{id}` | Passed — HTTP 200 |
| Delete customer | `DELETE /api/customers/{id}` | Passed — HTTP 204 |
| Verify deletion | `GET /api/customers` | Passed — HTTP 200 |

The tests verified customer creation, retrieval, updating, and deletion. After deletion, the test customer no longer appeared in the customer list.

### Subscription Plan API Testing

Tested using Swagger UI against the running application.

| Operation | Endpoint | Result |
|---|---|---|
| Create plan | `POST /api/plans` | Passed — HTTP 200 |
| List plans | `GET /api/plans` | Passed — HTTP 200 |
| Get plan by ID | `GET /api/plans/{id}` | Passed — HTTP 200 before deletion |
| Update plan | `PUT /api/plans/{id}` | Passed — HTTP 200 |
| Delete plan | `DELETE /api/plans/{id}` | HTTP 404 — requires investigation |
| Verify plan absence | `GET /api/plans/{id}` and `GET /api/plans` | HTTP 404 by ID; list returned HTTP 200 without plan ID 4 |

**Test data:** Created plan ID 4 (`Monthly Plan`) with a price of 499 and a duration of 30 days. Updated it to `Monthly Plan Updated`, priced at 599 for 30 days.

**Note:** Plan ID 4 was absent from the subsequent GET-by-ID response and plans list. The DELETE request returned HTTP 404, so the delete endpoint's behavior still requires investigation.

**Testing in progress:** Manual API testing for subscriptions, invoices, and payments.

## Prerequisites

Before running the application, ensure you have:

- Java JDK 11
- Maven
- PostgreSQL
- Git
- Eclipse IDE or another Java-compatible IDE

## Database Configuration

1. Install and start PostgreSQL.
2. Create a database named `subscription_billing`, if it matches your local application configuration.
3. Configure the database connection in `src/main/resources/application.properties`.
4. Configure your database credentials securely.

Example configuration:

```properties
spring.application.name=SubscriptionBillingSystem

spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_billing
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8081
```

**Important:** This example assumes your application uses the `DB_PASSWORD` environment variable. Set it in your local environment or Eclipse run configuration. Never commit real database passwords, access tokens, or other secrets to GitHub.

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

Alternatively, from the project directory, run:

```bash
mvn spring-boot:run
```

Ensure the `DB_PASSWORD` environment variable is configured before starting the application.

### 6. Open Swagger UI

After the application starts successfully, open:

[http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

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
- LinkedIn: [mayuri-deore](https://linkedin.com/in/mayuri-deore)
