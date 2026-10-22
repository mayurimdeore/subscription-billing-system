# Subscription Billing System

A RESTful backend application developed using **Java 11, Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL** to manage customers, subscription plans, subscriptions, invoices, and payments.

The project demonstrates practical Java backend development skills, including REST API design, layered architecture, database persistence, business logic implementation, request validation, exception handling, and automated testing.

## Overview

The Subscription Billing System is a backend application designed to manage subscription-based billing operations.

It allows users to manage customers, create subscription plans, maintain customer subscriptions, generate and manage invoices, and record payments.

The application follows a layered architecture to separate request handling, business logic, database operations, and exception handling.

### Key Highlights

- Developed RESTful APIs using Java 11 and Spring Boot.
- Implemented database persistence using PostgreSQL and Spring Data JPA.
- Used Hibernate ORM for entity mapping and database interactions.
- Implemented input validation and centralized exception handling.
- Developed invoice payment tracking based on completed payments.
- Implemented payment status validation.
- Performed manual API testing using Swagger UI.
- Successfully executed **85 automated tests with zero failures**.
- Used Git and GitHub for version control.

## Features

### Customer Management

- Create customers.
- Retrieve customer details.
- Update customer information.
- Delete customer records.

### Subscription Plan Management

- Create subscription plans.
- Retrieve available subscription plans.
- Update subscription plan details.
- Manage plan pricing and duration.
- Delete subscription plans.

### Subscription Management

- Create customer subscriptions.
- Associate customers with subscription plans.
- Track subscription dates and status.
- Apply subscription-related business rules and validation.

### Invoice Management

- Manage invoices associated with subscriptions.
- Track invoice amounts and payment status.
- Update invoice status based on completed payments.
- Support `PENDING`, `PARTIALLY_PAID`, and `PAID` invoice states.

### Payment Management

- Record payments against invoices.
- Retrieve and manage payment records.
- Validate payment status.
- Support `pending` and `completed` payment statuses.
- Reject unsupported payment statuses.
- Calculate completed payment totals for invoices.

### Additional Features

- RESTful API development.
- PostgreSQL database integration.
- Spring Data JPA and Hibernate ORM.
- Bean Validation.
- Centralized exception handling.
- JUnit 5 and Mockito automated testing.
- Spring MockMvc controller testing.
- Swagger UI and OpenAPI documentation.
- Git and GitHub version control.

## Technology Stack

| Category | Technology |
|---|---|
| Programming Language | Java 11 |
| Backend Framework | Spring Boot 2.7.18 |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |
| Unit Testing | JUnit 5, Mockito |
| Controller Testing | Spring MockMvc |
| Manual API Testing | Swagger UI |
| API Documentation | Springdoc OpenAPI 1.8.0 |
| Version Control | Git, GitHub |
| Development IDE | Eclipse |

## Architecture

The application follows a layered architecture that separates HTTP request handling, business logic, database operations, and exception handling.

### Controller Layer

Handles incoming HTTP requests, validates request data, and returns appropriate HTTP responses.

### Service Layer

Contains business logic for managing customers, subscriptions, invoices, and payments.

### Repository Layer

Uses Spring Data JPA repositories to interact with the PostgreSQL database.

### Entity Layer

Represents application data models and their relationships.

### Exception Handling

Uses custom exceptions and centralized exception handling to manage application errors.

### Application Request Flow

```text
Client / Swagger UI
        |
        v
REST Controller
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
PostgreSQL Database
```

## Main Entities

| Entity | Description |
|---|---|
| Customer | Stores customer information |
| SubscriptionPlan | Stores subscription plan details, pricing, and duration |
| Subscription | Tracks customer subscriptions and their dates |
| Invoice | Stores invoice details associated with subscriptions |
| Payment | Records payments associated with invoices |

## API Endpoints

| Resource | Base Endpoint |
|---|---|
| Customers | `/api/customers` |
| Subscription Plans | `/api/plans` |
| Subscriptions | `/api/subscriptions` |
| Invoices | `/api/invoices` |
| Payments | `/api/payments` |

Refer to the application controllers and Swagger UI for the exact request formats, response schemas, and supported HTTP operations.

## Subscription Billing Workflow

The application supports a billing workflow involving customers, subscription plans, subscriptions, invoices, and payments.

