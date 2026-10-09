# Subscription Billing System

A backend application built with Java and Spring Boot to manage customers, subscription plans, subscriptions, invoices, and payments through RESTful APIs.

## Overview

The Subscription Billing System demonstrates backend application development using a layered architecture, relational database integration, input validation, business rule enforcement, and centralized exception handling.

## Features

* Customer management
* Subscription plan management
* Subscription creation and tracking
* Invoice management
* Payment management
* RESTful CRUD APIs
* PostgreSQL database integration
* Spring Data JPA and Hibernate ORM
* Request validation using Bean Validation
* Centralized exception handling
* Business rule validation for subscription dates
* Business rule validation for payment amounts
* Consistent error responses for missing subscriptions
* Interactive API documentation with Swagger UI

## Technology Stack

* **Language:** Java 11
* **Framework:** Spring Boot 2.7.18
* **Persistence:** Spring Data JPA, Hibernate
* **Database:** PostgreSQL
* **Build Tool:** Maven
* **API Documentation:** Swagger UI / OpenAPI
* **API Testing:** Postman, Swagger UI
* **Version Control:** Git and GitHub
* **IDE:** Eclipse

## Architecture

The application follows a layered architecture:

* **Controller:** Handles HTTP requests and responses.
* **Service:** Contains business logic and validation rules.
* **Repository:** Performs database operations through Spring Data JPA.
* **Entity:** Represents database entities and relationships.
* **Exception:** Contains custom exceptions and centralized error handling.

## Main Entities

* Customer
* SubscriptionPlan
* Subscription
* Invoice
* Payment

## API Endpoints

The main resource endpoints follow these patterns:

| Resource           | Base endpoint        |
| ------------------ | -------------------- |
| Customers          | `/api/customers`     |
| Subscription Plans | `/api/plans`         |
| Subscriptions      | `/api/subscriptions` |
| Invoices           | `/api/invoices`      |
| Payments           | `/api/payments`      |

Typical CRUD operations include creating, retrieving, updating, and deleting resources. Refer to Swagger UI for the documented operations and request schemas.

## Swagger API Documentation

After starting the application, open:

`http://localhost:8081/swagger-ui.html`

You can explore the documented endpoints, inspect request and response formats, and execute API requests from the browser.

## Prerequisites

* Java JDK 11
* Maven
* PostgreSQL
* Git

## Configuration

Configure your PostgreSQL connection in `src/main/resources/application.properties`.

For example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/subscription_billing
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
server.port=8081
```

Set the `DB_PASSWORD` environment variable to your local PostgreSQL password before running the application. Never commit real credentials to GitHub.

Create the `subscription_billing` database in PostgreSQL before starting the application.

## Run the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/mayurimdeore/subscription-billing-system.git
   ```

2. Open the project in Eclipse or another Java IDE as a Maven project.

3. Configure PostgreSQL and set the `DB_PASSWORD` environment variable.

4. Update Maven dependencies.

5. Run `SubscriptionBillingApplication.java` as a Java application.

6. Open Swagger UI at `http://localhost:8081/swagger-ui.html`.

## Future Enhancements

* Automated recurring billing
* Payment gateway integration
* Email notifications
* Authentication and role-based authorization
* Docker support
* Cloud deployment
* Automated testing with JUnit and Mockito

## Author

**Mayuri Manohar Deore**

GitHub: [mayurimdeore](https://github.com/mayurimdeore)
