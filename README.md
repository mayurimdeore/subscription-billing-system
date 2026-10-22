# Subscription Billing System

A RESTful backend application built using **Java 11, Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL** to manage customers, subscription plans, subscriptions, invoices, and payments.

The project demonstrates Java backend development, layered architecture, REST API design, database persistence, input validation, business logic implementation, exception handling, and automated testing.

## Overview

The Subscription Billing System is a backend application designed to manage subscription-based billing operations.

It supports customer registration, subscription plan management, subscription tracking, invoice generation and management, and payment processing.

The application uses a layered architecture to separate API request handling, business logic, database operations, and exception handling.

**Key Highlights:**

- Developed RESTful APIs using Java and Spring Boot.
- Implemented database persistence using PostgreSQL, Spring Data JPA, and Hibernate.
- Applied a layered architecture using controllers, services, repositories, and entities.
- Implemented input validation and centralized exception handling.
- Developed invoice payment tracking based on completed payment amounts.
- Tested APIs manually using Swagger UI.
- Successfully executed **85 automated tests with zero failures**.

## Features

### Customer Management
- Create new customers.
- Retrieve customer information.
- Update customer details.
- Delete customer records.

### Subscription Plan Management
- Create subscription plans.
- Retrieve available plans.
- Update subscription plan details.
- Manage plan pricing and duration.
- Delete subscription plans.

### Subscription Management
- Create customer subscriptions.
- Associate customers with subscription plans.
- Track subscription dates and status.
- Validate subscription-related business rules.

### Invoice Management
- Manage invoices associated with subscriptions.
- Track invoice amounts and payment status.
- Update invoice payment states based on completed payments.
- Support `PENDING`, `PARTIALLY_PAID`, and `PAID` invoice states.

### Payment Management
- Record payments associated with invoices.
- Retrieve and manage payment records.
- Support `pending` and `completed` payment statuses.
- Reject unsupported