```text
Create Customer
      |
      v
Create Subscription Plan
      |
      v
Create Subscription
      |
      v
Create Invoice
      |
      v
Record Payment
      |
      v
Calculate Completed Payments
      |
      v
Update Invoice Payment Status
```

### Invoice Payment Status

Invoice payment status is determined by the amount of completed payments associated with the invoice.

| Payment Condition | Invoice Status |
|---|---|
| No completed payment amount | `PENDING` |
| Completed payments cover part of the invoice | `PARTIALLY_PAID` |
| Completed payments cover the full invoice amount | `PAID` |

Only payments with a `completed` status contribute to the completed payment total.

### Payment Validation

The application accepts the following payment statuses:

- `pending`
- `completed`

Unsupported payment statuses are rejected through Bean Validation.

For example, a payment request containing `"status": "SUCCESS"` returns HTTP 400 because the value does not match the accepted payment statuses.

## Swagger API Documentation

The project uses Springdoc OpenAPI to provide interactive API documentation.

After starting the application, open:

**Swagger UI:** http://localhost:8081/swagger-ui.html

Swagger UI allows developers to:

- Explore available REST endpoints.
- Inspect request and response schemas.
- Review API parameters.
- Execute API requests directly from a browser.
- Test API responses and HTTP status codes.

## Testing

The application includes automated tests for the service and controller layers using JUnit 5, Mockito, and Spring MockMvc.

### Automated Test Results

The complete Maven test suite was successfully executed on **October 22, 2026**.

| Test Layer | Tests Passed |
|---|---:|
| Service Layer | 60 |
| Controller Layer | 25 |
| **Total** | **85** |

**Maven Test Execution:**

```text
Tests run: 85, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

The automated tests cover service operations, controller responses, resource retrieval, missing-resource handling, and application behavior.

### Run Automated Tests

From the project directory, execute:

```bash
mvn clean test
```

Alternatively, in Eclipse:

1. Open **Run → Run Configurations**.
2. Select a Maven Build configuration.
3. Set the base directory to the project folder.
4. Enter `clean test` under Goals.
5. Click **Run**.

## Manual API Testing

The application was manually tested through Swagger UI while connected to PostgreSQL.

### Customer API Testing

| Operation | Endpoint | Result |
|---|---|---|
| Retrieve customers | `GET /api/customers` | HTTP 200 |
| Create customer | `POST /api/customers` | HTTP 200 |
| Update customer | `PUT /api/customers/{id}` | HTTP 200 |
| Delete customer | `DELETE /api/customers/{id}` | HTTP 204 |
| Verify deletion | `GET /api/customers` | HTTP 200 |

Customer creation, retrieval, updating, and deletion were verified.

After deletion, the test customer was no longer present in the customer list.

### Subscription Plan API Testing

The subscription-plan APIs were manually tested using Swagger UI.

| Operation | Endpoint | Result |
|---|---|---|
| Create plan | `POST /api/plans` | HTTP 200 |
| List plans | `GET /api/plans` | HTTP 200 |
| Retrieve plan by ID | `GET /api/plans/{id}` | HTTP 200 |
| Update plan | `PUT /api/plans/{id}` | HTTP 200 |
| Delete plan | `DELETE /api/plans/{id}` | Previously returned HTTP 404; retest performed |
| Verify plan absence | `GET /api/plans/6` | HTTP 404 |

#### Initial Subscription Plan Testing

A subscription plan was created with the following details:

- Plan ID: `4`
- Name: `Monthly Plan`
- Price: `499`
- Duration: `30 days`

The plan was subsequently updated to:

- Name: `Monthly Plan Updated`
- Price: `599`
- Duration: `30 days`

The initial DELETE request returned HTTP 404, but subsequent GET requests confirmed that plan ID 4 was absent.

#### Subscription Plan DELETE Retesting — October 22, 2026

A fresh subscription plan was created using `POST /api/plans`.

**Test Data:**

```json
{
  "id": 6,
  "name": "Delete API Test Plan",
  "price": 299,
  "durationInDays": 30
}
```

**Observed Results:**

| Test | Result |
|---|---|
| Create test plan | HTTP 200 |
| DELETE request during retesting | HTTP 204 No Content |
| Retrieve plan ID 6 afterward | HTTP 404 Not Found |

The final GET request returned:

```json
{
  "status": 404,
  "message": "Subscription plan not found with id: 6",
  "path": "/api/plans/6",
  "errors": null
}
```

**Observation:** Plan ID 6 was successfully created and was no longer available after the retesting sequence. A DELETE request returned HTTP 204, consistent with successful deletion. The DELETE request URL should be confirmed from Swagger UI before treating the exact endpoint result as fully verified.

### Subscription, Invoice, and Payment Workflow Testing

The core billing workflow was manually tested using Swagger UI against the running Spring Boot application connected to PostgreSQL.

The workflow included:

1. Creating a customer.
2. Creating a monthly subscription plan.
3. Creating a subscription associated with the customer.
4. Creating an invoice for the subscription.
5. Recording a completed payment.
6. Retrieving the invoice and verifying its updated payment status.

**Example Test Data:**

| Resource | Test Value |
|---|---|
| Customer ID | `2` |
| Subscription Plan ID | `5` |
| Plan Price | `499` |
| Plan Duration | `30 days` |
| Subscription ID | `5` |
| Invoice ID | `6` |
| Invoice Number | `INV-2026-005` |
| Invoice Amount | `499` |
| Payment ID | `8` |
| Payment Amount | `499` |
| Payment Method | `UPI` |
| Payment Status | `completed` |
| Final Invoice Status | `PAID` |

**Result:** A completed payment covering the full invoice amount was recorded, and the invoice status was verified as `PAID`.

### Payment Status Validation Testing

Payment validation was manually tested using Swagger UI.

| Test Scenario | Result |
|---|---|
| Unsupported status `SUCCESS` | HTTP 400 — rejected |
| Valid status `completed` | HTTP 200 — accepted |

These tests verified that unsupported payment statuses are rejected and valid payment statuses can be processed.

## Prerequisites

Before running the application, install:

- Java JDK 11
- Maven
- PostgreSQL
- Git
- Eclipse IDE or another Java-compatible IDE

## Database Configuration

### Step 1: Start PostgreSQL

Install and start the PostgreSQL database server.

### Step 2: Create the Database

Create a PostgreSQL database named:

```sql
CREATE DATABASE subscription_billing;
```

### Step 3: Configure Application Properties

Open:

```text
src/main/resources/application.properties
```

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

### Step 4: Configure Database Credentials

Set the `DB_PASSWORD` environment variable to your local PostgreSQL password.

Do not commit actual database passwords, API keys, or other secrets to GitHub.

**Note:** The `spring.jpa.hibernate.ddl-auto=update` setting is intended for development convenience. Production applications should use a controlled database migration strategy.

## Run the Application

### 1. Clone the Repository

```bash
git clone https://github.com/mayurimdeore/subscription-billing-system.git
```

### 2. Open the Project

Import the cloned repository into Eclipse as an existing Maven project.

### 3. Configure PostgreSQL

Ensure that:

- PostgreSQL is running.
- The `subscription_billing` database exists.
- The database username and password are configured.
- The `DB_PASSWORD` environment variable is available to the application.

### 4. Update Maven Dependencies

In Eclipse:

1. Right-click the project.
2. Select **Maven → Update Project**.
3. Wait for dependency resolution to finish.

### 5. Run the Application

Run `SubscriptionBillingApplication.java` as a Java application in Eclipse.

Alternatively, execute:

```bash
mvn spring-boot:run
```

### 6. Access Swagger UI

After the application starts successfully, open:

http://localhost:8081/swagger-ui.html

## Future Enhancements

Potential future improvements include:

- Automated recurring subscription billing.
- Payment gateway integration.
- Email notifications for invoices and payments.
- Authentication and role-based authorization.
- Docker containerization.
- Cloud deployment.
- Automated integration testing using a real PostgreSQL database.
- Additional API validation and error-handling tests.
- Further verification of the subscription-plan DELETE request behavior.

## Author

**Mayuri Manohar Deore**

- **GitHub:** https://github.com/mayurimdeore
- **Project Repository:** https://github.com/mayurimdeore/subscription-billing-system
- **LinkedIn:** https://linkedin.com/in/mayuri-deore

---

**Project Status:** Core backend functionality implemented, billing workflow manually tested, and all 85 automated tests passing.# Subscription Billing System

A RESTful backend application developed using **Java 11, Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL** to manage customers, subscription plans, subscriptions, invoices, and payments.

The project demonstrates practical Java backend development skills, including REST API design, layered architecture, database persistence, business logic implementation, request validation, exception handling, and automated testing.

## Overview

The Subscription Billing System is a backend application designed to manage subscription-based billing operations.

It allows users to manage customers, create subscription plans, maintain customer subscriptions, generate and manage invoices, and record payments.

The application follows a layered architecture to separate request handling, business logic, database operations, and exception handling.

### Key Highlights

- Developed RESTful APIs using Java 11 and Spring Boot.
- Implemented database persistence using PostgreSQL and Spring Data JPA.
- Used Hibernate ORM for entity mapping and database interactions.
- Implemented input validation and centralized exception handling.
- Developed invoice payment tracking based on completed payments.
- Implemented payment status validation.
- Performed manual API testing using Swagger UI.
- Successfully executed **85 automated tests with zero failures**.
- Used Git and GitHub for version control.

## Features

### Customer Management

- Create customers.
- Retrieve customer details.
- Update customer information.
- Delete customer records.

### Subscription Plan Management

- Create subscription plans.
- Retrieve available subscription plans.
- Update subscription plan details.
- Manage plan pricing and duration.
- Delete subscription plans.

### Subscription Management

- Create customer subscriptions.
- Associate customers with subscription plans.
- Track subscription dates and status.
- Apply subscription-related business rules and validation.

### Invoice Management

- Manage invoices associated with subscriptions.
- Track invoice amounts and payment status.
- Update invoice status based on completed payments.
- Support `PENDING`, `PARTIALLY_PAID`, and `PAID` invoice states.

### Payment Management

- Record payments against invoices.
- Retrieve and manage payment records.
- Validate payment status.
- Support `pending` and `completed` payment statuses.
- Reject unsupported payment statuses.
- Calculate completed payment totals for invoices.

### Additional Features

- RESTful API development.
- PostgreSQL database integration.
- Spring Data JPA and Hibernate ORM.
- Bean Validation.
- Centralized exception handling.
- JUnit 5 and Mockito automated testing.
- Spring MockMvc controller testing.
- Swagger UI and OpenAPI documentation.
- Git and GitHub version control.

## Technology Stack

| Category | Technology |
|---|---|
| Programming Language | Java 11 |
| Backend Framework | Spring Boot 2.7.18 |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |
| Unit Testing | JUnit 5, Mockito |
| Controller Testing | Spring MockMvc |
| Manual API Testing | Swagger UI |
| API Documentation | Springdoc OpenAPI 1.8.0 |
| Version Control | Git, GitHub |
| Development IDE | Eclipse |

## Architecture

The application follows a layered architecture that separates HTTP request handling, business logic, database operations, and exception handling.

### Controller Layer

Handles incoming HTTP requests, validates request data, and returns appropriate HTTP responses.

### Service Layer

Contains business logic for managing customers, subscriptions, invoices, and payments.

### Repository Layer

Uses Spring Data JPA repositories to interact with the PostgreSQL database.

### Entity Layer

Represents application data models and their relationships.

### Exception Handling

Uses custom exceptions and centralized exception handling to manage application errors.

### Application Request Flow

```text
Client / Swagger UI
        |
        v
REST Controller
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
PostgreSQL Database
```

## Main Entities

| Entity | Description |
|---|---|
| Customer | Stores customer information |
| SubscriptionPlan | Stores subscription plan details, pricing, and duration |
| Subscription | Tracks customer subscriptions and their dates |
| Invoice | Stores invoice details associated with subscriptions |
| Payment | Records payments associated with invoices |

## API Endpoints

| Resource | Base Endpoint |
|---|---|
| Customers | `/api/customers` |
| Subscription Plans | `/api/plans` |
| Subscriptions | `/api/subscriptions` |
| Invoices | `/api/invoices` |
| Payments | `/api/payments` |

Refer to the application controllers and Swagger UI for the exact request formats, response schemas, and supported HTTP operations.

## Subscription Billing Workflow

The application supports a billing workflow involving customers, subscription plans, subscriptions, invoices, and payments.

```text
Create Customer
      |
      v
Create Subscription Plan
      |
      v
Create Subscription
      |
      v
Create Invoice
      |
      v
Record Payment
      |
      v
Calculate Completed Payments
      |
      v
Update Invoice Payment Status
```

### Invoice Payment Status

Invoice payment status is determined by the amount of completed payments associated with the invoice.

| Payment Condition | Invoice Status |
|---|---|
| No completed payment amount | `PENDING` |
| Completed payments cover part of the invoice | `PARTIALLY_PAID` |
| Completed payments cover the full invoice amount | `PAID` |

Only payments with a `completed` status contribute to the completed payment total.

### Payment Validation

The application accepts the following payment statuses:

- `pending`
- `completed`

Unsupported payment statuses are rejected through Bean Validation.

For example, a payment request containing `"status": "SUCCESS"` returns HTTP 400 because the value does not match the accepted payment statuses.

## Swagger API Documentation

The project uses Springdoc OpenAPI to provide interactive API documentation.

After starting the application, open:

**Swagger UI:** http://localhost:8081/swagger-ui.html

Swagger UI allows developers to:

- Explore available REST endpoints.
- Inspect request and response schemas.
- Review API parameters.
- Execute API requests directly from a browser.
- Test API responses and HTTP status codes.

## Testing

The application includes automated tests for the service and controller layers using JUnit 5, Mockito, and Spring MockMvc.

### Automated Test Results

The complete Maven test suite was successfully executed on **October 22, 2026**.

| Test Layer | Tests Passed |
|---|---:|
| Service Layer | 60 |
| Controller Layer | 25 |
| **Total** | **85** |

**Maven Test Execution:**

```text
Tests run: 85, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
```

The automated tests cover service operations, controller responses, resource retrieval, missing-resource handling, and application behavior.

### Run Automated Tests

From the project directory, execute:

```bash
mvn clean test
```

Alternatively, in Eclipse:

1. Open **Run → Run Configurations**.
2. Select a Maven Build configuration.
3. Set the base directory to the project folder.
4. Enter `clean test` under Goals.
5. Click **Run**.

## Manual API Testing

The application was manually tested through Swagger UI while connected to PostgreSQL.

### Customer API Testing

| Operation | Endpoint | Result |
|---|---|---|
| Retrieve customers | `GET /api/customers` | HTTP 200 |
| Create customer | `POST /api/customers` | HTTP 200 |
| Update customer | `PUT /api/customers/{id}` | HTTP 200 |
| Delete customer | `DELETE /api/customers/{id}` | HTTP 204 |
| Verify deletion | `GET /api/customers` | HTTP 200 |

Customer creation, retrieval, updating, and deletion were verified.

After deletion, the test customer was no longer present in the customer list.

### Subscription Plan API Testing

The subscription-plan APIs were manually tested using Swagger UI.

| Operation | Endpoint | Result |
|---|---|---|
| Create plan | `POST /api/plans` | HTTP 200 |
| List plans | `GET /api/plans` | HTTP 200 |
| Retrieve plan by ID | `GET /api/plans/{id}` | HTTP 200 |
| Update plan | `PUT /api/plans/{id}` | HTTP 200 |
| Delete plan | `DELETE /api/plans/{id}` | Previously returned HTTP 404; retest performed |
| Verify plan absence | `GET /api/plans/6` | HTTP 404 |

#### Initial Subscription Plan Testing

A subscription plan was created with the following details:

- Plan ID: `4`
- Name: `Monthly Plan`
- Price: `499`
- Duration: `30 days`

The plan was subsequently updated to:

- Name: `Monthly Plan Updated`
- Price: `599`
- Duration: `30 days`

The initial DELETE request returned HTTP 404, but subsequent GET requests confirmed that plan ID 4 was absent.

#### Subscription Plan DELETE Retesting — October 22, 2026

A fresh subscription plan was created using `POST /api/plans`.

**Test Data:**

```json
{
  "id": 6,
  "name": "Delete API Test Plan",
  "price": 299,
  "durationInDays": 30
}
```

**Observed Results:**

| Test | Result |
|---|---|
| Create test plan | HTTP 200 |
| DELETE request during retesting | HTTP 204 No Content |
| Retrieve plan ID 6 afterward | HTTP 404 Not Found |

The final GET request returned:

```json
{
  "status": 404,
  "message": "Subscription plan not found with id: 6",
  "path": "/api/plans/6",
  "errors": null
}
```

**Observation:** Plan ID 6 was successfully created and was no longer available after the retesting sequence. A DELETE request returned HTTP 204, consistent with successful deletion. The DELETE request URL should be confirmed from Swagger UI before treating the exact endpoint result as fully verified.

### Subscription, Invoice, and Payment Workflow Testing

The core billing workflow was manually tested using Swagger UI against the running Spring Boot application connected to PostgreSQL.

The workflow included:

1. Creating a customer.
2. Creating a monthly subscription plan.
3. Creating a subscription associated with the customer.
4. Creating an invoice for the subscription.
5. Recording a completed payment.
6. Retrieving the invoice and verifying its updated payment status.

**Example Test Data:**

| Resource | Test Value |
|---|---|
| Customer ID | `2` |
| Subscription Plan ID | `5` |
| Plan Price | `499` |
| Plan Duration | `30 days` |
| Subscription ID | `5` |
| Invoice ID | `6` |
| Invoice Number | `INV-2026-005` |
| Invoice Amount | `499` |
| Payment ID | `8` |
| Payment Amount | `499` |
| Payment Method | `UPI` |
| Payment Status | `completed` |
| Final Invoice Status | `PAID` |

**Result:** A completed payment covering the full invoice amount was recorded, and the invoice status was verified as `PAID`.

### Payment Status Validation Testing

Payment validation was manually tested using Swagger UI.

| Test Scenario | Result |
|---|---|
| Unsupported status `SUCCESS` | HTTP 400 — rejected |
| Valid status `completed` | HTTP 200 — accepted |

These tests verified that unsupported payment statuses are rejected and valid payment statuses can be processed.

## Prerequisites

Before running the application, install:

- Java JDK 11
- Maven
- PostgreSQL
- Git
- Eclipse IDE or another Java-compatible IDE

## Database Configuration

### Step 1: Start PostgreSQL

Install and start the PostgreSQL database server.

### Step 2: Create the Database

Create a PostgreSQL database named:

```sql
CREATE DATABASE subscription_billing;
```

### Step 3: Configure Application Properties

Open:

```text
src/main/resources/application.properties
```

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

### Step 4: Configure Database Credentials

Set the `DB_PASSWORD` environment variable to your local PostgreSQL password.

Do not commit actual database passwords, API keys, or other secrets to GitHub.

**Note:** The `spring.jpa.hibernate.ddl-auto=update` setting is intended for development convenience. Production applications should use a controlled database migration strategy.

## Run the Application

### 1. Clone the Repository

```bash
git clone https://github.com/mayurimdeore/subscription-billing-system.git
```

### 2. Open the Project

Import the cloned repository into Eclipse as an existing Maven project.

### 3. Configure PostgreSQL

Ensure that:

- PostgreSQL is running.
- The `subscription_billing` database exists.
- The database username and password are configured.
- The `DB_PASSWORD` environment variable is available to the application.

### 4. Update Maven Dependencies

In Eclipse:

1. Right-click the project.
2. Select **Maven → Update Project**.
3. Wait for dependency resolution to finish.

### 5. Run the Application

Run `SubscriptionBillingApplication.java` as a Java application in Eclipse.

Alternatively, execute:

```bash
mvn spring-boot:run
```

### 6. Access Swagger UI

After the application starts successfully, open:

http://localhost:8081/swagger-ui.html

## Future Enhancements

Potential future improvements include:

- Automated recurring subscription billing.
- Payment gateway integration.
- Email notifications for invoices and payments.
- Authentication and role-based authorization.
- Docker containerization.
- Cloud deployment.
- Automated integration testing using a real PostgreSQL database.
- Additional API validation and error-handling tests.
- Further verification of the subscription-plan DELETE request behavior.

## Author

**Mayuri Manohar Deore**

- **GitHub:** https://github.com/mayurimdeore
- **Project Repository:** https://github.com/mayurimdeore/subscription-billing-system
- **LinkedIn:** https://linkedin.com/in/mayuri-deore

---

**Project Status:** Core backend functionality implemented, billing workflow manually tested, and all 85 automated tests passing.
